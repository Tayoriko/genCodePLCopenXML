package enums;

public enum eLang {
    EMPTY       ("empty"),
    RU          ("Русский"),
    EN          ("English");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eLang(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eLang findByValue (String value) {
        eLang type = eLang.EMPTY;
        for (eLang item : eLang.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
