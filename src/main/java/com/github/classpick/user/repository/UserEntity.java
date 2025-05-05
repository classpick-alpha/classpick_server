package com.github.classpick.user.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private String userGroup;


    private String schoolNumber;

    @NotNull
    private String email;

    private String phoneNumber;

    public void updateUserInfo(String schoolNumber, String phoneNumber, String userGroup) {

        this.schoolNumber = schoolNumber;
        this.phoneNumber = phoneNumber;
        this.userGroup = userGroup;
    }
}
