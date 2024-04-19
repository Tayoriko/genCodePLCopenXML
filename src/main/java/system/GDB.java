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

    //locations and smth
    public static final String filepathSource = "./src/main/resources/sources/";
    public static final String filepathTemplate = "./src/main/resources/templates/";
    public static final String fileFormatDefault = ".csv";
    public static final String fileFormatTemplate = ".vm";

    //global methods

    //default filenames for templates
    public static final String templateOneVar = "OneVarAddr";
    public static final String templateOneVarNoAddr = "OneVarNoAddr";
}

