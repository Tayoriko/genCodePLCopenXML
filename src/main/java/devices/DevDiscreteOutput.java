package devices;

import enums.FilePath;

public class DevDiscreteOutput {
    private String header = "// #";
    private int id = 0;
    private String varList = "SIG";
    private final String cmd;
    private final String cfg;
    private final String command;
    private final String result;


    // Конструктор для инициализации всех полей
    public DevDiscreteOutput(int id, String name, String devName, AddrPLC result) {
        this.header += setHeader(id, name);
        this.id = id;
        this.command = "IOL." + devName;
        this.cmd = "CVL.cmdDO[" + id + "]";;
        this.cfg = "RVL.cfgDO[" + id + "]";
        this.result = getAddr(result.getAddrCodesysDiscrete());
    }

    public DevDiscreteOutput(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        this.command = "IOL." + devOne.getDevName();
        this.cmd = "CVL.cmdDO[" + id + "]";
        this.cfg = "RVL.cfgDO[" + id + "]";
        this.result = getAddr(devOne.getSignal().getAddrCodesysDiscrete());
    }

    private String getAddr (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listDI" + addr;
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

    public String getCommand() {
        return command;
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
                        "drvDO[ID](\n" +
                        "   command     := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   result      => %s);\n",
                header, id, command, cmd, cfg, result
        );

        baseOutput += "\n";

        return baseOutput;
    }

}
