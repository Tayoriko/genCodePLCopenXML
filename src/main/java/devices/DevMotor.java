package devices;

import enums.FilePath;

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
    private StringBuilder options;
    private boolean useOptions = false;

    // Конструктор для инициализации всех полей
    public DevMotor(int id, String name, String devName, AddrPLC qf, AddrPLC km, AddrPLC cmdFw) {
        this.header += setHeader(id, name);
        this.id = id;
        this.cmd = "CVL.cmdM[" + id + "]";;
        this.cfg = "RVL.cfgM[" + id + "]";
        this.state = "SVL.stateM[" + id + "]";
        this.devState = "IOL." + devName;
        this.fbQF = getAddrDI(qf.getAddrCodesysDiscrete());
        this.fbKM = getAddrDI(km.getAddrCodesysDiscrete());
        this.cmdFW = getAddrDO(cmdFw.getAddrCodesysDiscrete());
        this.options = setOptions(qf, km);
    }

    public DevMotor (int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        this.cmd = "CVL.cmdM[" + id + "]";
        this.cfg = "RVL.cfgM[" + id + "]";
        this.state = "SVL.stateM[" + id + "]";
        this.devState = "IOL." + devOne.getDevName();
        this.fbQF = getAddrDI(devOne.getQf().getAddrCodesysDiscrete());
        this.fbKM = getAddrDI(devOne.getKm().getAddrCodesysDiscrete());
        this.cmdFW = getAddrDO(devOne.getCmdFw().getAddrCodesysDiscrete());
        this.options = setOptions(devOne.getQf(), devOne.getKm());
    }

    private StringBuilder setOptions(AddrPLC qf, AddrPLC km) {
        StringBuilder options = new StringBuilder();
        options.append(useIt(qf, "cfg.useQF"));
        options.append(useIt(km, "cfg.useKM"));
        return options;
    }

    private StringBuilder useIt (AddrPLC addrPLC, String cfgText) {
        StringBuilder option = new StringBuilder("");
        if (!addrPLC.isUse()){
            option.append(cfg + "." + cfgText + " := FALSE;\n");
            useOptions = true;
        }
        return option;
    }

    private String getAddrDI (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listDI" + addr;
    }

    private String getAddrDO (String addr) {
        if (addr.length() > FilePath.MAX_VAR_LENGHT) {
            return addr;
        } else return varList + ".listDO" + addr;
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
        String baseOutput = String.format(
                "%s\n" +
                        "ID := %d;\n" +
                        "drvM[ID](\n" +
                        "   devState    := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        "   fbQF        := %s,\n" +
                        "   fbKM        := %s,\n" +
                        "   cmdFW       => %s);\n",
                header, id, devState, cmd, cfg, state, fbQF, fbKM, cmdFW
        );

        // Проверка переменной useOptions и добавление данных из options, если useOptions == true
        if (useOptions) {
            baseOutput += options.toString();
        }
        baseOutput += "\n";

        return baseOutput;
    }

    public StringBuilder setOptions() {
        return options;
    }

    public boolean isUseOptions() {
        return useOptions;
    }
}
