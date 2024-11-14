package devices;

public class DevDiscreteInput {
    private String header = "// #";
    private int id = 0;
    private String varList = "SIG";
    private String signal = varList + ".listDI";
    private final String cmd;
    private final String cfg;
    private final String result;

    // Конструктор для инициализации всех полей
    public DevDiscreteInput(int id, String name, String devName, AddrPLC signal) {
        this.header += setHeader(id, name);
        this.id = id;
        this.signal = varList + ".listDI" + signal.getAddrCodesysDiscrete();
        this.cmd = "CVL.cmdDI[" + id + "]";;
        this.cfg = "RVL.cfgDI[" + id + "]";
        this.result = "IOL." + devName;
    }

    public DevDiscreteInput(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName());
        this.id = id;
        this.signal = varList + ".listDI" + devOne.getSignal().getAddrCodesysDiscrete();
        this.cmd = "CVL.cmdDI[" + id + "]";
        this.cfg = "RVL.cfgDI[" + id + "]";
        this.result = "IOL." + devOne.getDevName();
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
                        "drvDI[ID](\n" +
                        "   signal      := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   result      => %s);\n",
                header, id, signal, cmd, cfg, result
        );

        baseOutput += "\n";

        return baseOutput;
    }
}
