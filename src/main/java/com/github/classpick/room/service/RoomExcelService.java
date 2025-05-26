package com.github.classpick.room.service;

import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.exception.RoomExceptionCode;
import com.github.classpick.room.repository.RoomEntity;
import com.github.classpick.room.repository.RoomRepository;
import com.github.classpick.room.util.RoomExcelParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomExcelService {

    private static final String XLSX_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final RoomRepository roomRepository;

    @Transactional
    public void uploadRoomExcel(MultipartFile file) {

        validateFileFormat(file);

        try (InputStream is = file.getInputStream()) {
            List<RoomEntity> rooms = RoomExcelParser.parse(is);
            roomRepository.saveAll(rooms);
        } catch (IOException e) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_IO_ERROR);
        } catch (RuntimeException e) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_PARSING_ERROR);
        }
    }

    public void validateFileFormat(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_EMPTY_FILE);
        }
        String contentType = file.getContentType();
        if (!XLSX_TYPE.equals(contentType)) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_UNSUPPORTED_FORMAT);
        }
    }

}
