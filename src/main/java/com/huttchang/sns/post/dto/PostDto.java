package com.huttchang.sns.post.dto;

import com.huttchang.global.model.ACL;
import com.huttchang.global.model.KeyValueData;
import com.huttchang.global.model.Pagination;
import com.huttchang.sns.post.domain.Post;
import com.huttchang.sns.post.domain.PostComment;
import com.huttchang.sns.post.domain.PostImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDto extends Pagination {

    private Long id;
    @Setter
    private Long userId;
    private long likeCnt;
    private long commentCnt;
    private Date createAt = new Date();
    @Setter
    @NotEmpty
    private String description;
    @Setter
    private List<KeyValueData<Long, String>> imageDatas;
    private List<PostComment> commentList;
    private List<PostImage> imageList;
    @Setter
    private ACL acl;

    @Builder
    public PostDto(Long id, Long userId, long likeCnt, long commentCnt, Date createAt, @NotEmpty String description, List<KeyValueData<Long, String>> imageDatas, List<PostComment> commentList, List<PostImage> imageList, ACL acl) {
        this.id = id;
        this.userId = userId;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.createAt = createAt;
        this.description = description;
        this.imageDatas = imageDatas;
        this.commentList = commentList;
        this.imageList = imageList;
        this.acl = acl;
    }

    public Post toEntity(){
        return Post.builder()
                .id(getId())
                .userId(getUserId())
                .likeCnt(getLikeCnt())
                .commentCnt(getCommentCnt())
                .createAt(getCreateAt())
                .description(getDescription())
                .acl(getAcl())
                .build();
    }



}