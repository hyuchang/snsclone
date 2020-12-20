package com.huttchang.sns.post.domain;

import com.huttchang.sns.post.dto.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "like_cnt")
    private Long likeCnt = 0L;

    @Column(name = "comment_cnt")
    private Long commentCnt = 0L;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder
    public Post(Long id, Long userId, Long likeCnt, Long commentCnt, Date createAt, String description) {
        this.id = id;
        this.userId = userId;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.createAt = createAt;
        this.description = description;
    }
    public PostDto toDto() {
        return PostDto.builder()
                .id(getId())
                .userId(getUserId())
                .likeCnt(getLikeCnt())
                .commentCnt(getCommentCnt())
                .createAt(getCreateAt())
                .description(getDescription())
                .build();
    }

    public void like(){
        this.likeCnt++;
    }
    public void unlike(){
        this.likeCnt = this.likeCnt-1 < 0 ? 0 : --this.likeCnt;
    }

}
