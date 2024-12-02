package codesys;

import databases.DatabaseRegistry;
import dev.*;
import enums.eDevType;
import enums.eVarType;

import java.util.List;

public class CodesysCallDevices {
    private static boolean useNetData = false;

    public CodesysCallDevices() {
    }

    public static StringBuilder generateDeviceData(eDevType deviceType) {
        StringBuilder result = new StringBuilder();
        switch (deviceType) {
            case DI -> {
                List<DevDI> diRecords = DatabaseRegistry.getInstance(DevDI.class).getRecords();
                diRecords.forEach(dev -> result.append(CodesysCallDevices.callDi(dev)));
            }
            case DO -> {
                List<DevDO> doRecords = DatabaseRegistry.getInstance(DevDO.class).getRecords();
                doRecords.forEach(dev -> result.append(CodesysCallDevices.callDo(dev)));
            }
            case AI -> {
                List<DevAI> aiRecords = DatabaseRegistry.getInstance(DevAI.class).getRecords();
                aiRecords.forEach(dev -> result.append(CodesysCallDevices.callAi(dev)));
            }
            case AO -> {
                List<DevAO> aoRecords = DatabaseRegistry.getInstance(DevAO.class).getRecords();
                aoRecords.forEach(dev -> result.append(CodesysCallDevices.callAo(dev)));
            }
            case MOTOR -> {
                List<DevMotor> motorRecords = DatabaseRegistry.getInstance(DevMotor.class).getRecords();
                motorRecords.forEach(dev -> result.append(CodesysCallDevices.callMotor(dev)));
            }
            case VALVE -> {
                List<DevValve> valveRecords = DatabaseRegistry.getInstance(DevValve.class).getRecords();
                valveRecords.forEach(dev -> result.append(CodesysCallDevices.callValve(dev)));
            }
            default -> throw new IllegalArgumentException("Unsupported device type: " + deviceType);
        }

        return result;
    }


    public static boolean isUseNetData() {
        return useNetData;
    }

    public static void setUseNetData(boolean useNetData) {
        CodesysCallDevices.useNetData = useNetData;
    }

    private static StringBuilder callNetData(String devName) {
        StringBuilder netData = new StringBuilder();
        if (isUseNetData()) {
            netData.append(String.format("   netData     := %s,\n", CodesysAddressing.getNetList(devName)));
        }
        return netData;
    }

    public static StringBuilder callDi(DevDI devDI) {
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvDI[%d](\n" +
                        "   signal      := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n",
                devDI.getHeader(),
                devDI.getId(),
                CodesysAddressing.getAddrDi(devDI.getSignal()),
                devDI.getCmd(),
                devDI.getCfg()
        ));
        device.append(callNetData(devDI.getDevName()));
        device.append(String.format(
                "   result      => %s);\n",
                CodesysAddressing.getIoList(devDI.getDevName())
        ));
        device.append("\n");
        return device;
    }

    public static StringBuilder callAi(DevAI devAI) {
        String signalFormat = devAI.getVarType() == eVarType.INT
                ? "   signal      := INT_TO_REAL(%s),\n"
                : "   signal      := %s,\n";

        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvAI[%d](\n" +
                        signalFormat +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n",
                devAI.getHeader(),
                devAI.getId(),
                CodesysAddressing.getAddrAi(devAI.getSignal()),
                devAI.getCmd(),
                devAI.getCfg(),
                devAI.getState()
        ));

        device.append(callNetData(devAI.getDevName()));
        device.append(String.format(
                "   result      => %s);\n",
                CodesysAddressing.getIoList(devAI.getDevName())
        ));
        device.append("\n");
        return device;
    }

    public static StringBuilder callDo(DevDO devDo) {
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvDO[%d](\n" +
                        "   command     := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n",
                devDo.getHeader(),
                devDo.getId(),
                CodesysAddressing.getIoList(devDo.getDevName()),
                devDo.getCmd(),
                devDo.getCfg()
        ));
        device.append(callNetData(devDo.getDevName()));
        device.append(String.format(
                "   result      => %s);\n",
                CodesysAddressing.getAddrDo(devDo.getResult())
        ));
        device.append("\n");
        return device;
    }

    public static StringBuilder callAo(DevAO devAO) {
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvAO[%d](\n" +
                        "   command     := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n",
                devAO.getHeader(),
                devAO.getId(),
                CodesysAddressing.getIoList(devAO.getDevName()),
                devAO.getCmd(),
                devAO.getCfg(),
                devAO.getState()
        ));
        device.append(callNetData(devAO.getDevName()));
        if (devAO.getVarType() == eVarType.REAL) {
            device.append(String.format(
                    "   resultR     => %s);\n",
                    CodesysAddressing.getAddrAo(devAO.getResult())
            ));
        } else {
            device.append(String.format(
                    "   resultI     => %s);\n",
                    CodesysAddressing.getAddrAo(devAO.getResult())
            ));
        }
        device.append("\n");
        return device;
    }

    public static StringBuilder callMotor(DevMotor devMotor) {
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvM[%d](\n" +
                        "   devState    := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n",
                devMotor.getHeader(),
                devMotor.getId(),
                CodesysAddressing.getIoList(devMotor.getDevName()),
                devMotor.getCmd(),
                devMotor.getCfg(),
                devMotor.getState()
        ));
        device.append(callNetData(devMotor.getDevName()));
        device.append(String.format(
                        "   fbQF        := %s,\n" +
                        "   fbKM        := %s,\n" +
                        "   cmdFW       => %s);\n",
                CodesysAddressing.getAddrDi(devMotor.getFbQf()),
                CodesysAddressing.getAddrDi(devMotor.getFbKm()),
                CodesysAddressing.getAddrDo(devMotor.getCmdFw())
                ));
        device.append("\n");
        return device;
    }

    public static StringBuilder callValve(DevValve devValve) {
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvV[%d](\n" +
                        "   devState    := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n",
                devValve.getHeader(),
                devValve.getId(),
                CodesysAddressing.getIoList(devValve.getDevName()),
                devValve.getCmd(),
                devValve.getCfg(),
                devValve.getState()
        ));
        device.append(callNetData(devValve.getDevName()));
        device.append(String.format(
                        "   fbQF        := %s,\n" +
                        "   fbOpen      := %s,\n" +
                        "   fbClose     := %s,\n" +
                        "   cmdOpen     => %s,\n" +
                        "   cmdClose    => %s);\n",
                CodesysAddressing.getAddrDi(devValve.getFbQf()),
                CodesysAddressing.getAddrDi(devValve.getFbOpen()),
                CodesysAddressing.getAddrDi(devValve.getFbClose()),
                CodesysAddressing.getAddrDo(devValve.getCmdOpen()),
                CodesysAddressing.getAddrDo(devValve.getCmdClose())
        ));
        device.append("\n");
        return device;
    }
}
