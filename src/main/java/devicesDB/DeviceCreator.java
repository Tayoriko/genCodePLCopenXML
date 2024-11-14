package devicesDB;

import devices.AddrPLC;
import devices.DevMotor;
import devices.DevOne;
import devices.IOLrecord;
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

import static enums.eDevType.*;


public class DeviceCreator {
    private final Sheet sheet;

    public DeviceCreator(File source, eProtocol protocol) throws IOException {
        sheet = openSheet(source);
    }

    private Sheet openSheet (File source) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             Workbook workbook = new XSSFWorkbook(fis)) {
            return workbook.getSheetAt(0);
        }
    }

    public void reviewDevice(eProtocol protocol, eDevType devType) {
        boolean inTargetSection = false;
        int cnt = 0;
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
                switch (protocol) {
                    case CODESYS -> createDeviceCodesys(row, devType);
                }
            }
        }
    }

    public void createDeviceCodesys(Row row, eDevType devType) {
        int id = (int) row.getCell(2).getNumericCellValue();
        String comment = row.getCell(9).getStringCellValue();
        IOLrecord record = null;
        DevOne devOne = null;
        switch (devType) {
            case MOTOR -> {
                devOne = createDevMotor(row);
                DevMotor devMotor = new DevMotor(id, devOne);
                MotorDatabase.getInstance().addRecord(devMotor);
                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getTypeName() + "\"", comment);
            }
        }
        assert devOne != null;
        IOLdatabase.getInstance().addRecord(record);
    };

    private DevOne createDevMotor (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrQF = new AddrPLC(row.getCell(3).getStringCellValue());
        AddrPLC addrKM = new AddrPLC(row.getCell(4).getStringCellValue());
        AddrPLC addrFW = new AddrPLC(row.getCell(7).getStringCellValue());
        return new DevOne(name, devName, addrQF, addrKM, addrFW);
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

}