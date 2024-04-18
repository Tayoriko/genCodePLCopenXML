package system;

/**
 * Special regex and methods for project
 */
public class RegexBase {

    //exit from process
    public static void exit(){
        System.exit(-1);
    }

    //regex
    public static final String regexVarName = "^[a-zA-Z_][a-zA-Z0-9_]*$";
    public static final String regexVarComment = "^(?![0-9\\s!@#$%^&*()-+=`~|\\\\/<>?,.])[а-яА-Яa-zA-Z0-9_\\s!@#$%^&*()-+=`~|\\\\/<>?.]+$";

    //special constants
    public static final String csvSplit = ",";
    public static final String empty = "empty";
    public static final String tab = "\t";
    public static final int tabVarName = 10;
    public static final int tabToComment = 5;

    //locations and smth
    public static final String filepathSource = "./src/main/resources/sources/";
    public static final String filepathTemplate = "./src/main/resources/templates/";
    public static final String fileFormatDefault = ".csv";
    public static final String fileFormatTemplate = ".vm";
}
