package com.huttchang.sns.user.controller.v1;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.user.domain.User;
import com.huttchang.sns.user.dto.AccountReq;
import com.huttchang.sns.user.service.AccountService;
import com.huttchang.sns.user.service.UserService;
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
    public ResponseBody<User> signIn(@RequestBody AccountReq request) throws Exception{
        return new ResponseBody(accountService.findAccountByIdAndPw(request));
    }

    @PostMapping("/signup")
    public ResponseBody<User> signUp(@RequestBody AccountReq request) throws Exception{
        return new ResponseBody(accountService.createAccount(request));
    }


    @PostMapping("/signout")
    public ResponseBody signOut(@RequestBody AccountReq request) throws Exception{
        // fixme
        return null;
    }

}