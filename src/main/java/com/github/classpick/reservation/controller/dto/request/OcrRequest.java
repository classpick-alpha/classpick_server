package com.github.classpick.reservation.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class OcrRequest {

    @NotBlank
    String imageUrl;
}
