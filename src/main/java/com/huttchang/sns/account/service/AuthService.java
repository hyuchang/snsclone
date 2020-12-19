package com.huttchang.sns.account.service;

import com.huttchang.global.model.AuthToken;
import com.huttchang.global.provider.JwtTokenProvider;
import com.huttchang.sns.account.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider tokenProvider;

    public AuthToken createToken(User user) throws Exception {
        AuthToken result = tokenProvider.createToken(user);
        // fixme refresh token 생성 후 redis 저장
        result.setRefreshToken("refresh-fjfjal==QLFZK@EDFSSD5231");
        return result;
    }

    private AuthToken refreshToken(AuthToken oldToken) throws Exception {
        return oldToken;
    }

}
