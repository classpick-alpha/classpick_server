package com.github.classpick.room.controller;

import com.github.classpick.global.Response;
import com.github.classpick.room.controller.dto.request.RoomFilterRequest;
import com.github.classpick.room.controller.dto.request.RoomTimeTableRequest;
import com.github.classpick.room.controller.dto.response.RoomListResponse;
import com.github.classpick.room.controller.dto.response.RoomTimeTableResponse;
import com.github.classpick.room.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "강의실 목록 조회", description = "사용자가 필터링한 조건에 맞게 예약 가능한 강의실이 보여집니다.")
    @GetMapping("/v0.0/rooms")
    public Response<RoomListResponse> getRooms(
            @RequestParam @Valid RoomFilterRequest request
    ) {
        return Response.ok(roomService.getRoomList(request));
    }

    @Operation(
            summary = "강의실 타임테이블 조회",
            description = "사용자가 선택한 날짜를 기준으로, 해당 주차(월~금) 동안의 강의실 예약 타임테이블을 조회합니다. " +
                    "날짜를 지정하지 않으면 기본적으로 오늘 날짜를 기준으로 조회합니다."
    )
    @GetMapping("/v0.0/rooms/{roomId}")
    public Response<RoomTimeTableResponse> getRoomTimeTable(
            @PathVariable Long roomId,
            @Valid @ModelAttribute RoomTimeTableRequest request
    ){
        return Response.ok(roomService.getRoomTimeTable(roomId, request));
    }

}
