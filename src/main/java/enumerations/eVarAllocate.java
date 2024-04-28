package enumerations;

/**
 * This enumeration represents the variables allocation in PLC memory
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum eVarAllocate {
    EMPTY       ("empty"),
    IN          ("I"),
    OUT         ("Q"),
    LOCAL       ("M");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eVarAllocate(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eVarAllocate findByValue (String value) {
        eVarAllocate type = eVarAllocate.EMPTY;
        for (eVarAllocate item : eVarAllocate.values())
        {
            if (item.getValue().equals(value.toUpperCase())) {
                type = item;
                break;
            }
        }
        return type;
    }
}
