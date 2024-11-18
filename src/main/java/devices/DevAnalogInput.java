package devices;

import enums.FilePath;

public class DevAnalogInput {
    private String header = "// #";
    private int id = 0;
    private String varList = "SIG";
    private String signal = varList + ".listDI";
    private final String cmd;
    private final String cfg;
    private final String state;
    private final String result;

    // Конструктор для инициализации всех полей
    public DevAnalogInput(int id, String name, String devName, AddrPLC signal) {
        this.header += setHeader(id, name);
        this.id = id;
        if (signal.isIntToReal()) {
            this.signal = "INT_TO_REAL(" + getAddr(signal.getAddrCodesysAnalog()) + ")";
        } else this.signal = getAddr(signal.getAddrCodesysAnalog());
        this.cmd = "CVL.cmdAI[" + id + "]";;
        this.cfg = "RVL.cfgAI[" + id + "]";
        this.state = "SVL.stateAI[" + id + "]";
        this.result = "IOL." + devName;
    }

    public DevAnalogInput(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        if (devOne.getSignal().isIntToReal()) {
            this.signal = "INT_TO_REAL(" + getAddr(devOne.getSignal().getAddrCodesysAnalog()) + ")";
        } else this.signal = getAddr(devOne.getSignal().getAddrCodesysAnalog());
        this.cmd = "CVL.cmdAI[" + id + "]";
        this.cfg = "RVL.cfgAI[" + id + "]";
        this.state = "SVL.stateAI[" + id + "]";
        this.result = "IOL." + devOne.getDevName();
    }

    private String getAddr (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listAI" + addr;
    }

    // Геттеры для каждого поля
    public int getId() {
        return id;
    }

    public String getSignal() {
        return signal;
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
                        "ID := %d;\n" +
                        "drvAI[ID](\n" +
                        "   signal      := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        "   result      => %s);\n",
                header, id, signal, cmd, cfg, state, result
        );

        baseOutput += "\n";

        return baseOutput;
    }
}
