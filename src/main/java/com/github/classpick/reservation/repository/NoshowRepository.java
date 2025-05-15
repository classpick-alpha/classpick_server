package com.github.classpick.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoshowRepository extends JpaRepository<NoshowEntity, Long> {

    @Query("SELECT n FROM NoshowEntity n WHERE n.reservation = :reservationEntity AND n.verified = true")
    Optional<NoshowEntity> findByVerifiedNoshow(ReservationEntity reservationEntity);
}
