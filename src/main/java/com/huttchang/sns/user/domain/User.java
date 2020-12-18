package com.huttchang.sns.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huttchang.sns.user.dto.AccountReq;
import com.huttchang.sns.user.dto.UserState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "users")
@Table(name = "USERS")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @JsonIgnore
    @Column(name = "PWD")
    private String pwd;
    @Column(name = "STATUS")
    private UserState status;
    @Column(name = "NICKNAME")
    private String nickname;

    @Builder
    public User(String email, String pwd, String nickname, UserState state){
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

}
