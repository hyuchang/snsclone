package com.huttchang.sns.account.dto;

import lombok.Getter;

@Getter
public enum UserState {
    ACTIVE(0),
    BLOCK(1);

    private int value;

    UserState(int value) {
        this.value = value;
    }
}
