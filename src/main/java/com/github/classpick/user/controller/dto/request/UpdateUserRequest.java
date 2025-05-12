package com.github.classpick.user.controller.dto.request;

import com.github.classpick.global.validation.UserGroupValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.github.classpick.global.regexp.RegExps.NAME;
import static com.github.classpick.global.regexp.RegExps.PHONE_NUMBER;
import static com.github.classpick.global.regexp.RegExps.SCHOOL_NUMBER;

@Getter
@AllArgsConstructor(staticName = "of")
public class UpdateUserRequest {

    @Pattern(regexp = NAME)
    String name;

    @NotBlank
    @UserGroupValidate
    String userGroup;

    @Pattern(regexp = SCHOOL_NUMBER)
    String schoolNumber;

    @Pattern(regexp = PHONE_NUMBER)
    String phoneNumber;
}
