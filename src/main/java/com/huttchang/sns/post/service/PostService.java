package com.huttchang.sns.post.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.global.exception.InvalidRequestException;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.notification.model.NotificationReq;
import com.huttchang.sns.notification.model.NotificationType;
import com.huttchang.sns.notification.service.NotificationService;
import com.huttchang.sns.post.domain.Post;
import com.huttchang.sns.post.domain.PostComment;
import com.huttchang.sns.post.domain.PostImage;
import com.huttchang.sns.post.domain.PostLike;
import com.huttchang.sns.post.dto.PostCommentReq;
import com.huttchang.sns.post.dto.PostLikeId;
import com.huttchang.sns.post.dto.PostReq;
import com.huttchang.sns.post.repository.PostCommentRepository;
import com.huttchang.sns.post.repository.PostImageRepository;
import com.huttchang.sns.post.repository.PostLikeRepository;
import com.huttchang.sns.post.repository.PostRepository;
import com.huttchang.sns.relation.domain.RelationId;
import com.huttchang.sns.relation.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${path.upload_path}")
    private String uploadPath;

    private static final String POST_PATH = "/posts";
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostLikeRepository postLikeRepository;
    private final FileUploadService fileUploadService;
    private final RelationRepository relationRepository;
    private final NotificationService notificationService;

    public List<Post> findPostByRelationShip(PostReq req) throws Exception {
        return postRepository.findPostByRelationShip(req.getUserId(), req.getOffset(), req.getLimit());
    }

    public List<Post> findPostByUserId(PostReq req, Long someoneId) throws Exception {
        // 친구인경우
        if (relationRepository.isRelation(req.getUserId(), someoneId)>0)
            // 공개, 친구공개 까지 모두 보여줌
            return postRepository.findPostRelationByUserId(someoneId, req.getOffset(), req.getLimit());
        else
            // 아닌 경우 전체 공개 건만 보여줌
            return postRepository.findPostNONERelationByUserId(someoneId, req.getOffset(), req.getLimit());
    }

    @Transactional
    public Post createPost(PostReq req) throws Exception {
        // 작성자 ID 설정
        // 포스트 등록
        Post createdPost = postRepository.save(req.toEntity());
        // 이미지 경로 설정
        String webPath = new StringBuffer(uploadPath)
                .append(POST_PATH)
                .append("/")
                .append(createdPost.getId()).toString();
        // 이미지들의 정보를 담을 리스트 생성
        List<PostImage> imageList = new ArrayList<>();
        // 이미지 업로드
        List<String> pathList = fileUploadService.upload(webPath, "posts/"+createdPost.getId(), req.getImages());
        // 업로드 후 받은 결과이용하여 db삽입 데이터 준비
        pathList.forEach(path -> imageList.add(new PostImage(createdPost.getId(), path)));
        // 이미지 db insert
        postImageRepository.saveAll(imageList);
        return createdPost;
    }

    @Transactional
    public boolean likePost(Long userId, long postId) throws Exception {
        // 포스트 조회
        Post post = postRepository.findById(postId).orElseThrow(DataNotFoundException::new);
        // 좋아요 여부 확인
        if (postLikeRepository.findById(new PostLikeId(postId, userId)).isPresent()) {
            throw new DuplicationException("You have already liked it!");
        }
        // 좋아요 로그 추가
        postLikeRepository.save(new PostLike(postId, userId));
        // 좋아요 회수 증가
        post.incrementlikeCnt();
        // 좋아요 회수 증가 저장
        postRepository.save(post);
        // 노티 메세지 저장
        notificationService.createNotification(
            NotificationReq.builder()
                .postId(postId)
                .toUserId(post.getUserId())
                .fromUserId(userId)
                .type(NotificationType.LIKE)
                .build()
        );
        // 좋아요 결과 반환
        return true;
    }

    @Transactional
    public boolean unlikePost(Long userId, long postId) throws Exception {
        // 포스트 조회
        Post post = postRepository.findById(postId).orElseThrow(DataNotFoundException::new);
        // 좋아요 확인
        postLikeRepository.findById(new PostLikeId(postId, userId))
                .orElseThrow(() -> new DataNotFoundException("You didn't like it."));
        // 좋아요 로그 제거
        postLikeRepository.deleteById(new PostLikeId(postId, userId));
        // 좋아요 회수 감소
        post.decrementlikeCnt();
        // 좋아요 회수 감소 저장
        postRepository.save(post);
        return true;
    }

    @Transactional
    public boolean createComment(PostCommentReq req) throws Exception {
        // 포스트 조회
        Post post = postRepository.
                findById(req.getPostId()).orElseThrow(DataNotFoundException::new);
        // 댓글 등록
        postCommentRepository.save(PostComment.builder()
                .postId(req.getPostId())
                .userId(req.getUserId())
                .comment(req.getComment()).build());
        // 댓글 수 증가
        post.incrementCommentCnt();
        // 댓글 수 증가 저장
        postRepository.save(post);
        // 노티 메세지 저장
        notificationService.createNotification(
                NotificationReq.builder()
                        .postId(req.getPostId())
                        .toUserId(post.getUserId())
                        .fromUserId(req.getUserId())
                        .type(NotificationType.COMMENT)
                        .build()
        );
        return true;
    }

    @Transactional
    public boolean deleteComment(PostCommentReq req) throws Exception {
        // 포스트 조회
        Post post
                = postRepository.findById(req.getPostId()).orElseThrow(DataNotFoundException::new);
        // 댓글 조회
        PostComment comment
                = postCommentRepository.findById(req.getId()).orElseThrow(DataNotFoundException::new);
        // 작성자 본인인지 확인
        if ( req.getUserId() != comment.getUserId()){
            throw new InvalidRequestException();
        }
        // 댓글 삭제 수행
        postCommentRepository.deleteById(req.getId());
        // 댓글 수 감소
        post.decrementCommentCnt();
        // 댓글 수 감소 저장
        postRepository.save(post);
        return true;
    }

}
