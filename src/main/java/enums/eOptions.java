package enums;

public enum eOptions {
    EMPTY       ("no actions"),
    BIT         ("bit addr -1");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eOptions(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eOptions findByValue (String value) {
        eOptions type = eOptions.EMPTY;
        for (eOptions item : eOptions.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
