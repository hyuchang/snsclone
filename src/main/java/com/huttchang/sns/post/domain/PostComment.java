package com.huttchang.sns.post.domain;

import com.huttchang.sns.relation.domain.RelationUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "POST_COMMENT")
@Getter
@NoArgsConstructor
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long Id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "create_at", nullable = false)
    private Date createAt = new Date();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RelationUser userInfo;

    @Builder
    public PostComment(Long userId, Long postId, String comment) {
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
    }
}


