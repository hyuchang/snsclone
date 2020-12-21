package com.huttchang.sns.notification.domain;

import com.huttchang.sns.notification.model.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "POST_ID")
    private Long postId;
    @Column(name = "TO_USER_ID")
    private Long toUserId;
    @Column(name = "FROM_USER_ID")
    private Long fromUserId;
    @Column(name = "IS_READ")
    private boolean isRead;
    @Column(name = "TYPE")
    private NotificationType type;
    @Column(name = "DESCRIPTION")
    private String description;

    @Builder
    public Notification(Long postId, Long toUserId, Long fromUserId, boolean isRead, NotificationType type, String description) {
        this.postId = postId;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.isRead = isRead;
        this.type = type;
        this.description = description;
    }

    public void read(){
        this.isRead = true;
    }
}
