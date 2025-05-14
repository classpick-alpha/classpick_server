package com.github.classpick.lecture.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(staticName = "of")
public class LectureListResponse {

    Collection<LectureResponse> lectures;
}
