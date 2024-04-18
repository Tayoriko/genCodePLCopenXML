package enumerations;

/**
 * This enumeration represents the variables types for PLC
 * Each constant represents a variant:
 * standart:    BOOL, BYTE, SINT, USINT, UINT, INT, WORD, REAL, DINT
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum listBaseTypes {
    empty   ("empty"),
    BOOL    ("bool"),
    BYTE    ("byte"),
    SINT    ("sint"),
    USINT   ("usint"),
    UINT    ("uint"),
    INT     ("int"),
    WORD    ("word"),
    REAL    ("real"),
    LREAL   ("lreal"),
    DINT    ("dint"),
    UDINT   ("udint"),
    TIME    ("time");

    //add name to enumerations
    private final String type;

    //initialisation enumerations
    listBaseTypes(String type){
        this.type = type;
    }

    //get string description on vars
    public String getValue() {
        return this.type;
    }
}
