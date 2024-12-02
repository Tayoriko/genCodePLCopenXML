package dev;

import enums.eDevType;
import enums.eVarType;

public class DevAI extends AbstractDevice{
    private AddrIO signal;
    private eVarType varType = eVarType.REAL;

    public DevAI(Integer id, String name, String devName, String comment, String signal){
        super(id, eDevType.AI, name, devName, comment);
        this.signal = new AddrIO(signal);
    }

    public DevAI(RawDev rawDev, String signal){
        super(rawDev, eDevType.AI);
        this.signal = new AddrIO(signal);
    }

    public DevAI(RawDev rawDev){
        super(rawDev, eDevType.AI);
    }

    public void setSignalInt() {
        this.varType = eVarType.INT;
    }

    public void setSignalReal() {
        this.varType = eVarType.REAL;
    }

    public AddrIO getSignal() {
        return signal;
    }

    public eVarType getVarType() {
        return varType;
    }

    public void setSignal(String signal) {
        this.signal = new AddrIO(signal);
    }
}
