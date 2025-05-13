package com.github.classpick.lecture.service;

import com.github.classpick.lecture.controller.dto.response.LectureListResponse;
import com.github.classpick.lecture.controller.dto.response.LectureResponse;
import com.github.classpick.lecture.repository.LectureRepository;
import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.classpick.room.exception.RoomExceptionCode.ROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final RoomRepository roomRepository;
    private final LectureRepository lectureRepository;

    public LectureListResponse getLectures(long roomId) {

        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(() -> new RoomException(ROOM_NOT_FOUND));

        List<LectureResponse> lectureResponses = lectureRepository.findAllByRoom(roomEntity)
                .stream()
                .map(LectureResponse::from)
                .toList();

        return LectureListResponse.of(lectureResponses);
    }
}
