package devices;

import enums.FilePath;

public class DevAnalogOutput {
    private String header = "// #";
    private int id = 0;
    private String varList = "SIG";
    private final String cmd;
    private final String cfg;
    private final String state;
    private final String devState;
    private final String result;
    private String resultType = "resultR";


    // Конструктор для инициализации всех полей
    public DevAnalogOutput(int id, String name, String devName, AddrPLC result) {
        this.header += setHeader(id, name);
        this.id = id;
        this.devState = "IOL." + devName;
        this.cmd = "CVL.cmdAO[" + id + "]";;
        this.cfg = "RVL.cfgAO[" + id + "]";
        this.state = "SVL.stateAO[" + id + "]";
        if (result.isIntToReal()) {resultType = "resultI";};
        this.result = getAddr(result.getAddrCodesysAnalog());
    }

    public DevAnalogOutput(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        this.devState = "IOL." + devOne.getDevName();
        this.cmd = "CVL.cmdAO[" + id + "]";
        this.cfg = "RVL.cfgAO[" + id + "]";
        this.state = "SVL.stateAO[" + id + "]";
        if (devOne.getSignal().isIntToReal()) {resultType = "resultI";};
        this.result = getAddr(devOne.getSignal().getAddrCodesysAnalog());
    }

    private String getAddr (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listAO" + addr;
    }

    // Геттеры для каждого поля
    public int getId() {
        return id;
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

    public String getDevState() {
        return devState;
    }

    public String getResult() {
        return result;
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
                        "drvAO[ID](\n" +
                        "   devState    := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        "   " + resultType + "      => %s);\n",
                header, id, devState, cmd, cfg, state, result
        );

        baseOutput += "\n";

        return baseOutput;
    }

}
