package com.huttchang.sns.post.controller.v1;

import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.post.domain.PostLike;
import com.huttchang.sns.post.dto.PostDto;
import com.huttchang.sns.post.dto.PostReq;
import com.huttchang.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
        return new ResponseBody(postService.createPost(principal, req).toDto());
    }

    /**
     * 나와, 내 팔로우의 포스트 모두 리스트업,
     * 정렬
     *  내가 자주 찾아간 순
     *  좋아요한 친구 순
     *  포스팅 날짜 desc순
     * @return
     */
    @GetMapping
    public ResponseBody<List<PostDto>> getPosts(@AuthenticationPrincipal User principal){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseBody<PostDto> getPostById(@AuthenticationPrincipal User principal, @PathVariable long id){
        return null;
    }

    @PutMapping
    public ResponseBody<PostDto> modifyPost(@AuthenticationPrincipal User principal, @RequestBody PostReq req){
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseBody<PostDto> deletePost(@AuthenticationPrincipal User principal, @RequestBody PostReq req){
        return null;
    }

    @PostMapping("{id}/like")
    public ResponseBody<Boolean> likePost(@AuthenticationPrincipal User principal, @PathVariable long id) throws Exception {
        return new ResponseBody(postService.likePost(principal, id));
    }

    @PostMapping("{id}/unlike")
    public ResponseBody<Boolean> unlikePost(@AuthenticationPrincipal User principal, @PathVariable long id) throws Exception{
        return new ResponseBody(postService.unlikePost(principal, id));
    }

}
