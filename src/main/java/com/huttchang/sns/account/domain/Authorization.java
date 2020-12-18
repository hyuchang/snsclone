package com.huttchang.sns.account.domain;

import lombok.Data;

@Data
public class Authorization {
    private String accessToken;
    private String refreshToken;
    private long expiration;
    private User user;
}
