package databases;

import enums.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

public class GData {
    public static final String DEFAULT_SOURCE           = "./src/main/resources/sources/devices.xlsx";
    public static final String RESOURCES                = "./src/main/resources/";

    public static final int MAX_VAR_LENGHT              = 10;
    public static final String tab = "  ";

    public static final String codesysLibName = "mt.";
    public static final String codesysXmlns = "http://www.w3.org/1999/xhtml";
    public static final String codesysPlcOpenLink = "http://www.3s-software.com/plcopenxml/";

    private static final ThreadLocal<GData> instance = ThreadLocal.withInitial(GData::new);

    private static eHMI hmi;
    private static eLang lang = eLang.RU;
    private static eTemplate template;
    private static ePLC plc;
    private static String version = "";
    private static Set<eDevType> devices = new LinkedHashSet<>();
    private static Set<eActions> actions = new LinkedHashSet<>();
    private static Set<eOptions> options = new LinkedHashSet<>();
    private static String projectName = "default";
    private static String targetFolder;

    private GData() {}

    public static GData getInstance() {
        return instance.get();
    }

    public void clear() {
        instance.remove();
    }

    static public eHMI getHmi() {
        return hmi;
    }

    public void setHmi(eHMI hmi) {
        this.hmi = hmi;
    }

    static public eTemplate getTemplate() {
        return template;
    }

    public void setTemplate(eTemplate template) {
        this.template = template;
    }

    static public ePLC getPlc() {
        return plc;
    }

    public void setPlc(ePLC plc) {
        this.plc = plc;
    }

    static public Set<eDevType> getDevices() {
        return devices;
    }

    public void setDevices(Set<eDevType> devices) {
        this.devices = devices;
    }

    static public Set<eActions> getActions() {
        return actions;
    }

    public void setActions(Set<eActions> actions) {
        this.actions = actions;
    }

    static public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    static public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    static public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    static public Set<eOptions> getOptions() {
        return options;
    }

    static public eLang getLang() {
        return lang;
    }

    public void setLang(eLang lang) {
        this.lang = lang;
    }

    public void setOptions(Set<eOptions> options) {
        this.options = options;
    }

    static public String getTimeStamp () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(eRegex.timeStampCodesys.getValue());
        return now.format(formatter);
    }
}
