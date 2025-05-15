package com.github.classpick.reservation.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class OcrRequest {

    String imageUrl;
}
