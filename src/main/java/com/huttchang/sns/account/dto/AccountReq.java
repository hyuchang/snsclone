package com.huttchang.sns.account.dto;


import com.huttchang.global.model.ACL;
import com.huttchang.global.model.Pagination;
import com.huttchang.sns.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class AccountReq extends Pagination {

    @Setter
    private long id;
    @Setter
    @NotEmpty
    @Length(min = 10)
    private String email;
    @NotEmpty
    @Setter
    @Length(min = 8)
    private String pwd;
    private String nickname;
    private UserState status;
    private ACL acl = ACL.PUBLIC;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .state(UserState.ACTIVE)
                .pwd(this.pwd)
                .build();
    }

}

