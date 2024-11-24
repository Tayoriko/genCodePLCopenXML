package alarmsGen;

import alarmsBase.AlarmMessage;
import enums.eDevType;
import enums.eProtocol;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static enums.eDevType.*;


public class AlarmCreator {
    private String nameVarList = "";
    private String prefix = "";
    private String varName = "";
    private String alarmName = "";
    private String devName = "";
    private final Sheet sheet;

    public AlarmCreator(File source, eProtocol protocol) throws IOException {
        sheet = openSheet(source);
        grabData(protocol, sheet);
    }

    private Sheet openSheet (File source) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             Workbook workbook = new XSSFWorkbook(fis)) {
            return workbook.getSheetAt(0);
        }
    }

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

    public void reviewAlarms(eProtocol protocol, eDevType devType) {
            boolean inTargetSection = false;
            for (Row row : this.sheet) {
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
                    String name = cellValue;
                    System.out.println(name);
                    int devId = (int) Double.parseDouble(getCellValue(row.getCell(2)));
                    System.out.println(getCellValue(row.getCell(0)));
                    System.out.println(getCellValue(row.getCell(1)));
                    System.out.println(getCellValue(row.getCell(2)));
                    System.out.println(getCellValue(row.getCell(3)));
                    //System.out.println(devId);
                    switch (protocol) {
                        case CODESYS -> createAlarmCodesys(name, devId, devType);
                    }
                }
            }
        }


    public void createAlarmCodesys(String name, int devId, eDevType devType) {
        Set<Map.Entry<String, String>> alarmSet = Set.of();
        switch (devType){
            case MOTOR -> alarmSet = AlarmSets.getMotorAlarmSet();
            case VALVE -> alarmSet = AlarmSets.getValveAlarmSet();
            case AI -> alarmSet = AlarmSets.getAnalogInputAlarmSet();
            case AO -> alarmSet = AlarmSets.getAnalogAlarmOutputSet();
        }
        for (Map.Entry<String, String> alarm : alarmSet) {
            // Формируем шаблонное обращение к переменной
            String addressRead = "Application." + nameVarList + "." + prefix;
            alarmName = varName + "." + alarm.getKey();
            // Формируем имя переменной на основе шаблона и последовательного номера
            String template = getTemplateForDeviceType(devType);
            if (!name.equals(devName)) {devName = name;}
            addressRead +=  template + "[" + devId + "]." + alarmName;
            // Создаем объект AlarmConfig
            String contentWithDeviceName = devName + " - " + alarm.getValue();
            AlarmMessage alarmConfig = new AlarmMessage(addressRead, contentWithDeviceName);
            AlarmDatabase.getInstance().addAlarm(alarmConfig);
        }
    };

    private boolean nextSection (String cellValue, eDevType devType) {
        eDevType newType = findByValue(cellValue);
        return !newType.equals(EMPTY) && !newType.equals(devType);
    }

    private static boolean isDeviceTypeHeader(String cellValue, eDevType deviceType) {
        boolean result = false;
        switch (deviceType) {
            case EMPTY, PID, DI, DO, FLOW -> {System.out.println("not found " + deviceType.getValue());}
            case MOTOR -> result = cellValue.equalsIgnoreCase(MOTOR.getValue());
            case VALVE -> result = cellValue.equalsIgnoreCase(VALVE.getValue());
            case AI -> result = cellValue.equalsIgnoreCase(AI.getValue());
            case AO -> result = cellValue.equalsIgnoreCase(AO.getValue());
        };
        return result;
    }

    private static String getTemplateForDeviceType(eDevType deviceType) {
        return switch (deviceType) {
            case EMPTY, PID, DI, DO, FLOW -> "null";
            case MOTOR -> MOTOR.getName();
            case VALVE -> VALVE.getName();
            case AI -> AI.getName();
            case AO -> AO.getName();
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