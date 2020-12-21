package com.huttchang.sns.post.controller.v1;

import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.post.dto.PostDto;
import com.huttchang.sns.post.dto.PostReq;
import com.huttchang.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseBody<PostDto> createPost(@AuthenticationPrincipal User principal, @Valid PostReq req) throws Exception {
        req.setUserId(principal.getId());
        return new ResponseBody(postService.createPost(req).toDto());
    }

    /**
     * todo 찍고 들어온 경우는 상대의 계정이 비공개인지 공개 인지 확인
     * 비공개인 경우는 친구인 경우는 노출
     * 공개인 경우는 친구이던 아니던 상관없음
     * @param principal
     * @param req
     * @return
     * @throws Exception
     */
//    @GetMapping
//    public ResponseBody<List<PostDto>> getPostsByEmail(@AuthenticationPrincipal User principal, PostReq req) throws Exception {
//        req.setUserId(principal.getId());
//        return new ResponseBody(postService.findPost(req));
//    }

    @GetMapping
    public ResponseBody<List<PostDto>> getPosts(@AuthenticationPrincipal User principal, PostReq req) throws Exception {
        req.setUserId(principal.getId());
        return new ResponseBody(postService.findPostByRelationShip(req));
    }


    @GetMapping("/{id}")
    public ResponseBody<PostDto> getPostById(@AuthenticationPrincipal User principal, @PathVariable long id) {
        return null;
    }

    @PostMapping("{id}/like")
    public ResponseBody<Boolean> likePost(@AuthenticationPrincipal User principal, @PathVariable long id) throws Exception {
        return new ResponseBody(postService.likePost(principal.getId(), id));
    }

    @PostMapping("{id}/unlike")
    public ResponseBody<Boolean> unlikePost(@AuthenticationPrincipal User principal, @PathVariable long id) throws Exception {
        return new ResponseBody(postService.unlikePost(principal.getId(), id));
    }

    @PutMapping
    public ResponseBody<PostDto> modifyPost(@AuthenticationPrincipal User principal, @RequestBody PostReq req) {
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseBody<PostDto> deletePost(@AuthenticationPrincipal User principal, @RequestBody PostReq req) {
        return null;
    }


}
