package enums;

public enum eProtocol {
    EMPTY       ("empty"),
    CODESYS     ("CoDeSyS"),
    OPC         ("OPC"),
    OMRON       ("Omron"),
    MODBUS      ("Modbus"),
    STEP7       ("Step7");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eProtocol(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eProtocol findByValue (String value) {
        eProtocol type = eProtocol.EMPTY;
        for (eProtocol item : eProtocol.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
