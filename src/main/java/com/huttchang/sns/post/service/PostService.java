package com.huttchang.sns.post.service;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.post.domain.Post;
import com.huttchang.sns.post.domain.PostLike;
import com.huttchang.sns.post.dto.PostLikeId;
import com.huttchang.sns.post.dto.PostReq;
import com.huttchang.sns.post.repository.PostLikeRepository;
import com.huttchang.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

//    public Post findPosts(User user, PostReq req) throws Exception {
//        req.setUserId(user.getId());
//        return postRepository.f(req.toEntity());
//    }


    public Post createPost(User user, PostReq req) throws Exception {
        req.setUserId(user.getId());
        return postRepository.save(req.toEntity());
    }

    @Transactional
    public boolean likePost(User user,long postId) throws Exception {
        // 포스트 조회
        Post post = postRepository.findById(postId).orElseThrow(DataNotFoundException::new);
        // 좋아요 여부 확인
        if (postLikeRepository.findById(new PostLikeId(postId, user.getId())).isPresent()){
            throw new DuplicationException("You have already liked it!");
        }
        // 좋아요 로그 추가
        postLikeRepository.save(new PostLike(postId, user.getId()));
        // 좋아요 회수 증가
        post.like();
        // 좋아요 회수 증가 저장
        postRepository.save(post);
        // 좋아요 결과 반환
        return true;
    }

    @Transactional
    public boolean unlikePost(User user, long postId) throws Exception {
        // 포스트 조회
        Post post = postRepository.findById(postId).orElseThrow(DataNotFoundException::new);
        // 좋아요 확인
        postLikeRepository.findById(new PostLikeId(postId, user.getId()))
                .orElseThrow(()-> new DataNotFoundException("You didn't like it."));
        // 좋아요 로그 제거
        postLikeRepository.deleteById(new PostLikeId(postId, user.getId()));
        // 좋아요 회수 감소
        post.unlike();
        // 좋아요 회수 감소 저장
        postRepository.save(post);

        return true;
    }

}
