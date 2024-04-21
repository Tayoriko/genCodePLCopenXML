package enumerations;

/**
 * This enumeration represents the variables types for PLC
 * Each constant represents a variant:
 * standart:    BOOL, BYTE, SINT, USINT, UINT, INT, WORD, REAL, DINT
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum eVarType {
    EMPTY   ("EMPTY",   eVarSize.EMPTY),
    BOOL    ("BOOL",    eVarSize.BOOL),
    BYTE    ("BYTE",    eVarSize.BYTE),
    SINT    ("SINT",    eVarSize.BYTE),
    USINT   ("USINT",   eVarSize.BYTE),
    UINT    ("UINT",    eVarSize.WORD),
    INT     ("INT",     eVarSize.WORD),
    WORD    ("WORD",    eVarSize.WORD),
    REAL    ("REAL",    eVarSize.DOUBLE),
    LREAL   ("LREAL",   eVarSize.LONG),
    DINT    ("DINT",    eVarSize.DOUBLE),
    UDINT   ("UDINT",   eVarSize.DOUBLE),
    TIME    ("TIME",    eVarSize.DOUBLE),
    CUSTOM  ("CUSTOM",  eVarSize.LONG);

    //add name to enumerations
    private final String type;
    private final String addr;

    //initialisation enumerations
    eVarType(String type, eVarSize addr){
        this.type = type;
        this.addr = addr.getValue();
    }

    //get string description on vars
    public String getValue() {
        return this.type;
    }

    //get string addr prefix
    public String getAddr ()    {
        return this.addr;
    }

    //find type by string value
    public static eVarType findByValue (String value) {
        eVarType type = eVarType.CUSTOM;
        for (eVarType item : eVarType.values())
        {
            if (item.getValue().equals(value.toUpperCase())) {
                type = item;
                break;
            }
        }
        return type;
    }
}
