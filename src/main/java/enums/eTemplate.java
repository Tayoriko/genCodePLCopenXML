package enums;

public enum eTemplate {
    EMPTY       ("empty"),
    BASIC       ("Basic"),
    EXT         ("Extended"),
    CUSTOM      ("Custom");


    //add name to enumerations
    private final String alloc;

    //initialisation enumerations
    eTemplate(String alloc){
        this.alloc = alloc;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //find type by string value
    public static eTemplate findByValue (String value) {
        eTemplate type = eTemplate.EMPTY;
        for (eTemplate item : eTemplate.values())
        {
            if (item.getValue().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
