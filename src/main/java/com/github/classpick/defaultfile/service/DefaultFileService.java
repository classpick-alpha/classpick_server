package com.github.classpick.defaultfile.service;

import com.github.classpick.defaultfile.exception.DefaultFileException;
import com.github.classpick.defaultfile.exception.DefaultFileExceptionCode;
import com.github.classpick.defaultfile.repository.DefaultFileEntity;
import com.github.classpick.defaultfile.repository.DefaultFileRepository;
import com.github.classpick.user.exception.UserException;
import com.github.classpick.user.exception.UserExceptionCode;
import com.github.classpick.user.repository.UserEntity;
import com.github.classpick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultFileService {

    private final DefaultFileRepository defaultFileRepository;
    private final UserRepository userRepository;

    // TODO: userId를 직접 받는 것은 보안 상 위험함으로,
    //  추후 인증 구현 후 JWT 토큰에서 사용자 정보 추출하는 방식으로 수정
    public void saveDefaultFile(MultipartFile multipartFile, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND.getMessage(),
                        UserExceptionCode.USER_NOT_FOUND.getCode()));

        String fileName = multipartFile.getOriginalFilename();
        String filePath = "uploads/" + userId + "/" + fileName;

        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new DefaultFileException(
                        DefaultFileExceptionCode.FILE_SAVE_FAIL.getMessage(),
                        DefaultFileExceptionCode.FILE_SAVE_FAIL.getCode());
            }

            defaultFileRepository.save(
                    DefaultFileEntity.builder()
                            .fileName(fileName)
                            .filePath(filePath)
                            .user(userEntity)
                            .build());

        } catch (Exception e) {
            throw new DefaultFileException(
                    DefaultFileExceptionCode.FILE_SAVE_FAIL.getMessage(),
                    DefaultFileExceptionCode.FILE_SAVE_FAIL.getCode());
        }
    }

    // TODO: userId를 직접 받는 것은 보안 상 위험함으로,
    //  추후 인증 구현 후 JWT 토큰에서 사용자 정보 추출하는 방식으로 수정
    public List<DefaultFileEntity> getDefaultFile(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserException(
                        UserExceptionCode.USER_NOT_FOUND.getMessage(),
                        UserExceptionCode.USER_NOT_FOUND.getCode()));

        List<DefaultFileEntity> defaultFileEntities = defaultFileRepository.findAllByUser_UserId(userId);

        if (defaultFileEntities.isEmpty()) {
            throw new DefaultFileException(
                    DefaultFileExceptionCode.FILE_NOT_FOUND.getMessage(),
                    DefaultFileExceptionCode.FILE_NOT_FOUND.getCode());
        }

        return defaultFileEntities;
    }

    // TODO: fileID를 알 시 소유자와 관계없이 파일 삭제가 가능함으로,
    //  추후 인증 구현 후 JWT 토큰에서 유저 정보 확인 후 파일 삭제할 수 있도록 구현
    public void deleteDefaultFile(Long fileId) {
        DefaultFileEntity defaultFileEntity = defaultFileRepository.findById(fileId)
                .orElseThrow(() -> new DefaultFileException(
                        DefaultFileExceptionCode.FILE_NOT_FOUND.getMessage(),
                        DefaultFileExceptionCode.FILE_NOT_FOUND.getCode()));

        File file = new File(defaultFileEntity.getFilePath());

        if (file.exists()) {
            if(!file.delete()) {
                throw new DefaultFileException(
                        DefaultFileExceptionCode.FILE_DELETE_FAIL.getMessage(),
                        DefaultFileExceptionCode.FILE_DELETE_FAIL.getCode());
            }
        } else {
            throw new DefaultFileException(
                    DefaultFileExceptionCode.FILE_NOT_FOUND.getMessage(),
                    DefaultFileExceptionCode.FILE_NOT_FOUND.getCode());
        }

        defaultFileRepository.delete(defaultFileEntity);
    }
}
