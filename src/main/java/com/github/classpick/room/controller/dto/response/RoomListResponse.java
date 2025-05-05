package com.github.classpick.room.controller.dto.response;

import lombok.Builder;

import java.util.Collection;

@Builder
public record RoomListResponse(
        Collection<RoomInfo> rooms
) {

    @Builder
    public record RoomInfo(
            long roomId,
            String image,
            int unitNumber
    ) {

        public static RoomInfo of(long roomId, String image, int unitNumber) {

            return new RoomInfo(roomId, image, unitNumber);
        }
    }

}
