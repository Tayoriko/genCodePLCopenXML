package alarmsGen;

import enums.eDevType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import enums.eVarLists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static enums.eDevType.*;


public class AlarmCreator {

    // Метод для создания набора аварий для заданного типа устройства
    public static List<AlarmConfig> createAlarmsFromExcel(String filePath, eDevType deviceType, Set<Map.Entry<String, String>> alarmSet) throws IOException {
        List<AlarmConfig> alarms = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean inTargetSection = false;
            int sequenceNumber = 1;
            String devName = "";

            for (Row row : sheet) {
                Cell firstCell = row.getCell(0);
                if (firstCell == null) continue;

                String cellValue = firstCell.getStringCellValue().trim();

                // Определяем начало нужного раздела
                if (isDeviceTypeHeader(cellValue, deviceType)) {
                    inTargetSection = true;
                    continue;
                }

                // Проверяем, что мы в нужном разделе
                if (inTargetSection) {
                    if (cellValue.isEmpty()) {
                        break; // Конец раздела
                    }

                    String deviceName = cellValue;
                    String variableName = getCellValue(row.getCell(1));

                    for (Map.Entry<String, String> alarm : alarmSet) {
                        String addressRead;

                        // Проверяем значение второго столбца
                        if (variableName.equals("0") || variableName.isEmpty()) {
                            // Формируем имя переменной на основе шаблона и последовательного номера
                            String template = getTemplateForDeviceType(deviceType);
                            if (!deviceName.equals(devName)) {devName = deviceName; sequenceNumber++;}
                            addressRead = "Application.SVL." + eVarLists.status.getName() + template + "[" + sequenceNumber + "]." + alarm.getKey();
                        } else {
                            addressRead = "Application.SVL." + eVarLists.status.getName() + variableName + "." + alarm.getKey();
                        }

                        // Создаем объект AlarmConfig
                        String contentWithDeviceName = devName + " - " + alarm.getValue();
                        AlarmConfig alarmConfig = new AlarmConfig(addressRead, contentWithDeviceName);
                        alarms.add(alarmConfig);
                    }
                }
            }
        }

        return alarms;
    }

    private static boolean isDeviceTypeHeader(String cellValue, eDevType deviceType) {
        return switch (deviceType) {
            case AI -> cellValue.equalsIgnoreCase("Analog Input");
            case EMPTY -> false;
            case MOTOR -> cellValue.equalsIgnoreCase("Motor");
            case VALVE -> cellValue.equalsIgnoreCase("Valve");
            // Добавьте проверки для других типов устройств
            case PID -> false;
            case AO -> false;
            case DI -> false;
            case DO -> false;
        };
    }

    private static String getTemplateForDeviceType(eDevType deviceType) {
        return switch (deviceType) {
            case AI -> AI.getName();
            case EMPTY -> null;
            case MOTOR -> MOTOR.getName();
            case VALVE -> VALVE.getName();
            // Добавьте шаблоны для других типов устройств
            case PID -> null;
            case AO -> null;
            case DI -> null;
            case DO -> null;
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
}