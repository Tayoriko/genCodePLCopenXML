package devPLC;

import databases.DatabaseRegistry;
import databases.GData;
import dev.*;
import enums.eActions;
import enums.eDevType;
import enums.eVarType;

import java.util.List;

public class CodesysCallDevices {

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
            case PID -> {
                List<DevPID> valveRecords = DatabaseRegistry.getInstance(DevPID.class).getRecords();
                valveRecords.forEach(dev -> result.append(CodesysCallDevices.callPid(dev)));
            }
            case FLOW -> {
                List<DevFlow> valveRecords = DatabaseRegistry.getInstance(DevFlow.class).getRecords();
                valveRecords.forEach(dev -> result.append(CodesysCallDevices.callFlow(dev)));
            }
            default -> System.out.println("skip " + deviceType.getName());
        }

        return result;
    }

    private static StringBuilder callNetData(String devName) {
        StringBuilder netData = new StringBuilder();
        if (GData.getActions().contains(eActions.MBS)) {
            netData.append(String.format("   netData     => %s,\n", CodesysAddressing.getNetList(devName)));
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
                        "   state       => %s,\n",
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
                        "   fbQf        := %s,\n",
                devAO.getHeader(),
                devAO.getId()
        ));
        if (devAO.isUseQf()){
            device.append(String.format(
                        "   fbQf        := %s,\n",
                    CodesysAddressing.getAddrDi(devAO.getFbQf())
            ));
        }
        device.append(String.format(
                        "   devState    := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       => %s,\n",
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
                    "   resultI     := %s);\n",
                    CodesysAddressing.getAddrAo(devAO.getResult())
            ));
        }
        if (devAO.isUseQf()){
            device.append(checkInput(CodesysAddressing.getAddrDi(devAO.getFbQf()), "cfg.useQF", devAO));
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
                        "   state       => %s,\n",
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
        device.append(checkInput(CodesysAddressing.getAddrDi(devMotor.getFbQf()), "cfg.useQF", devMotor));
        device.append(checkInput(CodesysAddressing.getAddrDi(devMotor.getFbKm()), "cfg.useKM", devMotor));
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
                        "   state       => %s,\n",
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
        device.append(checkInput(CodesysAddressing.getAddrDi(devValve.getFbQf()), "cfg.useQF", devValve));
        device.append(checkInput(CodesysAddressing.getAddrDi(devValve.getFbOpen()), "cfg.useFbOpen", devValve));
        device.append(checkInput(CodesysAddressing.getAddrDi(devValve.getFbClose()), "cfg.useFbClose", devValve));
        device.append("\n");
        return device;
    }

    public static StringBuilder callPid(DevPID devPID) {
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvPID[%d](\n" +
                        "   signal      := %s,\n" +
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       => %s,\n",
                devPID.getHeader(),
                devPID.getId(),
                CodesysAddressing.getIoList(devPID.getSignal()),
                devPID.getCmd(),
                devPID.getCfg(),
                devPID.getState()
        ));
        device.append(String.format(
                        "   result      := %s);\n",
                CodesysAddressing.getIoList(devPID.getResult())
        ));
        device.append("\n");
        return device;
    }

    public static StringBuilder callFlow(DevFlow devFlow) {
        String options;
        StringBuilder device = new StringBuilder(String.format(
                "%s\n" +
                        "drvFlow[%d](\n",
                devFlow.getHeader(),
                devFlow.getId()
        ));
        if (devFlow.getVarType().equals(eVarType.BOOL)){
            options = devFlow.getCfg() + ".cfg.useDI := TRUE;\n";
            device.append(String.format(
                        "   signalDi    := %s,\n" +
                        "   signalAi    := zero,\n",
                    CodesysAddressing.getIoList(devFlow.getSignal())
            ));
        } else {
            options = devFlow.getCfg() + ".cfg.useDI := FALSE;\n";
            device.append(String.format(
                        "   signalDi    := empty,\n" +
                        "   signalAi    := %s,\n",
                    CodesysAddressing.getIoList(devFlow.getSignal())
            ));
        }
        device.append(String.format(
                        "   cmd         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       => %s,\n",
                devFlow.getCmd(),
                devFlow.getCfg(),
                devFlow.getState()
        ));
        device.append(callNetData(devFlow.getDevName()));
        device.append(String.format(
                        "   PreDone     => (*command for dosing mode for switch to low speed*),\n" +
                        "   Done        => (*command for dosing mode for finish*),\n" +
                        "   result      => %s);\n",
                CodesysAddressing.getIoList(devFlow.getDevName())
        ));
        device.append(options);
        device.append("\n");
        return device;
    }

    public static StringBuilder callRaw(AbstractDevice dev) {
        StringBuilder device = new StringBuilder(String.format(dev.toString()));
        device.append("\n");
        return device;
    }

    private static StringBuilder checkInput(String addr, String cfg, AbstractDevice dev) {
        if (addr.equals(CodesysAddressing.noData)) return disableInput(cfg, dev);
        else return new StringBuilder();
    }

    private static StringBuilder disableInput(String cfg, AbstractDevice dev) {
        return new StringBuilder(dev.getCfg() + "." + cfg + " := FALSE;\n");
    }
}
