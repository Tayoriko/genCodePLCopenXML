package enums;

public enum eDevType {
    EMPTY       ("empty",           "empty","empty",            "empty",     "empty",       eVarType.EMPTY, "empty"),
    DI          ("Discrete Input",  "DI",   "pou10_inputDI",    "DrvDI",     "limitDI", eVarType.BOOL, "loadDI();"),
    AI          ("Analog Input",    "AI",   "pou11_inputAI",    "DrvAI",     "limitAI", eVarType.REAL, "loadAI();"),
    FLOW        ("FlowMeter",       "Flow", "pou12_flowMeter",  "DrvFlow",   "limitFlow",eVarType.REAL, "loadFlow();"),
    PID         ("PID",             "PID",  "pou75_PID",        "DrvPID",    "limitPID",eVarType.IOLA, "loadPID();"),
    DO          ("Discrete Output", "DO",   "pou80_outputDO",   "DrvDO",     "limitDO", eVarType.BOOL, "loadDO();"),
    AO          ("Analog Output",   "AO",   "pou82_outputAO",   "DrvAO",     "limitAO", eVarType.IOLA, "loadAO();"),
    MOTOR       ("Motor",           "M",    "pou85_callM",      "DrvM",      "limitM",  eVarType.IOLD, "loadM();"),
    VALVE       ("Valve",           "V",    "pou90_callV",      "DrvV",      "limitV",  eVarType.IOLD, "loadV();");


    //add name to enumerations
    private final String alloc;
    private final String name;
    private final String pou;
    private final String drv;
    private final String cnt;
    private final eVarType varType;
    private final String loader;


    //initialisation enumerations
    eDevType(String alloc, String name, String pou, String drv, String cnt, eVarType varType, String loader){
        this.alloc = alloc;
        this.name = name;
        this.pou = pou;
        this.drv = drv;
        this.cnt = cnt;
        this.varType = varType;
        this.loader = loader;
    }

    //get string description on vars
    public String getValue() {
        return this.alloc;
    }

    //get string description on vars
    public String getName() {
        return this.name;
    }

    public String getPou() {
        return pou;
    }

    public String getDrv() {
        return drv;
    }

    public String getCnt() {
        return cnt;
    }

    public eVarType getVarType() {
        return varType;
    }

    public String getLoader() {
        return loader;
    }

    //find type by string value
    public static eDevType findByValue (String value) {
        eDevType type = eDevType.EMPTY;
        for (eDevType item : eDevType.values())
        {
            if (item.getValue().equalsIgnoreCase(value)) {
                type = item;
                break;
            }
        }
        return type;
    }

    //find type by string name
    public static eDevType findByName (String value) {
        eDevType type = eDevType.EMPTY;
        for (eDevType item : eDevType.values())
        {
            if (item.getName().equalsIgnoreCase(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
