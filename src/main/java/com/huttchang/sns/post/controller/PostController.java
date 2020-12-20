package com.huttchang.sns.post.controller;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.post.domain.Post;
import com.huttchang.sns.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
public class PostController {

    /**
     * 나와, 내 팔로우의 포스트 모두 리스트업,
     * 정렬
     *  내가 자주 찾아간 순
     *  좋아요한 친구 순
     *  포스팅 날짜 desc순
     * @return
     */
    @GetMapping
    public ResponseBody<List<PostDto>> getPosts(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseBody<PostDto> getPostById(@PathVariable long id){
        return null;
    }

    @PostMapping
    public ResponseBody<PostDto> createPost(){
        return null;
    }

    @PutMapping
    public ResponseBody<PostDto> modifyPost(){
        return null;
    }

    @DeleteMapping
    public ResponseBody<PostDto> deletePost(){
        return null;
    }

    @PostMapping("{id}/like")
    public ResponseBody<PostDto> likePost(@PathVariable long id){
        return null;
    }

    @PostMapping("{id}/unlike")
    public ResponseBody<PostDto> unlikePost(@PathVariable long id){
        return null;
    }

}
