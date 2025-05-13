package com.github.classpick.file.default_file.controller.dto;

import com.github.classpick.file.default_file.repository.DefaultFileEntity;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class GetDefaultFileInfoRes {

    private final Long fileId;
    private final String fileName;
    private final String filePath;

    public static List<GetDefaultFileInfoRes> of(List<DefaultFileEntity> fileList) {

        return fileList.stream().map(entity -> of(entity.getId(), entity.getFileName(), entity.getFilePath())).toList();
    }
}
