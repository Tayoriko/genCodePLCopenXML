package enumerations;

/**
 * This enumeration represents the library for source
 * Each constant represents a variant:
 * system libraries:
 * custom libraries: mt
 * Use this enumeration to work with CoDeSyS or PLCopenXML
 */

public enum listLib {

    mt         ("mt"),         //mechatronics default library
    mtMotion   ("mt_motion");   //mechatronics default library with axis

    //add name to enumerations
    private final String lib;

    //initialisation enumerations
    listLib(String lib){
        this.lib = lib;
    }

    //get string description on vars
    public String getLib() {
        return this.lib;
    }
}
