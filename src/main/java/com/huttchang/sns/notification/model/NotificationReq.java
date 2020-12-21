package com.huttchang.sns.notification.model;

import com.huttchang.global.model.Pagination;
import com.huttchang.sns.notification.domain.Notification;
import lombok.Data;

@Data
public class NotificationReq extends Pagination {

    private Long id;
    private Long postId;
    private Long toUserId;
    private Long fromUserId;
    private boolean isRead = false;
    private NotificationType type = NotificationType.LIKE;
    private String description;

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
