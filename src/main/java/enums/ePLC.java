package enums;

public enum ePLC {
    EMPTY       ("empty"),
    CODESYS     ("CoDeSyS"),
    OPC         ("OPC"),
    OMRON       ("Omron"),
    MODBUS      ("Modbus"),
    TIA_PORTAL  ("TIA Portal");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    ePLC(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static ePLC findByValue (String value) {
        ePLC type = ePLC.EMPTY;
        for (ePLC item : ePLC.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
