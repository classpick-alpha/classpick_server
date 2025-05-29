package com.github.classpick.reservation.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor(staticName = "of")
public class NoshowListResponse {

    Collection<NoshowResponse> noshows;
}
