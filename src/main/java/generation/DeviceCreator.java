package generation;

import devices.*;
import devicesDB.*;
import enums.eDevType;
import enums.eProtocol;
import org.apache.poi.ss.usermodel.*;
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
            case VALVE -> {
                devOne = createDevValve(row);
                DevValve devValve = new DevValve(id, devOne);
                ValveDatabase.getInstance().addRecord(devValve);
                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getTypeName() + "\"", comment);
            }
            case AI -> {
                devOne = createDevAnalogInput(row);
                DevAnalogInput devAnalogInput = new DevAnalogInput(id, devOne);
                AnalogInputDatabase.getInstance().addRecord(devAnalogInput);
                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getTypeName() + "\"", comment);
            }
            case AO -> {
                devOne = createDevAnalogOutput(row);
                DevAnalogOutput devAnalogOutput = new DevAnalogOutput(id, devOne);
                AnalogOutputDatabase.getInstance().addRecord(devAnalogOutput);
                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getTypeName() + "\"", comment);
            }
            case DI -> {
                devOne = createDevDiscreteInput(row);
                DevDiscreteInput devDiscreteInput = new DevDiscreteInput(id, devOne);
                DiscreteInputDatabase.getInstance().addRecord(devDiscreteInput);
                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getTypeName() + "\"", comment);
            }
            case DO -> {
                devOne = createDevDiscreteOutput(row);
                DevDiscreteOutput devDiscreteOutput = new DevDiscreteOutput(id, devOne);
                DiscreteOutputDatabase.getInstance().addRecord(devDiscreteOutput);
                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getTypeName() + "\"", comment);
            }
        }
        assert devOne != null;
        IOLdatabase.getInstance().addRecord(record);
    };

    private DevOne createDevMotor (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrQF = getCellAsAddr(row, 3);
        AddrPLC addrKM = getCellAsAddr(row, 4);
        AddrPLC addrFW = getCellAsAddr(row, 7);
        return new DevOne(name, devName, addrQF, addrKM, addrFW);
    }

    private DevOne createDevValve (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrQF = getCellAsAddr(row, 3);
        AddrPLC addrFbOpen = getCellAsAddr(row, 5);
        AddrPLC addrFbClose = getCellAsAddr(row, 6);
        AddrPLC addrCmdOpen = getCellAsAddr(row, 7);
        AddrPLC addrCmdClose = getCellAsAddr(row, 8);
        return new DevOne(name, devName, addrQF, addrFbOpen, addrFbClose, addrCmdOpen, addrCmdClose);
    }

    private DevOne createDevAnalogInput (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrSignal = getCellAsAddr(row, 3);
        if (!addrSignal.isUse()) {
            addrSignal = getCellAsAddr(row, 4);
            addrSignal.setIntToReal(true);
        }
        return new DevOne(name, devName, addrSignal);
    }

    private DevOne createDevAnalogOutput (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrSignal = getCellAsAddr(row, 7);
        if (!addrSignal.isUse()) {
            addrSignal = getCellAsAddr(row, 8);
            addrSignal.setIntToReal(true);
        }
        return new DevOne(name, devName, addrSignal);
    }

    private DevOne createDevDiscreteInput (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrSignal = getCellAsAddr(row, 3);
        return new DevOne(name, devName, addrSignal);
    }

    private DevOne createDevDiscreteOutput (Row row) {
        String name = row.getCell(0).getStringCellValue();
        String devName = row.getCell(1).getStringCellValue();
        AddrPLC addrSignal = getCellAsAddr(row, 7);
        return new DevOne(name, devName, addrSignal);
    }

    private boolean nextSection (String cellValue, eDevType devType) {
        eDevType newType = findByValue(cellValue);
        return !newType.equals(EMPTY) && !newType.equals(devType);
    }

    private static boolean isDeviceTypeHeader(String cellValue, eDevType deviceType) {
        boolean result = false;
        switch (deviceType) {
            case EMPTY, PID -> {System.out.println("not found " + deviceType.getValue());}
            case MOTOR -> result = cellValue.equalsIgnoreCase(MOTOR.getValue());
            case VALVE -> result = cellValue.equalsIgnoreCase(VALVE.getValue());
            case AI -> result = cellValue.equalsIgnoreCase(AI.getValue());
            case AO -> result = cellValue.equalsIgnoreCase((AO.getValue()));
            case DI -> result = cellValue.equalsIgnoreCase((DI.getValue()));
            case DO -> result = cellValue.equalsIgnoreCase((DO.getValue()));
        };
        return result;
    }

    private AddrPLC getCellAsAddr(Row row, int cellId) {
        Cell cell = row.getCell(cellId);
        String cellValue = null;
        if (cell != null && cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue();
            if (value != null && !value.isEmpty()) {
                cellValue = value;
            }
        }
        return (cellValue != null) ? new AddrPLC(cell.getStringCellValue()) : new AddrPLC();
    }

}