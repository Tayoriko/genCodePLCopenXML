package devPLC;

public enum eCtags {
    EMPTY           ("empty"),
    type            ("type"),
    globalVars      ("globalVars"),
    baseType        ("baseType"),
    derived         ("derived"),
    doc             ("documentation"),
    xhtml           ("xhtml"),
    addData         ("addData"),
    attr            ("attribute"),
    data            ("data"),
    attrs           ("attributes"),
    objectId        ("objectId"),
    xmlns           ("xmlns"),
    handleUnknown   ("handleUnknown"),
    variable        ("variable"),
    name            ("name"),
    dimension       ("dimension"),
    lower           ("lower"),
    upper           ("upper"),
    array           ("array"),
    value           ("value"),
    simpleValue     ("simpleValue"),
    initialValue    ("initialValue"),
    ProjectStructure("ProjectStructure"),
    Folder          ("Folder"),
    Object          ("Object")
    ;


    //add name to enumerations
    private final String tag;

    //initialisation enumerations
    eCtags(String alloc){
        this.tag = alloc;
    }

    //get string description on vars
    public String getTag() {
        return this.tag;
    }

    //find type by string value
    public static eCtags findByValue (String value) {
        eCtags type = eCtags.EMPTY;
        for (eCtags item : eCtags.values())
        {
            if (item.getTag().equals(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
