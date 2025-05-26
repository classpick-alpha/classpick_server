package com.github.classpick.room.util;

import com.github.classpick.room.exception.RoomException;
import com.github.classpick.room.exception.RoomExceptionCode;
import com.github.classpick.room.repository.RoomEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoomExcelParser {

    private static final int COL_PLACE_NAME = 0;
    private static final int COL_UNIT_NUMBER = 1;
    private static final int COL_CAPACITY = 2;
    private static final int COL_ALIAS = 3;
    private static final int COL_IMAGE = 4;

    public static List<RoomEntity> parse(InputStream is) throws IOException {

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<RoomEntity> rooms = new ArrayList<>();

            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isRowEmpty(row)) {
                    continue;
                }

                RoomEntity room = parseRow(row);
                rooms.add(room);
            }

            return rooms;
        } catch (IOException e) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_PARSING_ERROR);
        }
    }

    private static boolean isRowEmpty(Row row) {

        for (Cell cell : row) {
            if (cell.getCellType() != CellType.BLANK &&
                    !(cell.getCellType() == CellType.STRING && cell.getStringCellValue().isBlank())) {
                return false;
            }
        }
        return true;
    }

    private static RoomEntity parseRow(Row row) {

        String placeName = getCellValue(row.getCell(COL_PLACE_NAME));
        String unitNumber = getCellValue(row.getCell(COL_UNIT_NUMBER));

        if (placeName == null || placeName.isBlank()) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_PLACENAME_MISSING);
        }

        if (unitNumber == null || unitNumber.isBlank()) {
            throw new RoomException(RoomExceptionCode.ROOM_EXCEL_UNITNUMBER_MISSING);
        }

        Integer capacity = getCellValueAsInt(row.getCell(COL_CAPACITY));
        String alias = getCellValue(row.getCell(COL_ALIAS));
        String image = getCellValue(row.getCell(COL_IMAGE));

        return RoomEntity.builder()
                .placeName(placeName.trim())
                .unitNumber(unitNumber.trim())
                .capacity(capacity)
                .alias(alias != null && !alias.isBlank() ? alias.trim() : null)
                .image(image != null && !image.isBlank() ? image.trim() : null)
                .build();
    }

    private static String getCellValue(Cell cell) {

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        return switch (cell.getCellType()) {
            case STRING -> {
                String value = cell.getStringCellValue().trim();
                yield value.isEmpty() ? null : value;
            }
            case NUMERIC -> {
                double numericValue = cell.getNumericCellValue();
                yield (numericValue % 1 == 0) ? String.valueOf((int) numericValue) : String.valueOf(numericValue);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }


    private static Integer getCellValueAsInt(Cell cell) {

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
