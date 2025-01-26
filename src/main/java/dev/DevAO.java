package dev;

import databases.GData;
import enums.eDevType;
import enums.eVarType;

public class DevAO extends AbstractDevice{
    private AddrIO result;
    private AddrIO fbQf;
    private boolean useQf = false;
    private eVarType varType = eVarType.REAL;

    public DevAO(Integer id, String name, String devName, String comment, String result){
        super(id, eDevType.AO, name, devName, comment);
        this.result = new AddrIO(result);
    }

    public DevAO(RawDev rawDev, String result, String fbQf){
        super(rawDev, eDevType.AO);
        this.fbQf = new AddrIO(fbQf);
        this.result = new AddrIO(result);
    }

    public DevAO(RawDev rawDev){
        super(rawDev, eDevType.AO);
    }

    public AddrIO getResult() {
        return result;
    }

    public AddrIO getFbQf() {
        return fbQf;
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

    public boolean isUseQf() {
        return useQf;
    }

    public void setUseQf(boolean useQf) {
        this.useQf = useQf;
    }

    public void setResult(String result) {
        this.result = new AddrIO(result);
    }

    public void setFbQf(String fbQf) {
        this.fbQf = new AddrIO(fbQf);
    }
}
