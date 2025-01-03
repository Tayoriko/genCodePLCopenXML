package dev;

import enums.eDevType;
import enums.eVarType;

public class DevFlow extends AbstractDevice{
    private String signal;
    private eVarType varType = eVarType.REAL;

    public DevFlow(Integer id, String name, String devName, String comment, String signal){
        super(id, eDevType.FLOW, name, devName, comment);
        this.signal = signal;
    }

    public DevFlow(RawDev rawDev, String signal){
        super(rawDev, eDevType.FLOW);
        this.signal = signal;
    }

    public DevFlow(RawDev rawDev){
        super(rawDev, eDevType.FLOW);
    }

    public void setSignalDiscrete() {
        this.varType = eVarType.BOOL;
    }

    public void setSignalReal() {
        this.varType = eVarType.REAL;
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

}
