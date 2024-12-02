package dev;

import enums.eDevType;
import enums.eVarType;

public class DevAO extends AbstractDevice{
    private AddrIO result;
    private eVarType varType = eVarType.REAL;

    public DevAO(Integer id, String name, String devName, String comment, String result){
        super(id, eDevType.AO, name, devName, comment);
        this.result = new AddrIO(result);
    }

    public DevAO(RawDev rawDev, String result){
        super(rawDev, eDevType.AO);
        this.result = new AddrIO(result);
    }

    public DevAO(RawDev rawDev){
        super(rawDev, eDevType.AO);
    }

    public AddrIO getResult() {
        return result;
    }

    public void setResultInt() {
        this.varType = eVarType.INT;
    }

    public void setResultReal() {
        this.varType = eVarType.REAL;
    }

    public eVarType getVarType() {
        return varType;
    }

    public void setResult(String result) {
        this.result = new AddrIO(result);
    }
}
