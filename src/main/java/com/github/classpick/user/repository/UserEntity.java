package com.github.classpick.user.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;



@Getter
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private String deviceId;

    @NotNull @Enumerated(EnumType.STRING)
    private Role role;

    private String userGroup;

    @NotNull
    private String schoolNumber;

    private String email;
    private String phoneNumber;


}
