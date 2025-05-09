package com.github.classpick.user.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Nullable
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Nullable
    private String userGroup;

    @Nullable
    private String schoolNumber;

    @NotNull
    private String email;

    @Nullable
    private String phoneNumber;
}
