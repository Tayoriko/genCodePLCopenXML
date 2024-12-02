package dev;

import enums.eDevType;

public class DevDO extends AbstractDevice{
    private final AddrIO result;

    public DevDO(Integer id, String name, String devName, String comment, String result){
        super(id, eDevType.DO, name, devName, comment);
        this.result = new AddrIO(result);
    }

    public DevDO(RawDev rawDev, String result){
        super(rawDev, eDevType.DO);
        this.result = new AddrIO(result);
    }

    public AddrIO getResult() {
        return result;
    }
}
