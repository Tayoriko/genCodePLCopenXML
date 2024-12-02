package generation;

import databases.DatabaseRegistry;
import databases.GenericDatabase;
import dev.*;
import enums.eDevType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class CreateRecords {
    private static GenericDatabase<DevDI> diDatabase;
    private static GenericDatabase<DevDO> doDatabase;
    private static GenericDatabase<DevAI> aiDatabase;
    private static GenericDatabase<DevAO> aoDatabase;
    private static GenericDatabase<DevMotor> motorDatabase;
    private static GenericDatabase<DevValve> valveDatabase;

    public CreateRecords() {
        diDatabase = DatabaseRegistry.getInstance(DevDI.class);
        diDatabase.clear();
        doDatabase = DatabaseRegistry.getInstance(DevDO.class);
        doDatabase.clear();
        aiDatabase = DatabaseRegistry.getInstance(DevAI.class);
        aiDatabase.clear();
        aoDatabase = DatabaseRegistry.getInstance(DevAO.class);
        aoDatabase.clear();
        motorDatabase = DatabaseRegistry.getInstance(DevMotor.class);
        motorDatabase.clear();
        valveDatabase = DatabaseRegistry.getInstance(DevValve.class);
        valveDatabase.clear();
    }

    public static void createDeviceCodesys(Row row, eDevType devType) {
        switch (devType) {
            case DI -> {
                DevDI dev = createDevDiscreteInput(row);
                diDatabase.addRecord(dev);
            }
            case DO -> {
                DevDO dev = createDevDiscreteOutput(row);
                doDatabase.addRecord(dev);
            }
            case AI -> {
                DevAI dev = createDevAnalogInput(row);
                aiDatabase.addRecord(dev);
            }
            case AO -> {
                DevAO dev = createDevAnalogOutput(row);
                aoDatabase.addRecord(dev);
            }
            case MOTOR -> {
                DevMotor dev = createDevMotor(row);
                motorDatabase.addRecord(dev);
            }
            case VALVE -> {
                DevValve dev = createDevValve(row);
                valveDatabase.addRecord(dev);
            }
            default -> {
                break;
            }
//            case PID -> {
//                devOne = createDevPID(row, comment);
//                DevPid devPid = new DevPid(id, devOne);
//                PidDatabase.getInstance().addRecord(devPid);
//                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getVarType() + "\"", devOne.getCommentIOL(), id);
//            }
//            case FLOW -> {
//                devOne = createDevFlow(row, comment);
//                DevFlowMeter devFlowMeter = new DevFlowMeter(id, devOne);
//                FlowMetersDatabase.getInstance().addRecord(devFlowMeter);
//                record = new IOLrecord(devOne.getDevName(), "derived name=\"" + devType.getVarType() + "\"", devOne.getCommentIOL(), id);
//            }
        }
    };


    private static DevDI createDevDiscreteInput (Row row) {
        String addr = row.getCell(3).getStringCellValue();
        return new DevDI(createRawDev(row), addr);
    }

    private static DevDO createDevDiscreteOutput (Row row) {
        String addr = row.getCell(7).getStringCellValue();
        return new DevDO(createRawDev(row), addr);
    }

    private static DevAI createDevAnalogInput (Row row) {
        DevAI dev = new DevAI(createRawDev(row));
        String addr = getCellAsAddr(row, 3);
        if (addr.equals("empty")) {
            addr = getCellAsAddr(row, 4);
            dev.setSignalInt();
        }
        dev.setSignal(addr);
        return dev;
    }

    private static DevAO createDevAnalogOutput(Row row) {
        DevAO dev = new DevAO(createRawDev(row));
        String addr = getCellAsAddr(row, 3);
        if (addr.equals("empty")) {
            addr = getCellAsAddr(row, 4);
            dev.setResultInt();
        }
        dev.setResult(addr);
        return dev;
    }

    private static DevMotor createDevMotor (Row row) {
        String addrQF = getCellAsAddr(row, 3);
        String addrKM = getCellAsAddr(row, 4);
        String addrFW = getCellAsAddr(row, 7);
        return new DevMotor(createRawDev(row), addrQF, addrKM, addrFW);
    }

    private static DevValve createDevValve (Row row) {
        String addrQF = getCellAsAddr(row, 3);
        String addrFbOpen = getCellAsAddr(row, 5);
        String addrFbClose = getCellAsAddr(row, 6);
        String addrCmdOpen = getCellAsAddr(row, 7);
        String addrCmdClose = getCellAsAddr(row, 8);
        return new DevValve(createRawDev(row), addrQF, addrFbOpen, addrFbClose, addrCmdOpen, addrCmdClose);
    }

    private static RawDev createRawDev(Row row) {
        return new RawDev(row);
    }

    private static String getCellAsAddr(Row row, int cellId) {
        Cell cell = row.getCell(cellId);
        String cellValue = null;
        if (cell != null && cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue();
            if (value != null && !value.isEmpty()) {
                cellValue = value;
            }
        }
        return (cellValue != null) ? cellValue : "empty";
    }
}


//    private DevOne createDevPID (Row row, String comment) {
//        String name = row.getCell(0).getStringCellValue();
//        String devName = row.getCell(1).getStringCellValue();
//        String varInput = getCellAsString(row, 3);
//        String varOutput = getCellAsString(row, 7);
//        return new DevOne(name, comment, devName, varInput, varOutput);
//    }
//
//    private DevOne createDevFlow (Row row, String comment) {
//        String name = row.getCell(0).getStringCellValue();
//        String devName = row.getCell(1).getStringCellValue();
//        AddrPLC addrSignal = getCellAsAddr(row, 3);
//        if (!addrSignal.isUse()) {
//            addrSignal = getCellAsAddr(row, 4);
//            addrSignal.setBool(true);
//        }
//        return new DevOne(name, comment, devName, addrSignal);
//    }
