package com.huttchang.sns.account.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huttchang.global.model.ACL;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.account.dto.UserState;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity(name = "users")
@Table(name = "USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "pwd")
    private String pwd;
    @JsonIgnore
    @Column(name = "status")
    private UserState status;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "acl")
    private ACL acl = ACL.PUBLIC;
    @JsonIgnore
    @Transient
    private List<String> roleList = new ArrayList<>();

    public User(){
        this.roleList.add("USER");
    }

    @Builder
    public User(long id, String email, String pwd, String nickname, UserState state){
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.status = state;
        this.nickname = nickname;
    }

    public void modifyUser(AccountReq signupReq) {
        this.pwd = signupReq.getPwd();
        this.status = signupReq.getStatus();
        this.nickname = signupReq.getNickname();
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoleList().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.getStatus() == UserState.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getStatus() == UserState.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getStatus() == UserState.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getStatus() == UserState.ACTIVE;
    }
}
