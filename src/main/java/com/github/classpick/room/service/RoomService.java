package com.github.classpick.room.service;

import static com.github.classpick.room.exception.RoomExceptionCode.ROOM_NOT_FOUND;

import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.room.controller.dto.request.RoomFilterRequest;
import com.github.classpick.room.controller.dto.request.RoomTimeTableRequest;
import com.github.classpick.room.controller.dto.response.RoomListResponse;
import com.github.classpick.room.controller.dto.response.RoomTimeTableResponse;
import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    
    public RoomListResponse getRoomList(RoomFilterRequest dto){
        List<RoomListResponse.RoomInfo> rooms =  roomRepository
                .findAllWithFilter(
                        dto.placeName(),
                        dto.capacity(),
                        dto.date(),
                        dto.startTime(),
                        dto.endTime()
                ).stream()
                .map(room -> RoomListResponse.RoomInfo.of(
                        room.getRoomId(),
                        room.getImage(),
                        room.getUnitNumber()
                )).toList();

        return RoomListResponse.builder()
                .rooms(rooms)
                .build();
    }

    public RoomTimeTableResponse getRoomTimeTable(Long roomId, RoomTimeTableRequest dto){
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND.getMessage(), ROOM_NOT_FOUND.getCode()));

        LocalDate baseDate = dto.date() != null ? dto.date() : LocalDate.now();
        LocalDate monday = baseDate.with(DayOfWeek.MONDAY);
        LocalDate friday = baseDate.with(DayOfWeek.FRIDAY);

        List<ReservationEntity> reservationsOfWeek = reservationRepository
                .findAllByRoom_RoomIdAndDateBetween(
                        roomId,
                        monday,
                        friday
                );

         return RoomTimeTableResponse.from(
                 room,
                 monday.datesUntil(friday.plusDays(1))
                         .map(date -> RoomTimeTableResponse.DailyReservation.from(
                                 date,
                                 reservationsOfWeek.stream()
                                         .filter(reservation -> reservation.getDate().equals(date))
                                         .map(reservation -> RoomTimeTableResponse.TimeReservations.from(
                                                 reservation.getStartTime(),
                                                 reservation.getEndTime(),
                                                 reservation.getStatus()
                                         ))
                                         .toList()
                         ))
                         .toList()
         );
    }

}
