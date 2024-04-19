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
}
