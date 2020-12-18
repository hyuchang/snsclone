package com.huttchang.sns.account.dto;


import com.huttchang.sns.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AccountReq {

    @Setter
    private long id;
    private String email;
    private String pwd;
    private String nickname;
    private UserState status;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .state(UserState.ACTIVE)
                .pwd(this.pwd)
                .build();
    }

}

