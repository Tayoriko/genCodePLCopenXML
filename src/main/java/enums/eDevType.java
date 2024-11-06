package enums;

public enum eDevType {
    EMPTY       ("empty", "empty"),
    MOTOR       ("Motor", "M"),
    VALVE       ("Valve", "V"),
    PID         ("PID", "PID"),
    AI          ("Analog Input", "AI"),
    AO          ("Analog Output", "AO"),
    DI          ("Discrete Input", "DI"),
    DO          ("Discrete Output", "DO");


    //add name to enumerations
    private final String alloc;
    private final String name;

    //initialisation enumerations
    eDevType(String alloc, String name){
        this.alloc = alloc;
        this.name = name;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //get string description on vars
    public String getName() {
        return this.name;
    }

    //find type by string value
    public static eVarLists findByValue (String value) {
        eVarLists type = eVarLists.EMPTY;
        for (eVarLists item : eVarLists.values())
        {
            if (item.getValue().equals(value.toUpperCase())) {
                type = item;
                break;
            }
        }
        return type;
    }
}
