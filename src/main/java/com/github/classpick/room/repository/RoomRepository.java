package com.github.classpick.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Query("""
            select r
            from RoomEntity r
            where r.group.groupId = :groupId and r.placeName = coalesce(:placeName, r.placeName)
              and (
                  :capacity is null
                  or (r.capacity is not null and r.capacity >= :capacity)
              )
              and (
                   :date      is null
                or :startTime is null
                or :endTime   is null
                or not exists (
                    select 1
                    from ReservationEntity re
                    where re.room   = r
                      and re.status in ('APPROVED','REQUESTED')
                      and re.date   = :date
                      and :startTime < re.endTime
                      and :endTime   > re.startTime
                )
              )
            """)
    List<RoomEntity> findAllWithFilter(
            Long groupId,
            String placeName,
            Integer capacity,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );

}
