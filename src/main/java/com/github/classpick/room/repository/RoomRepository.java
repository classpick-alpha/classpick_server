package com.github.classpick.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Query("""
            select room from RoomEntity room
            where room.placeName = :placeName
            and room.capacity >= :capacity
            and not exists (
                select rsv from ReservationEntity rsv
                where rsv.room = room
                and (rsv.status = 'APPROVED' or rsv.status = 'REQUESTED')
                and rsv.date = :date
                and (:startTime < rsv.endTime and :endTime > rsv.startTime)
            )
            """)
    List<RoomEntity> findAllWithFilter(
            @Param("placeName") String placeName,
            @Param("capacity") Integer capacity,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );


}
