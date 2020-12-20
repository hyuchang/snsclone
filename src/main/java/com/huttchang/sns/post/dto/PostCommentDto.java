package com.huttchang.sns.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class PostCommentDto {

    private Long id;
    private Long userId;
    private Long postId;
    @NotNull
    @Length(min = 1, max = 120)
    private String comment;
    private Date createAt = new Date();

    public PostCommentDto(Long id, Long userId, Long postId) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
    }
}
