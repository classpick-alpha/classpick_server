package com.github.classpick.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByPlaceNameAndCapacityIsLessThanEqual(String placename, int capacity);
}