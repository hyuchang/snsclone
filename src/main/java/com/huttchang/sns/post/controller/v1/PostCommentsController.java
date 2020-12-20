package com.huttchang.sns.post.controller.v1;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.post.domain.Post;
import com.huttchang.sns.post.dto.PostCommentReq;
import com.huttchang.sns.post.dto.PostDto;
import com.huttchang.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/post/{postId}/comments")
public class PostCommentsController {

    private final PostService postService;

    /**
     * 댓글 작성
     * @param principal 인증 유저 정보
     * @param postId 댓글 작성할 포스트 아이디
     * @param req 댓글 정보
     * @return 성공, 실패
     * @throws Exception
     */
    @PostMapping
    public ResponseBody<Boolean> createComment(
            @AuthenticationPrincipal User principal, @PathVariable long postId, @Valid @RequestBody PostCommentReq req
    ) throws Exception{
        req.setPostId(postId);
        req.setUserId(principal.getId());
        return new ResponseBody(postService.createComment(req));
    }

    @DeleteMapping("{id}")
    public ResponseBody<Boolean> deleteComment(
            @AuthenticationPrincipal User principal, @PathVariable long postId, @PathVariable long id
    ) throws Exception {
        return new ResponseBody(postService.deleteComment(new PostCommentReq(id, postId, principal.getId())));
    }


}
