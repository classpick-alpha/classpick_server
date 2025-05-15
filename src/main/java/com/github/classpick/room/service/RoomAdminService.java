package com.github.classpick.room.service;

import com.github.classpick.room.controller.dto.request.RoomCreateRequest;
import com.github.classpick.room.controller.dto.response.RoomCreateResponse;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomAdminService {

    private final RoomRepository roomRepository;

    public RoomCreateResponse createRoom(RoomCreateRequest request) {

        RoomEntity room = RoomEntity.builder()
                .placeName(request.getPlaceName())
                .unitNumber(request.getUnitNumber())
                .capacity(request.getCapacity())
                .alias(request.getAlias())
                .image(request.getImage())
                .build();

        room = roomRepository.save(room);

        return RoomCreateResponse.from(room);
    }
}
