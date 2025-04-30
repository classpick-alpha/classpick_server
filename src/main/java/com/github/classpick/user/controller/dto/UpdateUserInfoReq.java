package com.github.classpick.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UpdateUserInfoReq {

    private String userGroup;

    private String schoolNumber;

    private String phoneNumber;
}
