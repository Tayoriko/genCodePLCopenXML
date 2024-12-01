package enums;

public enum eDevices {
    EMPTY       ("empty",           "empty","empty",            "empty",     "empty",       eVarType.EMPTY.getTypeName(), "empty"),
    DI          ("Discrete Input",  "DI",   "pou10_inputDI",    "DrvDI",     "IDL.limitDI", eVarType.BOOL.getTypeName(), "loadDI();"),
    AI          ("Analog Input",    "AI",   "pou11_inputAI",    "DrvAI",     "IDL.limitAI", eVarType.REAL.getTypeName(), "loadAI();"),
    FLOW        ("FlowMeter",       "Flow", "pou12_flowMeter",  "DrvFlow",   "IDL.limitFlow",eVarType.REAL.getTypeName(), "loadFlow();"),
    PID         ("PID",             "PID",  "pou75_PID",        "DrvPID",    "IDL.limitPID",eVarType.IOLA.getTypeName(), "loadPID();"),
    DO          ("Discrete Output", "DO",   "pou80_outputDO",   "DrvDO",     "IDL.limitDO", eVarType.BOOL.getTypeName(), "loadDO();"),
    AO          ("Analog Output",   "AO",   "pou82_outputAO",   "DrvAO",     "IDL.limitAO", eVarType.IOLA.getTypeName(), "loadAO();"),
    MOTOR       ("Motor",           "M",    "pou85_callM",      "DrvM",      "IDL.limitM",  eVarType.IOLD.getTypeName(), "loadM();"),
    VALVE       ("Valve",           "V",    "pou90_callV",      "DrvV",      "IDL.limitV",  eVarType.IOLD.getTypeName(), "loadV();");


    //add name to enumerations
    private final String alloc;
    private final String name;
    private final String pou;
    private final String drv;
    private final String cnt;
    private final String typeName;
    private final String loader;


    //initialisation enumerations
    eDevices(String alloc, String name, String pou, String drv, String cnt, String typeName, String loader){
        this.alloc = alloc;
        this.name = name;
        this.pou = pou;
        this.drv = drv;
        this.cnt = cnt;
        this.typeName = typeName;
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

    public String getTypeName() {
        return typeName;
    }

    public String getLoader() {
        return loader;
    }

    //find type by string value
    public static eDevices findByValue (String value) {
        eDevices type = eDevices.EMPTY;
        for (eDevices item : eDevices.values())
        {
            if (item.getValue().equalsIgnoreCase(value)) {
                type = item;
                break;
            }
        }
        return type;
    }
}
