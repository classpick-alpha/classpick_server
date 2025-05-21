package com.github.classpick.room.service;

import com.github.classpick.reservation.repository.ReservationEntity;
import com.github.classpick.reservation.repository.ReservationRepository;
import com.github.classpick.room.controller.dto.request.RoomFilterRequest;
import com.github.classpick.room.controller.dto.response.RoomListResponse;
import com.github.classpick.room.controller.dto.response.RoomResponse;
import com.github.classpick.room.controller.dto.response.RoomTimeTableResponse;
import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.exception.RoomExceptionCode;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.classpick.user.exception.UserException;
import com.github.classpick.user.exception.UserExceptionCode;
import com.github.classpick.user.repository.GroupRepository;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public RoomListResponse getRoomList(RoomFilterRequest dto, Long userId) {

        UserEntity savedUserEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));
        Long groupId = savedUserEntity.getGroup().getGroupId();

        List<RoomResponse> rooms = roomRepository.findAllWithFilter(
                groupId,
                        dto.getPlaceName(),
                        dto.getCapacity(),
                        dto.getDate(),
                        dto.getStartTime(),
                        dto.getEndTime()
                )
                .stream()
                .map(RoomResponse::from)
                .toList();

        return RoomListResponse.of(rooms);
    }

    @Transactional(readOnly = true)
    public RoomTimeTableResponse getRoomTimeTable(Long roomId, LocalDate baseDate) {

        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomExceptionCode.ROOM_NOT_FOUND));

        LocalDate monday = baseDate.with(DayOfWeek.MONDAY);
        LocalDate friday = baseDate.with(DayOfWeek.FRIDAY);

        Map<LocalDate, List<ReservationEntity>> reservationsByDate =
                reservationRepository.findAllByRoom_RoomIdAndDateBetween(
                    roomId,
                    monday,
                    friday
        ).stream().collect(Collectors.groupingBy(ReservationEntity::getDate));

        List<RoomTimeTableResponse.DailyReservation> dailyReservations = monday.datesUntil(friday.plusDays(1))
                .map(date -> RoomTimeTableResponse.DailyReservation.of(
                        date,
                        reservationsByDate.getOrDefault(date, List.of())
                                .stream()
                                .map(reservation -> RoomTimeTableResponse.TimeReservations.of(
                                        reservation.getStartTime(),
                                        reservation.getEndTime(),
                                        reservation.getStatus(),
                                        RoomTimeTableResponse.TimeReservations.User.of(
                                                reservation.getUser().getUserId(),
                                                reservation.getUser().getName()
                                        )
                                ))
                                .toList()
                ))
                .toList();

        return RoomTimeTableResponse.of(RoomResponse.from(room), dailyReservations);
    }
}
