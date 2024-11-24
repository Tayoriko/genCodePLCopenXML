package devices;

import enums.FilePath;

public class DevPid {
    private String header = "// #";
    private int id = 0;
    private String varList = "IOL";
    private String signal = varList + ".listPID";
    private final String cmd;
    private final String cfg;
    private final String state;
    private final String result;

    public DevPid(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        this.signal = "IOL." + devOne.getVarInput();
        this.cmd = "CVL.cmdPID[" + id + "]";
        this.cfg = "RVL.cfgPID[" + id + "]";
        this.state = "SVL.statePID[" + id + "]";
        this.result = "IOL." + devOne.getVarOutput();
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
        return text + id + " - PID " + name;
    }

    // Метод для отображения информации о команде двигателя
    @Override
    public String toString() {
        String baseOutput = String.format(
                "%s\n" +
                        "drvPID[%d](\n" +
                        "   signal      := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        "   result      := %s);\n",
                header, id, signal, cmd, cfg, state, result
        );

        baseOutput += "\n";

        return baseOutput;
    }
}
