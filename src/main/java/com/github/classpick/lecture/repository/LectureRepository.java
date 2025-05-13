package com.github.classpick.lecture.repository;

import com.github.classpick.room.repository.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<LectureEntity, Long> {

    List<LectureEntity> findAllByRoom(RoomEntity room);
    RoomEntity room(RoomEntity room);
}
