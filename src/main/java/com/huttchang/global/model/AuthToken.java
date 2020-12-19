package com.huttchang.global.model;

import lombok.Data;

@Data
public class AuthToken {
    private String accessToken;
    private String refreshToken;
    private long expiration;
}
