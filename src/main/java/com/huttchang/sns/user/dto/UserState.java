package com.huttchang.sns.user.dto;

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
