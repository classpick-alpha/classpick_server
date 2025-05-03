package com.github.classpick.user.controller;

import com.github.classpick.global.Response;
import com.github.classpick.security.oauth.OAuth2GoogleUser;
import com.github.classpick.user.controller.dto.GetUserInfoRes;
import com.github.classpick.user.controller.dto.UpdateUserInfoReq;
import com.github.classpick.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    @GetMapping("/v0.0/users/{userId}")
    public Response<GetUserInfoRes> getUserId(@PathVariable Long userId) {
        Long userInfo = userService.getUserInfo(userId);
        return Response.of(200, "success", GetUserInfoRes.of(userInfo));
    }

    @PutMapping("/v0.0/users/info")
    public Response<GetUserInfoRes> updateUserInfo(@AuthenticationPrincipal OAuth2GoogleUser userDetails,
                                                   @Valid @RequestBody UpdateUserInfoReq updateUserInfoReq)
    {
        Long userId = userService.updateUserInfo(userDetails.getUser().getUserId(), updateUserInfoReq);
        return Response.of(200, "success", GetUserInfoRes.of(userId));
    }
}
