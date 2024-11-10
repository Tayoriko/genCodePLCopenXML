package alarmsGen;

import alarmsBase.AlarmMessage;
import enums.eDevType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import enums.eProtocol;
import enums.eRegex;
import enums.eVarLists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static enums.eDevType.*;


public class AlarmCreator {
    private String nameVarList = "";
    private String prefix = "";
    private String varName = "";
    private String alarmName = "";
    private String deviceName = "";
    private String devName = "";

    public AlarmCreator (File source, eProtocol protocol) throws IOException {
        Sheet sheet = openSheet(source);
        grabData(protocol, sheet);
        reviewDevices(sheet, protocol, AI);
        reviewDevices(sheet, protocol, MOTOR);
        reviewDevices(sheet, protocol, VALVE);
    }

    private Sheet openSheet (File source) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             Workbook workbook = new XSSFWorkbook(fis)) {
            return workbook.getSheetAt(0);
        }
    }

    public void createMessageCodesys (String variableName, eDevType devType) {
        Set<Map.Entry<String, String>> alarmSet = Set.of();
        switch (devType){
            case MOTOR -> alarmSet = AlarmSets.getMotorAlarmSet();
            case VALVE -> alarmSet = AlarmSets.getValveAlarmSet();
            case AI -> alarmSet = AlarmSets.getAnalogAlarmSet();
        }
        for (Map.Entry<String, String> alarm : alarmSet) {
            int sequenceNumber = 1;
            // Формируем шаблонное обращение к переменной
            String addressRead = "Application." + nameVarList + "." + prefix;
            alarmName = varName + "." + alarm.getKey();
            // Проверяем значение второго столбца
            if (variableName.equals("0") || variableName.isEmpty()) {
                // Формируем имя переменной на основе шаблона и последовательного номера
                String template = getTemplateForDeviceType(devType);
                if (!deviceName.equals(devName)) {devName = deviceName; sequenceNumber++;}
                addressRead +=  template + "[" + sequenceNumber + "]." + alarmName;
            } else {
                addressRead += variableName + "." + alarmName;
            }
            System.out.println(addressRead);
            // Создаем объект AlarmConfig
            String contentWithDeviceName = devName + " - " + alarm.getValue();
            System.out.println(contentWithDeviceName);
            AlarmMessage alarmConfig = new AlarmMessage(addressRead, contentWithDeviceName);
            AlarmDatabase.getInstance().addAlarm(alarmConfig);
        }
    };


    private void grabDataCodesys (Sheet sheet) {
        nameVarList = getCell(sheet, 0,1);
        prefix = getCell(sheet, 1, 1);
        varName = getCell(sheet, 2, 1);
    }

    private void grabData (eProtocol protocol, Sheet sheet) {
        if (Objects.requireNonNull(protocol) == eProtocol.CODESYS) {
            grabDataCodesys(sheet);
        }
    }

    private void reviewDevices (Sheet sheet, eProtocol protocol, eDevType devType) {
            boolean inTargetSection = false;
            for (Row row : sheet) {
                Cell firstCell = row.getCell(0);
                if (firstCell == null) continue;
                String cellValue = firstCell.getStringCellValue().trim();
                // Определяем начало нужного раздела
                if (isDeviceTypeHeader(cellValue, devType)) {
                    inTargetSection = true;
                    continue;
                }

                // Проверяем, что мы в нужном разделе
                if (inTargetSection) {
                    if (cellValue.isEmpty() || nextSection(cellValue, devType)) {
                        break; // Конец раздела
                    }
                    deviceName = cellValue;
                    //System.out.println(deviceName);
                    String variableName = getCellValue(row.getCell(1));
                    switch (protocol) {
                        case CODESYS -> createMessageCodesys(variableName, devType);
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
            case EMPTY, PID, AO, DI, DO -> {System.out.println("not found " + deviceType.getValue());}
            case MOTOR -> result = cellValue.equalsIgnoreCase(MOTOR.getValue());
            case VALVE -> result = cellValue.equalsIgnoreCase(VALVE.getValue());
            case AI -> result = cellValue.equalsIgnoreCase(AI.getValue());
        };
        return result;
    }

    private static String getTemplateForDeviceType(eDevType deviceType) {
        return switch (deviceType) {
            case EMPTY, PID, AO, DI, DO -> "null";
            case MOTOR -> MOTOR.getName();
            case VALVE -> VALVE.getName();
            case AI -> AI.getName();
        };
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "0"; // Обработка пустой ячейки как "0"
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()); // Приведение числового значения к int
            default -> "0";
        };
    }

    private String getCell (Sheet sheet, int rowID, int cellID){
        Row row = sheet.getRow(rowID);
        Cell cell = row.getCell(cellID);
        return cell.getStringCellValue().trim();
    }
}