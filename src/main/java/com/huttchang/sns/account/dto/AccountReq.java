package com.huttchang.sns.account.dto;


import com.huttchang.sns.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AccountReq {

    @Setter
    private long id;
    @NotEmpty
    private String email;
    @NotEmpty
    @Setter
    @Min(8)
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

