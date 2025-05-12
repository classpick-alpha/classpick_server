package com.github.classpick.file.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(staticName = "of")
public class UploadImageResponse {

    String url;
}
