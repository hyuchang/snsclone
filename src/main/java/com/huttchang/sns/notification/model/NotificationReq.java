package com.huttchang.sns.notification.model;

import com.huttchang.global.model.Pagination;
import com.huttchang.sns.notification.domain.Notification;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationReq extends Pagination {

    private Long id;
    private Long postId;
    private Long toUserId;
    private Long fromUserId;
    private boolean isRead = false;
    private NotificationType type;
    private String description;
    private Date createAt = new Date();

    @Builder
    public NotificationReq(Long id, Long postId, Long toUserId, Long fromUserId, NotificationType type) {
        this.id = id;
        this.postId = postId;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.type = type;
    }

    public Notification toEntity(){
        return Notification.builder()
                .postId(getPostId())
                .toUserId(getToUserId())
                .fromUserId(getFromUserId())
                .isRead(isRead())
                .type(getType())
                .description(getDescription())
                .build();

    }

}
