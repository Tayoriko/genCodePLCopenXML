package dev;

import enums.eDevType;

public class DevDI extends AbstractDevice{
    private final AddrIO signal;
    private boolean alarm = false;

    public DevDI(Integer id, String name, String devName, String comment, String signal){
        super(id, eDevType.DI, name, devName, comment);
        this.signal = new AddrIO(signal);
    }

    public DevDI(RawDev rawDev, String signal){
        super(rawDev, eDevType.DI);
        this.signal = new AddrIO(signal);
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public AddrIO getSignal() {
        return signal;
    }
}
