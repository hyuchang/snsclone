package com.huttchang.sns.user.controller;


import com.huttchang.global.model.ResponseBody;
import com.huttchang.sns.user.model.User;
import com.huttchang.sns.user.model.UserRequest;
import com.huttchang.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final UserService userService;

    @GetMapping(path="/{id}")
    public ResponseBody<User> findByOne(@PathVariable long id) throws Exception {
        return new ResponseBody(userService.getUserInfo(new UserRequest(id)));
    }

    @PutMapping(path="/{id}")
    public ResponseBody<User> modify(@PathVariable UserRequest request) throws Exception {
        return null;
    }

    @PostMapping
    public ResponseBody<User> join(@RequestBody UserRequest request) throws Exception {
        return null;
    }


}
