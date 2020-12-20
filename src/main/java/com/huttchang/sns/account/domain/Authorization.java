package com.huttchang.sns.account.domain;

import com.huttchang.global.model.AuthToken;
import lombok.Data;

@Data
public class Authorization  {

    private AuthToken authToken;
    private User user;

    public Authorization(User user, AuthToken token){
        this.user = user;
        this.authToken = token;
    }

}
