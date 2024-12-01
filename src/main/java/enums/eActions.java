package enums;

public enum eActions {

    EMPTY       ("no actions"),
    ALM         ("Alarms"),
    PUO         ("Program"),
    IOL         ("IO List"),
    MBS         ("ModBus");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eActions(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eActions findByValue (String value) {
        eActions type = eActions.EMPTY;
        for (eActions item : eActions.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }

}
