package com.huttchang.sns.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class PostLikeId implements Serializable {
    private Long postId;
    private Long userId;

    public PostLikeId(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
