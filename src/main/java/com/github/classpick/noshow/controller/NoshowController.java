package com.github.classpick.noshow.controller;

import com.github.classpick.global.dto.Response;
import com.github.classpick.noshow.service.NoshowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "노쇼")
@RequiredArgsConstructor
@RestController
public class NoshowController {

    private final NoshowService noshowService;

    @Operation(summary = "노쇼 검증")
    @PostMapping("/v0.0/ocr/verify")
    public Response<Boolean> verifyOcr(@PathVariable Long reservationId) {
        return Response.ok(noshowService.verifyOcr(reservationId));
    }

}
