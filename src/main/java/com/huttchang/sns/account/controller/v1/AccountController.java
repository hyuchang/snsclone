package com.huttchang.sns.account.controller.v1;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.Authorization;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 계정과 관련된 내용을 처리하는 API
 */
@RequestMapping("v1/accounts")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signin")
    public ResponseBody<Authorization> signIn(@RequestBody AccountReq request) throws Exception{
        return new ResponseBody<Authorization>(accountService.authorization(request));
    }

    @PostMapping("/signup")
    public ResponseBody<User> signUp(@RequestBody AccountReq request) throws Exception{
        return new ResponseBody<User>(accountService.createAccount(request));
    }


    @PostMapping("/signout")
    public ResponseBody signOut(@RequestBody AccountReq request) throws Exception{
        // fixme
        return null;
    }

}