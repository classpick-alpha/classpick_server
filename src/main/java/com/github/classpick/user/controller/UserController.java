package com.github.classpick.user.controller;

import com.github.classpick.global.dto.Request;
import com.github.classpick.global.dto.Response;
import com.github.classpick.user.controller.dto.request.UpdateUserRequest;
import com.github.classpick.user.controller.dto.response.SafeUserResponse;
import com.github.classpick.user.controller.dto.response.UserResponse;
import com.github.classpick.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 확인")
    @GetMapping("/v0.0/users/me")
    public Response<UserResponse> getMe() {

        return Response.ok(userService.getMe());
    }

    @Operation(summary = "유저 ID로 유저 정보 조회")
    @GetMapping("/v0.0/users/{userId}")
    public Response<SafeUserResponse> getUserId(@PathVariable Long userId) {

        return Response.ok(userService.getUserInfo(userId));
    }

    @Operation(summary = "추가 정보 입력")
    @PutMapping("/v0.0/users/info")
    public Response<UserResponse> updateUserInfo(
            @RequestBody @Valid Request<UpdateUserRequest> body
    ) {

        return Response.ok(userService.updateUserInfo(body.getData()));
    }
}
