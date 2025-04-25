package com.github.classpick.user.controller;

import com.github.classpick.global.Response;
import com.github.classpick.user.controller.dto.GetUserInfoRes;
import com.github.classpick.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/v0.0/user/{userId}")
    public Response<GetUserInfoRes> getUserId(@PathVariable Long userId) {
        Long userInfo = userService.getUserInfo(userId);
        return Response.of(200, "success", GetUserInfoRes.of(userInfo));
    }
}
