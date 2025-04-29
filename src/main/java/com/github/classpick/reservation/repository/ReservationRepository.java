package com.github.classpick.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("""
    SELECT COUNT(re) > 0
    FROM ReservationEntity re
    JOIN re.room ro
    WHERE ro.roomId = :roomId
      AND re.date = :date
      AND re.startTime < :endTime
      AND re.endTime > :startTime
""")
    boolean checkAvailableRoom(
            @Param("roomId") Long roomId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    List<ReservationEntity> findByUser_UserId(Long userId);

    List<ReservationEntity> findAllByRoom_RoomIdAndDateBetween(Long roomId, LocalDate startDate, LocalDate endDate);
}
