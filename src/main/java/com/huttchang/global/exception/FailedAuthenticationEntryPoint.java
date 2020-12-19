package com.huttchang.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huttchang.global.model.ResponseBody;
import com.huttchang.global.model.SystemCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("FailedAuthenticationEntryPoint", authException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter()
                .println(new ObjectMapper().writeValueAsString(new ResponseBody(SystemCode.UNAUTHORIZED, SystemCode.UNAUTHORIZED.toString())));
    }
}
