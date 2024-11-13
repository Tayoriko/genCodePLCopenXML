package enums;

public enum eVarType {
    EMPTY   ("EMPTY"),
    BIT     ("BIT"),
    BOOL    ("BOOL"),
    REAL    ("REAL"),
    INT     ("INT"),
    DINT    ("DINT"),
    STRING  ("STRING"),
    FLOAT   ("FLOAT"),
    DOUBLE  ("DOUBLE"),
    CHAR    ("CHAR"),
    BYTE    ("BYTE"),
    WORD    ("WORD"),
    DWORD   ("DWORD"),
    LWORD   ("LWORD"),
    IOLD    ("mt.udtDevDiscrete"),
    IOLA    ("mt.udtDevAnalog");

    private final String typeName;

    eVarType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}