package com.github.classpick.room.controller.dto.response;

import java.util.Collection;
import lombok.Builder;

@Builder
public record RoomListResponse(
        Collection<RoomInfo> rooms
) {
    @Builder
    public record RoomInfo (
            long roomId,
            String image,
            int unitNumber
    ) {
        public static RoomInfo of (
                long roomId,
                String image,
                int unitNumber
        ) {
            return new RoomInfo(roomId, image, unitNumber);
        }
    }

}
