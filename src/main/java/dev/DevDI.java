package dev;

import enums.eDevType;

public class DevDI extends AbstractDevice{
    private final AddrIO signal;

    public DevDI(Integer id, String name, String devName, String comment, String signal){
        super(id, eDevType.DI, name, devName, comment);
        this.signal = new AddrIO(signal);
    }

    public DevDI(RawDev rawDev, String signal){
        super(rawDev, eDevType.DI);
        this.signal = new AddrIO(signal);
    }

    public AddrIO getSignal() {
        return signal;
    }
}
