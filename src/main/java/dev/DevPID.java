package dev;

import enums.eDevType;
import enums.eVarType;

public class DevPID extends AbstractDevice{
    private String signal;
    private String result;
    private eVarType varType = eVarType.REAL;

    public DevPID(Integer id, String name, String devName, String comment, String signal, String result){
        super(id, eDevType.PID, name, devName, comment);
        this.signal = signal;
        this.result = result;
    }

    public DevPID(RawDev rawDev, String signal, String result){
        super(rawDev, eDevType.PID);
        this.signal = signal;
        this.result = result;
    }

    public DevPID(RawDev rawDev){
        super(rawDev, eDevType.PID);
    }

    public String getSignal() {
        return signal;
    }

    public eVarType getVarType() {
        return varType;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
