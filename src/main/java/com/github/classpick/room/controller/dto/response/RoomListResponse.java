package com.github.classpick.room.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(staticName = "of")
public class RoomListResponse {

    Collection<RoomResponse> rooms;
}
