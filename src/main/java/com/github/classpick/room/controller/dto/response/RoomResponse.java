package com.github.classpick.room.controller.dto.response;

import com.github.classpick.room.repository.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class RoomResponse {

    long roomId;
    String placeName;
    String unitNumber;
    String alias;
    String image;
    Integer capacity;

    public static RoomResponse from(RoomEntity room) {

        return of(
                room.getRoomId(),
                room.getPlaceName(),
                room.getUnitNumber(),
                room.getAlias(),
                room.getImage(),
                room.getCapacity()
        );
    }
}