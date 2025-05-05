package com.github.classpick.user.controller;

import com.github.classpick.global.dto.Response;
import com.github.classpick.global.security.oauth.OAuth2GoogleUser;
import com.github.classpick.user.controller.dto.GetUserInfoRes;
import com.github.classpick.user.controller.dto.UpdateUserInfoReq;
import com.github.classpick.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/v0.0/users/{userId}")
    public Response<GetUserInfoRes> getUserId(@PathVariable Long userId) {

        Long userInfo = userService.getUserInfo(userId);
        return Response.ok(GetUserInfoRes.of(userInfo));
    }

    @PutMapping("/v0.0/users/info")
    public Response<GetUserInfoRes> updateUserInfo(
            @AuthenticationPrincipal OAuth2GoogleUser userDetails,
            @Valid @RequestBody UpdateUserInfoReq updateUserInfoReq
    ) {

        Long userId = userService.updateUserInfo(userDetails.getUser().getUserId(), updateUserInfoReq);
        return Response.ok(GetUserInfoRes.of(userId));
    }
}
