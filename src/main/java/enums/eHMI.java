package enums;

public enum eHMI {
    EMPTY       ("empty"),
    WEINTEK     ("Weintek"),
    ONI         ("Oni"),
    OMRON_NB    ("Omron NB"),
    WINCC       ("WinCC");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eHMI(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eHMI findByValue (String value) {
        eHMI type = eHMI.EMPTY;
        for (eHMI item : eHMI.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
