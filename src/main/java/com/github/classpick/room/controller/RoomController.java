package com.github.classpick.room.controller;

import com.github.classpick.global.dto.Response;
import com.github.classpick.room.controller.dto.request.RoomFilterRequest;
import com.github.classpick.room.controller.dto.response.RoomListResponse;
import com.github.classpick.room.controller.dto.response.RoomTimeTableResponse;
import com.github.classpick.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "강의실")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "강의실 목록 조회")
    @GetMapping("/v0.0/rooms")
    public Response<RoomListResponse> getRooms(
            @ModelAttribute @Valid RoomFilterRequest request
    ) {

        return Response.ok(roomService.getRoomList(request));
    }

    @Operation(summary = "강의실 타임테이블 조회")
    @GetMapping("/v0.0/rooms/{roomId}")
    public Response<RoomTimeTableResponse> getRoomTimeTable(
            @PathVariable long roomId,
            @RequestParam LocalDate date
    ) {

        return Response.ok(roomService.getRoomTimeTable(roomId, date));
    }
}
