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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND.getMessage(),
                                ROOM_NOT_FOUND.getCode()));
        LocalDate baseDate = dto.date() != null ? dto.date() : LocalDate.now();
        LocalDate monday = baseDate.with(DayOfWeek.MONDAY);
        LocalDate friday = baseDate.with(DayOfWeek.FRIDAY);

        List<ReservationEntity> reservationsOfWeek = reservationRepository
                .findAllByRoom_RoomIdAndDateBetween(
                        roomId,
                        monday,
                        friday
                );

        Map<LocalDate, List<ReservationEntity>> reservationsByDate = reservationsOfWeek.stream()
                .collect(Collectors.groupingBy(ReservationEntity::getDate));

        List<LocalDate> datesOfWeek = monday.datesUntil(friday.plusDays(1))
                .toList();

        List<RoomTimeTableResponse.DailyReservation> dailyReservations = new ArrayList<>();

        for (LocalDate date : datesOfWeek) {
            List<ReservationEntity> reservationsOnDate = reservationsByDate.getOrDefault(date, List.of());

            List<RoomTimeTableResponse.TimeReservations> timeReservations = new ArrayList<>();
            for (ReservationEntity reservation : reservationsOnDate) {
                timeReservations.add(RoomTimeTableResponse.TimeReservations.from(
                        reservation.getStartTime(),
                        reservation.getEndTime(),
                        reservation.getStatus()
                ));
            }
            dailyReservations.add(RoomTimeTableResponse.DailyReservation.from(date, timeReservations));
        }
        return RoomTimeTableResponse.from(room, dailyReservations);
    }

}
