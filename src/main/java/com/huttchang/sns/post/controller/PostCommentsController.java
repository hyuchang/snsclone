package com.huttchang.sns.post.controller;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.post.domain.Post;
import com.huttchang.sns.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/post/{postId}/comments")
public class PostCommentsController {

    /**
     * 나와, 내 팔로우의 포스트 모두 리스트업,
     * 정렬
     *  내가 자주 찾아간 순
     *  좋아요한 친구 순
     *  포스팅 날짜 desc순
     * @return
     */
    @PostMapping
    public ResponseBody<PostDto> createComment(@PathVariable long postId){
        return null;
    }

    @PutMapping("{id}")
    public ResponseBody<PostDto> modifyComment(@PathVariable long postId, @PathVariable long commentId){
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseBody<PostDto> deleteComment(@PathVariable long postId, @PathVariable long commentId){
        return null;
    }

    @PostMapping("{id}/like")
    public ResponseBody<PostDto> likeComment(@PathVariable long id, @PathVariable long commentId){
        return null;
    }

    @PostMapping("{id}/unlike")
    public Post unlikeComment(@PathVariable long id, @PathVariable long commentId){
        return null;
    }

}
