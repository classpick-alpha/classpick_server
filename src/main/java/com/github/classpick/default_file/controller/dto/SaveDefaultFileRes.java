package com.github.classpick.default_file.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveDefaultFileRes {

    private final String fileName;
    private final String filePath;

}
