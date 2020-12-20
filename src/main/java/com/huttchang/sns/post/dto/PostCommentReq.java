package com.huttchang.sns.post.dto;

public class PostCommentReq extends PostCommentDto {

    public PostCommentReq(Long id, Long userId, Long postId) {
        super(id, userId, postId);
    }
}
