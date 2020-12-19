package com.huttchang.global.provider;

import antlr.TokenStreamException;
import com.huttchang.global.exception.AuthTokenExpiredException;
import com.huttchang.global.exception.UnauthorizedException;
import com.huttchang.global.model.AuthToken;
import com.huttchang.sns.account.domain.Authorization;
import com.huttchang.sns.account.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKeyStr;
    private Key secretKey;
    // fixme 개발단계에서만 토큰 유효성 무제한급으로 처리(토큰 유효시간 30분)
    private static final long TOKEN_VALIDTIME = 3000 * 60 * 1000L;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyStr);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public AuthToken createToken(User user) {
        AuthToken result = new AuthToken();
        Claims claims = Jwts.claims().setSubject(user.getEmail()); // JWT payload 에 저장되는 정보단위
        claims.put(ClaimKeyEnum.ROLES.name(), user.getRoleList());
        claims.put(ClaimKeyEnum.ID.name(), user.getId());
        claims.put(ClaimKeyEnum.NICK_NAME.name(), user.getNickname());
        Date now = new Date();
        result.setExpiration(now.getTime() + TOKEN_VALIDTIME);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(result.getExpiration()))
                .signWith(this.secretKey, SignatureAlgorithm.HS384)
                .compact();
        result.setAccessToken(token);
        return result;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        try {
            Claims userClaim = getClaim(token);
            User userInfo = User.builder()
                    .email(userClaim.getSubject())
                    .nickname((String) userClaim.get(ClaimKeyEnum.NICK_NAME.name()))
                    .id((Integer) userClaim.get(ClaimKeyEnum.ID.name())).build();

            return new UsernamePasswordAuthenticationToken(userInfo, "", null);
        } catch (Exception e) {
            log.error("getAuthentication", e);
            return null;
        }
    }

    // 토큰에서 회원 정보 추출
    public Claims getClaim(String token) throws Exception {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            throw new UnauthorizedException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new AuthTokenExpiredException("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("JWT token compact of handler are invalid.");
        }
    }

    public enum ClaimKeyEnum {
        ID("id"), EMAIL("email"), ROLES("roles"), NICK_NAME("nick");
        private String value;

        ClaimKeyEnum(String value) {
            this.value = value;
        }
    }
}