package com.huttchang.sns.post.dto;

import com.huttchang.sns.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long id;
    @Setter
    private Long userId;
    private Long likeCnt;
    private Long commentCnt;
    private Date createAt;
    @Setter
    @NotEmpty
    private String description;
    @Setter
    private List<KeyValueData<Long, String>> imageDatas;

    @Builder
    public PostDto(Long id,Long userId,Long likeCnt,Long commentCnt,Date createAt,String description) {
        this.id = id;
        this.userId = userId;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.createAt = createAt;
        this.description = description;
    }

    public Post toEntity(){
        return Post.builder()
                .id(getId())
                .userId(getUserId())
                .likeCnt(getLikeCnt())
                .commentCnt(getCommentCnt())
                .createAt(getCreateAt())
                .description(getDescription())
                .build();
    }



}