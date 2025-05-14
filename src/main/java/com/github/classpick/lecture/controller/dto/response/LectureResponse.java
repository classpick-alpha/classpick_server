package com.github.classpick.lecture.controller.dto.response;

import com.github.classpick.lecture.repository.LectureEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class LectureResponse {

    String name;
    String professor;
    DayOfWeek dow;
    LocalTime startTime;
    LocalTime endTime;

    public static LectureResponse from(LectureEntity lecture) {

        return of(
                lecture.getName(),
                lecture.getProfessor(),
                lecture.getDow(),
                lecture.getStartTime(),
                lecture.getEndTime()
        );
    }
}
