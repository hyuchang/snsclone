package com.huttchang.global.config;

import com.huttchang.global.filter.JWTFilter;
import com.huttchang.global.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtAuthTokenProvider;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v*/accounts/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().sameOrigin().and().addFilterBefore(new JWTFilter(jwtAuthTokenProvider), UsernamePasswordAuthenticationFilter.class);


    }
}
