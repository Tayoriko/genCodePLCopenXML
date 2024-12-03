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
    private int cnt;

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
        boolean foundDevType = false; // Флаг, указывающий, что devType найден
        System.out.println("Search: " + devType.getValue());
        for (Row row : sheet) {
            Cell firstCell = row.getCell(0); // Первая ячейка строки
            String cellValue = firstCell.getStringCellValue().trim();
            if (firstCell == null) continue;
            // Определяем начало нужного раздела
            if (isDeviceTypeHeader(cellValue, devType)) {
                System.out.println("Start: " + devType.getValue());
                foundDevType = true;
                continue;
            }
            //обработка данных
            if (foundDevType) {
                if (cellValue.isEmpty() || nextSection(cellValue, devType)) {
                    System.out.println("End: " + devType.getValue());
                    break; // Конец раздела
                } else {
                    CreateRecords.createDeviceCodesys(row, devType);
                }
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

}