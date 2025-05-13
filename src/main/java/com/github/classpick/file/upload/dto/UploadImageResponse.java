package com.github.classpick.file.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UploadImageResponse {

    String url;
}
