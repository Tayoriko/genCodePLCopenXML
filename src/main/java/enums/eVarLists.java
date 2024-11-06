package enums;

public enum eVarLists {
    EMPTY       ("empty", "empty"),
    constant    ("IDL", "limit"),
    retain      ("RVL", "cfg"),
    setpoint    ("SPL", ""),
    status      ("SVL", "stat"),
    command     ("CVL", "cmd");


    //add name to enumerations
    private final String alloc;
    private final String name;

    //initialisation enumerations
    eVarLists(String alloc, String name){
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
