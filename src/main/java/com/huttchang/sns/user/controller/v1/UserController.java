package com.huttchang.sns.user.controller.v1;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.user.domain.User;
import com.huttchang.sns.user.dto.AccountReq;
import com.huttchang.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자관련 정보를 처리하는 API Controller
 */
@RequestMapping("v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseBody<User> findById(@PathVariable long id) throws Exception {
        return new ResponseBody(userService.findById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseBody<User> modify(@PathVariable long id, @RequestBody AccountReq request) throws Exception {
        return new ResponseBody(userService.modifyUser(id, request));
    }

}