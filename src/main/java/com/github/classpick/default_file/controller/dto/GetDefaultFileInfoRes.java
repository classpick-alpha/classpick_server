package com.github.classpick.default_file.controller.dto;

import com.github.classpick.default_file.repository.DefaultFileEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class GetDefaultFileInfoRes {
    private final Long fileId;
    private final String fileName;
    private final String filePath;

    public static List<GetDefaultFileInfoRes> of(List<DefaultFileEntity> fileList) {
        return fileList.stream()
                .map(entity -> of(entity.getId(), entity.getFileName(), entity.getFilePath()))
                .toList();
    }
}
