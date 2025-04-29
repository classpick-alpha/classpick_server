package com.github.classpick.room.repository;

import com.github.classpick.global.entity.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @NotNull
    private String placeName;

    @NotNull
    private Integer unitNumber;

    @NotNull
    private Integer capacity;

    @Nullable
    private String alias;

    @Nullable
    private String image;
}
