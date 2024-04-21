package enumerations;

/**
 * This enumeration represents the variables address format for PLC
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum eVarSize {
    EMPTY   ("empty"),
    BOOL    ("X"),
    BYTE    ("B"),
    WORD    ("W"),
    DOUBLE  ("D"),
    LONG    ("L");


    //add name to enumerations
    private final String addr;

    //initialisation enumerations
    eVarSize(String addr){
        this.addr = addr;
    }

    //get string description on vars
    public String getValue() {
        return this.addr;
    }
}
