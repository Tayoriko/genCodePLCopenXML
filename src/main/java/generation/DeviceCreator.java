package generation;

import enums.eDevType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import static enums.eDevType.*;


public class DeviceCreator {
    private final Sheet sheet;
    private static Set<eDevType> devices;

    public DeviceCreator(File source, Set<eDevType> devices) throws IOException {
        this.sheet = openSheet(source);
        devices.forEach(this::reviewDevice);
    }

    private Sheet openSheet (File source) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             Workbook workbook = new XSSFWorkbook(fis)) {
            return workbook.getSheetAt(0);
        }
    }

    public void reviewDevice(eDevType devType) {
        boolean inTargetSection = false;
        for (Row row : this.sheet) {
            Cell firstCell = row.getCell(0);
            if (firstCell == null) continue;
            String cellValue = firstCell.getStringCellValue().trim();
            // Определяем начало нужного раздела
            if (isDeviceTypeHeader(cellValue, devType)) {
                System.out.println(cellValue);
                inTargetSection = true;
                continue;
            }
            // Проверяем, что мы в нужном разделе
            if (inTargetSection) {
                if (cellValue.isEmpty() || nextSection(cellValue, devType)) {
                    break; // Конец раздела
                }
                CreateRecords.createDeviceCodesys(row, devType);
            }
        }
    }

    private boolean nextSection (String cellValue, eDevType devType) {
        eDevType newType = findByValue(cellValue);
        return !newType.equals(EMPTY) && !newType.equals(devType);
    }

    private static boolean isDeviceTypeHeader(String cellValue, eDevType deviceType) {
        boolean result = false;
        switch (deviceType) {
            case EMPTY -> {System.out.println("not found " + deviceType.getValue());}
            case MOTOR -> result = cellValue.equalsIgnoreCase(MOTOR.getValue());
            case VALVE -> result = cellValue.equalsIgnoreCase(VALVE.getValue());
            case AI -> result = cellValue.equalsIgnoreCase(AI.getValue());
            case AO -> result = cellValue.equalsIgnoreCase((AO.getValue()));
            case DI -> result = cellValue.equalsIgnoreCase((DI.getValue()));
            case DO -> result = cellValue.equalsIgnoreCase((DO.getValue()));
            case PID -> result = cellValue.equalsIgnoreCase((PID.getValue()));
            case FLOW -> result = cellValue.equalsIgnoreCase((FLOW.getValue()));
        };
        return result;
    }

    private String getCellAsString(Row row, int cellId) {
        Cell cell = row.getCell(cellId);
        String cellValue = null;
        if (cell != null && cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue();
            if (value != null && !value.isEmpty()) {
                cellValue = value;
            }
        }
        return (cellValue != null) ? cellValue : "unknown device";
    }

}