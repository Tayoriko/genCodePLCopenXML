package system;

/**
 * Special regex and methods for project
 */
public class GDB {

    //exit from process
    public static void exit(){
        System.exit(-1);
    }

    //regex
    public static final String regexVarName = "^[a-zA-Z_][a-zA-Z0-9_]*$";
    public static final String regexVarComment = "^(?![0-9\\s!@#$%^&*()-+=`~|\\\\/<>?,.])[а-яА-Яa-zA-Z0-9_\\s!@#$%^&*()-+=`~|\\\\/<>?.]+$";
    public static final String regexVarAddrBool = "^(999|([0-9]\\d{0,2}|0))\\.((0\\d|1[0-5])|1[0-5])$";
    public static final String regexVarAddr = "^([1-9]\\d{0,3}|[1-3]\\d{3}|409[0-6])$"; //limit 4096

    //special constants
    public static final String splitCsv = ",";
    public static final String splitVarAddress = "\\.";
    public static final String empty = "empty";
    public static final String tab = "\t";
    public static final int tabVarName = 10;
    public static final int tabToComment = 5;
    public static final int instances = 1;

    //locations and smth
    public static final String filepathTemplate = "./src/main/resources/templates/";
    public static final String filepathSource = "./src/main/resources/sources/";
    public static final String filepathDataBase = "./src/main/resources/db/";
    public static final String fileFormatDefault = ".csv";
    public static final String fileFormatTemplate = ".vm";
    public static final String xmlVersion = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";


    //global namespace
    public static final String companyName = "sintozone's studio";
    public static final String productName = "CODESYS";
    public static final String productVersion = "CODESYS V3.5 SP20";
    public static final String projectName = "genXMLtest";
    public static final String dataBase = "PLCopen.db";

    //PLCopenXML specific attributes
    public static final String attributeXmlns = "http://www.w3.org/1999/xhtml";

    //global methods

    //default filenames for templates
    public static final String templateFileHeader = "genFileHeader";
    public static final String templateContentHeader = "genContentHeader";

}

