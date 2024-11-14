package devices;

public class DevValve {
    private String header = "// #";
    private int id = 0;
    private final String varList = "SIG";
    private final String fbQF;
    private final String fbOpen;
    private final String fbClose;
    private final String cmd;
    private final String cfg;
    private final String state;
    private final String devState;
    private final String cmdOpen;
    private final String cmdClose;
    private StringBuilder options;
    private boolean useOptions = false;

    // Конструктор для инициализации всех полей
    public DevValve(int id, String name, String devName, AddrPLC qf, AddrPLC fbOpen, AddrPLC fbClose, AddrPLC cmdOpen,AddrPLC cmdClose) {
        this.header += setHeader(id, name);
        this.id = id;
        this.cmd = "CVL.cmdV[" + id + "]";;
        this.cfg = "RVL.cfgV[" + id + "]";
        this.state = "SVL.stateV[" + id + "]";
        this.devState = "IOL." + devName;
        this.fbQF = varList + ".listDI" + qf.getAddrCodesysDiscrete();
        this.fbOpen = varList + ".listDI" + fbOpen.getAddrCodesysDiscrete();
        this.fbClose = varList + ".listDI" + fbClose.getAddrCodesysDiscrete();
        this.cmdOpen = varList + ".listDO" + cmdOpen.getAddrCodesysDiscrete();
        this.cmdClose = varList + ".listDO" + cmdClose.getAddrCodesysDiscrete();
        this.options = setOptions(qf, fbOpen, fbClose);
    }

    public DevValve(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName());
        this.id = id;
        this.cmd = "CVL.cmdV[" + id + "]";;
        this.cfg = "RVL.cfgV[" + id + "]";
        this.state = "SVL.stateV[" + id + "]";
        this.devState = "IOL." + devOne.getDevName();
        this.fbQF = varList + ".listDI" + devOne.getQf().getAddrCodesysDiscrete();
        this.fbOpen = varList + ".listDI" + devOne.getFbOpen().getAddrCodesysDiscrete();
        this.fbClose = varList + ".listDI" + devOne.getFbOpen().getAddrCodesysDiscrete();
        this.cmdOpen = varList + ".listDO" + devOne.getCmdOpen().getAddrCodesysDiscrete();
        this.cmdClose = varList + ".listDO" + devOne.getCmdClose().getAddrCodesysDiscrete();
        this.options = setOptions(devOne.getQf(), devOne.getFbOpen(), devOne.getFbClose());
    }

    private StringBuilder setOptions (AddrPLC qf, AddrPLC fbOpen, AddrPLC fbClose) {
        StringBuilder options = new StringBuilder();
        options.append(useIt(qf, "cfg.useQF"));
        options.append(useIt(fbOpen, "cfg.usefbOpen"));
        options.append(useIt(fbClose, "cfg.usefbClose"));
        return options;
    }

    private StringBuilder useIt (AddrPLC addrPLC, String cfgText) {
        StringBuilder option = new StringBuilder();
        if (!addrPLC.isUse()){
            option.append(cfg + "." + cfgText + " = FALSE;\n");
            useOptions = true;
        }
        return option;
    }

    // Геттеры для каждого поля
    public int getId() {
        return id;
    }

    public String getFbQF() {
        return fbQF;
    }

    public String getFbOpen() {
        return fbOpen;
    }

    public String getFbClose() {
        return fbClose;
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

    public String getCmdOpen() {
        return cmdOpen;
    }

    public String getCmdClose() {
        return cmdClose;
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
                        "drvV[ID](\n" +
                        "   devState    := %s,\n" +
                        "   CMD         := %s,\n" +
                        "   cfg         := %s,\n" +
                        "   state       := %s,\n" +
                        "   fbQF        := %s,\n" +
                        "   fbOpen      := %s,\n" +
                        "   fbClose     := %s,\n" +
                        "   cmdOpen     => %s,\n" +
                        "   cmdClose    => %s);\n",
                header, id, devState, cmd, cfg, state, fbQF, fbOpen, fbClose, cmdOpen, cmdClose
        );

        // Добавление данных из options, если useOptions равно true
        if (useOptions) {
            baseOutput += options.toString();
        }
        baseOutput += "\n";

        return baseOutput;
    }
}
