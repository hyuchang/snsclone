package com.huttchang.sns.post.domain;

import com.huttchang.sns.post.dto.PostLikeId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POST_LIKE")
@IdClass(PostLikeId.class)
@Getter
@NoArgsConstructor
public class PostLike {

    @Id
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public PostLike(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}


