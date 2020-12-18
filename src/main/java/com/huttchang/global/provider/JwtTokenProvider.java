package com.huttchang.global.provider;

import com.huttchang.global.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    // 토큰 유효시간 30분
    private static final long TOKEN_VALIDTIME = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    private Key key;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALIDTIME)) // set Expire Time
                .signWith(this.key, SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(getClaim(token).getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }catch (Exception e){
            log.error("getAuthentication",e);
            return null;
        }
    }

    // 토큰에서 회원 정보 추출
    public Claims getClaim(String token) throws Exception {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            throw new UnauthorizedException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("JWT token compact of handler are invalid.");
        }
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("validateToken",e);
            return false;
        }
    }
}