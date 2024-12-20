package enums;

public enum eRegex {
    EMPTY               ("empty"),
    deviceName          ("^[A-ZА-Я][a-zA-Zа-яА-Я0-9]{0,9}$"),
    timeStampCodesys    ("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eRegex(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }
}
