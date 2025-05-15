package com.github.classpick.room.controller;

import com.github.classpick.global.dto.Request;
import com.github.classpick.global.dto.Response;
import com.github.classpick.room.controller.dto.request.RoomCreateRequest;
import com.github.classpick.room.controller.dto.response.RoomResponse;
import com.github.classpick.room.service.RoomAdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[관리자] 강의실")
@PreAuthorize("hasRole('MANAGER')")
@RestController
@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomAdminService roomAdminService;

    @PostMapping("/v0.0/admin/rooms")
    public Response<RoomResponse> createRoom(@RequestBody @Valid Request<RoomCreateRequest> request) {

        return Response.ok(roomAdminService.createRoom(request.getData()));
    }
}
