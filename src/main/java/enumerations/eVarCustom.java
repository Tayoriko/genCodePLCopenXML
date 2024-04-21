package enumerations;

/**
 * This enumeration represents the variables types for PLC
 * Each constant represents a variant:
 * custom:
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum eVarCustom {
    EMPTY       ("empty"),
    DEVLIST     ("devList");

    //add name to enumerations
    private final String type;

    //initialisation enumerations
    eVarCustom(String type){
        this.type = type;
    }

    //get string description on vars
    public String getValue() {
        return this.type;
    }
}
