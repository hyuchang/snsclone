package com.huttchang.sns.notification.model;

public enum NotificationType {
    LIKE(0),
    COMMENT(1),
    RELATION_REQUESTED(2),
    RELATION_ACCEPTED(3);

    private int value;

    NotificationType(int value) {
        this.value = value;
    }
}
