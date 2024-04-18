package enumerations;

/**
 * This enumeration represents the variables parts for PLC
 * Each constant represents a variant:
 * FB/FC section:   VAR_INPUT, VAR_OUTPUT, VAR_IN_OUT
 * Variables List:  VAR_GLOBAL, VAR_GLOBAL RETAIN, VAR_GLOBAL PERSISTENT RETAIN
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum listVars {

    IN          ("VAR_INPUT"),
    OUT         ("VAR_OUTPUT"),
    IN_OUT      ("VAR_IN_OUT"),
    GLOBAL      ("VAR_GLOBAL"),
    RETAIN      ("VAR_GLOBAL RETAIN"),
    PERSISTENT  ("VAR_GLOBAL PERSISTENT RETAIN"),
    END         ("END_VARS");

    //add name to enumerations
    private final String vars;

    //initialisation enumerations
    listVars(String vars){
        this.vars = vars;
    }

    //get string description on vars
    public String getVars() {
        return this.vars;
    }
}
