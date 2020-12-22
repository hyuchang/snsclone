package com.huttchang.sns.post.domain;

import com.huttchang.global.model.ACL;
import com.huttchang.sns.post.dto.PostDto;
import com.huttchang.sns.relation.domain.RelationUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @ColumnDefault("0")
    @Column(name = "like_cnt")
    private long likeCnt = 0L;

    @ColumnDefault("0")
    @Column(name = "comment_cnt")
    private long commentCnt = 0L;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "acl")
    private ACL acl = ACL.PUBLIC;

    @OneToMany(mappedBy = "postId")
    @OrderBy("create_at desc")
    private List<PostComment> commentList;

    @OneToMany(mappedBy = "postId")
    @OrderBy("id desc")
    private List<PostImage> imageList;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RelationUser userInfo;

    @OneToMany(mappedBy = "postId")
    private List<PostLike> likeList;

    @Builder
    public Post(Long id, Long userId, long likeCnt, long commentCnt, Date createAt,
                String description, List<PostComment> commentList, List<PostImage> imageList, ACL acl) {
        this.id = id;
        this.userId = userId;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.createAt = createAt;
        this.description = description;
        this.commentList = commentList;
        this.imageList = imageList;
        this.acl = acl;
    }

    public PostDto toDto() {
        return PostDto.builder()
                .id(getId())
                .userId(getUserId())
                .likeCnt(getLikeCnt())
                .commentCnt(getCommentCnt())
                .createAt(getCreateAt())
                .description(getDescription())
                .commentList(getCommentList())
                .imageList(getImageList())
                .acl(getAcl())
                .build();
    }

    public void incrementlikeCnt() {
        this.likeCnt++;
    }

    public void decrementlikeCnt() {
        this.likeCnt = this.likeCnt - 1 < 0 ? 0 : --this.likeCnt;
    }

    public void incrementCommentCnt() {
        this.commentCnt++;
    }

    public void decrementCommentCnt() {
        this.commentCnt = this.commentCnt - 1 < 0 ? 0 : this.commentCnt--;
    }

}
