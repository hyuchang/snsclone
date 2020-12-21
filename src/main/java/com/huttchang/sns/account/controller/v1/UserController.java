package com.huttchang.sns.account.controller.v1;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.account.domain.Authorization;
import com.huttchang.sns.account.domain.User;
import com.huttchang.sns.account.dto.AccountReq;
import com.huttchang.sns.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용자관련 정보를 처리하는 API Controller
 */
@RequestMapping("v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자의 로그인
     * @param request 아이디, 비밀번호
     * @return 인증정보
     * @throws Exception
     */
    @PostMapping("/signin")
    public ResponseBody<Authorization> signIn(@Valid @RequestBody AccountReq request) throws Exception{
        return new ResponseBody(userService.signIn(request));
    }

    /**
     * 회원가입
     * @param request 회원가입정보
     * @return 가입 후 인증정보
     * @throws Exception
     */
    @PostMapping("/signup")
    public ResponseBody<User> signUp(@Valid @RequestBody AccountReq request) throws Exception{
        return new ResponseBody(userService.createAccount(request));
    }

    /**
     * 내 정보
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/me")
    public ResponseBody<User> findById(@AuthenticationPrincipal User user) throws Exception {
        return new ResponseBody(userService.findById(user.getId()));
    }

    /**
     * 내 정보 수정
     * @param principal 토큰내 정보
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(path = "/me")
    public ResponseBody<User> modify(@AuthenticationPrincipal User user, @RequestBody AccountReq request) throws Exception {
        return new ResponseBody(userService.modifyUser(user.getId(), request));
    }

}