package databases;

import enums.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

public class GData {
    public static final String DEFAULT_SOURCE           = "./src/main/resources/sources/devices.xlsx";
    public static final String DEFAULT_TARGET_FOLDER    = "./src/main/resources/target";
    public static final String RESOURCES                = "./src/main/resources/";
    public static final String CUSTOM_MV210             = "./src/main/resources/custom_MV210/";
    public static final String FILEPATH_XML_CLOSE       = "./src/main/resources/xml/XMLclose.vm";
    public static final String FILEPATH_XML_OPEN        = "./src/main/resources/xml/XMLopen.vm";
    public static final String FILEPATH_XML_DATA        = "./src/main/resources/xml/XMLdata.vm";
    public static final String FILEPATH_XML_POU         = "./src/main/resources/xml/XMLpou.vm";
    public static final String FILEPATH_XML_VAR         = "./src/main/resources/xml/XMLvar.vm";
    public static final String FILEPATH_XML_VARS        = "./src/main/resources/xml/XMLvars.vm";
    public static final int MAX_VAR_LENGHT              = 10;
    public static final String tab = "  ";

    public static final String codesysLibName = "mt.";
    public static final String codesysXmlns = "http://www.w3.org/1999/xhtml";
    public static final String codesysPlcOpenLink = "http://www.3s-software.com/plcopenxml/";

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
    private static Boolean bitCorrection = false;

    public static eHMI getHmi() {
        return hmi;
    }

    public static void setHmi(eHMI hmi) {
        GData.hmi = hmi;
    }

    public static eTemplate getTemplate() {
        return template;
    }

    public static void setTemplate(eTemplate template) {
        GData.template = template;
    }

    public static ePLC getPlc() {
        return plc;
    }

    public static void setPlc(ePLC plc) {
        GData.plc = plc;
    }

    public static Set<eDevType> getDevices() {
        return devices;
    }

    public static void setDevices(Set<eDevType> devices) {
        GData.devices = devices;
    }

    public static Set<eActions> getActions() {
        return actions;
    }

    public static void setActions(Set<eActions> actions) {
        GData.actions = actions;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static void setProjectName(String projectName) {
        GData.projectName = projectName;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        GData.version = version;
    }

    public static String getTargetFolder() {
        return targetFolder;
    }

    public static void setTargetFolder(String targetFolder) {
        GData.targetFolder = targetFolder;
    }

    public static Set<eOptions> getOptions() {
        return options;
    }

    public static eLang getLang() {
        return lang;
    }

    public static void setLang(eLang lang) {
        GData.lang = lang;
    }

    public static void setOptions(Set<eOptions> options) {
        GData.options = options;
    }

    public static Boolean getBitCorrection() {
        return bitCorrection;
    }

    public static void setBitCorrection(Boolean bitCorrection) {
        GData.bitCorrection = bitCorrection;
    }

    public static String getTimeStamp () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(eRegex.timeStampCodesys.getValue());
        return now.format(formatter);
    }
}
