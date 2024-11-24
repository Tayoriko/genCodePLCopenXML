package devices;

import enums.FilePath;

public class DevFlowMeter {
    private String header = "// #";
    private int id = 0;
    private String varList = "SIG";
    private String signalDi = varList + ".listDI";
    private String signalAi = varList + ".listAI";
    private final String cmd;
    private final String cfg;
    private final String state;
    private final String result;

    public DevFlowMeter(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        if (devOne.getSignal().isBool()) {
            this.signalDi = getAddrDI(devOne.getSignal().getAddrCodesysDiscrete());
            this.signalAi = "zero";
        } else {
            this.signalAi = getAddrAI(devOne.getSignal().getAddrCodesysAnalog());
            this.signalDi = "empty";
        }
        this.cmd = "CVL.cmdFlow[" + id + "]";
        this.cfg = "RVL.cfgFlow[" + id + "]";
        this.state = "SVL.stateFlow[" + id + "]";
        this.result = "IOL." + devOne.getDevName();
    }

    private String getAddrDI (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listDI" + addr;
    }

    private String getAddrAI (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listAI" + addr;
    }

    // Геттеры для каждого поля
    public int getId() {
        return id;
    }

    public String getSignalDi() {
        return signalDi;
    }

    public String getSignalAi() {
        return signalAi;
    }

    public String getCmd() {
        return cmd;
    }

    public String getCfg() {
        return cfg;
    }

    public String getState() {
        return state;
    }


    private String setHeader (int id, String name) {
        String text = "";
        if (id < 100) {text += "0";}
        if (id < 10) {text += "0";}
        return text + id + " - " + name;
    }

    // Метод для отображения информации о команде двигателя
    @Override
    public String toString() {
        String baseOutput = String.format(
                "%s\n" +
                        "drvFlow[%d](\n" +
                        "   signalDI    := %s,\n" +
                        "   signalAI    := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        //"   PreDone     => (*command for dosing mode for switch to low speed*),\n",
                        //"   Done        => (*command for dosing mode about end of process*),\n",
                        "   result      => %s);\n",
                header, id, signalDi, signalAi, cmd, cfg, state, result
        );

        baseOutput += "\n";

        return baseOutput;
    }
}
