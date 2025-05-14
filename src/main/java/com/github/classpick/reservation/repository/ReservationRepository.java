package com.github.classpick.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    boolean checkAvailableRoom(long roomId, LocalDate date, LocalTime startTime, LocalTime endTime);

    List<ReservationEntity> findByUser_UserId(Long userId);

    @Query("""
            SELECT re
            FROM ReservationEntity re
            JOIN re.room ro
                WHERE ro.roomId = :roomId
                AND re.date Between :startDate and :endDate
                AND re.status NOT IN (com.github.classpick.reservation.repository.Status.REJECTED)
            """)
    List<ReservationEntity> findAllByRoom_RoomIdAndDateBetween(Long roomId, LocalDate startDate, LocalDate endDate);
}
