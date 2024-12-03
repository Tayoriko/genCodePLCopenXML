package generation;

import databases.DatabaseRegistry;
import databases.GenericDatabase;
import dev.*;
import enums.eDevType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class CreateRecords {

    public CreateRecords() {
    }

    public static void createDeviceCodesys(Row row, eDevType devType) {
        switch (devType) {
            case DI -> {
                DevDI dev = createDevDiscreteInput(row);
                DatabaseRegistry.getInstance(DevDI.class).addRecord(dev);
            }
            case DO -> {
                DevDO dev = createDevDiscreteOutput(row);
                DatabaseRegistry.getInstance(DevDO.class).addRecord(dev);
            }
            case AI -> {
                DevAI dev = createDevAnalogInput(row);
                DatabaseRegistry.getInstance(DevAI.class).addRecord(dev);
            }
            case AO -> {
                DevAO dev = createDevAnalogOutput(row);
                DatabaseRegistry.getInstance(DevAO.class).addRecord(dev);
            }
            case MOTOR -> {
                DevMotor dev = createDevMotor(row);
                DatabaseRegistry.getInstance(DevMotor.class).addRecord(dev);
            }
            case VALVE -> {
                DevValve dev = createDevValve(row);
                DatabaseRegistry.getInstance(DevValve.class).addRecord(dev);
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
        String addr = getCellAsAddr(row, 3);
        return new DevDI(createRawDev(row), addr);
    }

    private static DevDO createDevDiscreteOutput (Row row) {
        String addr = getCellAsAddr(row, 7);
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
