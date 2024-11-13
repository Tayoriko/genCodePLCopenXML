package devices;

public class DevMotor {
    private String header = "// #";
    private int id = 0;
    private String varList = "SIG";
    private String fbQF = varList + ".listDI";
    private String fbKM = varList + ".listDI";
    private final String cmd;
    private final String cfg;
    private final String state;
    private final String devState;
    private final String cmdFW;

    // Конструктор для инициализации всех полей
    public DevMotor(int id, String name, String devName, AddrPLC qf, AddrPLC km, AddrPLC cmdFw) {
        this.header += setHeader(id, name);
        this.id = id;
        this.cmd = "CVL.cmdM[" + id + "]";;
        this.cfg = "RVL.cfgM[" + id + "]";
        this.state = "SVL.stateM[" + id + "]";
        this.devState = "IOL." + devName;
        this.fbQF = varList + ".listDI" + qf.getAddrCodesys();
        this.fbKM = varList + ".listDI" + km.getAddrCodesys();
        this.cmdFW = varList + ".listDO" + cmdFw.getAddrCodesys();
    }

    public DevMotor (int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName());
        this.id = id;
        this.cmd = "CVL.cmdM[" + id + "]";;
        this.cfg = "RVL.cfgM[" + id + "]";
        this.state = "SVL.stateM[" + id + "]";
        this.devState = "IOL." + devOne.getDevName();
        this.fbQF = varList + ".listDI" + devOne.getQf().getAddrCodesys();
        this.fbKM = varList + ".listDI" + devOne.getKm().getAddrCodesys();
        this.cmdFW = varList + ".listDO" + devOne.getCmdFw().getAddrCodesys();
    }

    // Геттеры для каждого поля
    public int getId() {
        return id;
    }

    public String getFbQF() {
        return fbQF;
    }

    public String getFbKM() {
        return fbKM;
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

    public String getCmdFW() {
        return cmdFW;
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
        return String.format(
                "%s\n" +
                "ID := %d;\n" +
                        "drvM[ID](\n" +
                        "   devState    := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        "   fbQF        := %s,\n" +
                        "   fbKM        := %s,\n" +
                        "   cmdFW       => %s);\n\n",
                header, id, devState, cmd, cfg, state, fbQF, fbKM, cmdFW
        );
    }
}
