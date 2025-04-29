package com.github.classpick.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    boolean existsByRoom_RoomIdAndStartTimeLessThanAndEndTimeGreaterThan(Long roomId, LocalDateTime endTime, LocalDateTime startTime);

    List<ReservationEntity> findByUser_UserId(Long userId);
}
