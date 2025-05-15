package com.github.classpick.room.controller.dto.response;

import com.github.classpick.room.repository.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class RoomCreateResponse {

    long roomId;
    String placeName;
    String unitNumber;
    Integer capacity;
    String alias;
    String image;

    public static RoomCreateResponse from(RoomEntity room) {
        return of(
                room.getRoomId(),
                room.getPlaceName(),
                room.getUnitNumber(),
                room.getCapacity(),
                room.getAlias(),
                room.getImage()
        );
    }
}