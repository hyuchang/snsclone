package com.huttchang.sns.relation.dto;

public enum RelationState {
    NONE(0),
    REQUESTED(1),
    ACCEPTED(2),
    REJECTED(3);

    private int value;

    RelationState(int value) {
        this.value = value;
    }
}
