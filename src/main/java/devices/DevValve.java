package devices;

import enums.FilePath;

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

    public DevValve(int id, DevOne devOne){
        this.header += setHeader(id, devOne.getName()) + " - " + devOne.getComment();
        this.id = id;
        this.cmd = "CVL.cmdV[" + id + "]";;
        this.cfg = "RVL.cfgV[" + id + "]";
        this.state = "SVL.stateV[" + id + "]";
        this.devState = "IOL." + devOne.getDevName();
        this.fbQF = getAddrDI(devOne.getQf().getAddrCodesysDiscrete());
        this.fbOpen = getAddrDI(devOne.getFbOpen().getAddrCodesysDiscrete());
        this.fbClose = getAddrDI(devOne.getFbOpen().getAddrCodesysDiscrete());
        this.cmdOpen = getAddrDO(devOne.getCmdOpen().getAddrCodesysDiscrete());
        this.cmdClose = getAddrDO(devOne.getCmdClose().getAddrCodesysDiscrete());
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
                        "drvV[%d](\n" +
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
