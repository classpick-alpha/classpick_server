package com.github.classpick.user.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.GenerationType.*;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    @NotNull
    private String name;

    @NotNull @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String email;

    private String userGroup;

    private String schoolNumber;

    private String phoneNumber;

    public void updateUserInfo(String schoolNumber, String phoneNumber, String userGroup) {
        this.schoolNumber = schoolNumber;
        this.phoneNumber = phoneNumber;
        this.userGroup = userGroup;
    }
}
