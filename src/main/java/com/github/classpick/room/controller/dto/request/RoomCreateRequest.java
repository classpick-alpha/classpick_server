package com.github.classpick.room.controller.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class RoomCreateRequest {

    @NotNull
    private String placeName;

    @NotNull
    private String unitNumber;

    @NotNull
    private Integer capacity;

    @Nullable
    private String alias;

    @NotNull
    private String image;
}
