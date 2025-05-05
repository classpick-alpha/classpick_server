package com.github.classpick.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class GetUserInfoRes {

    private Long userId;
}
