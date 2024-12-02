package dev;

import enums.eDevType;

public class DevMotor extends AbstractDevice{
    private final AddrIO fbQf;
    private final AddrIO fbKm;
    private final AddrIO cmdFw;

    public DevMotor(Integer id, String name, String devName, String comment, String fbQf, String fbKm, String cmdFw){
        super(id, eDevType.MOTOR, name, devName, comment);
        this.fbQf = new AddrIO(fbQf);
        this.fbKm = new AddrIO(fbKm);
        this.cmdFw = new AddrIO(cmdFw);
    }

    public DevMotor(RawDev rawDev, String fbQf, String fbKm, String cmdFw){
        super(rawDev, eDevType.MOTOR);
        this.fbQf = new AddrIO(fbQf);
        this.fbKm = new AddrIO(fbKm);
        this.cmdFw = new AddrIO(cmdFw);
    }

    public AddrIO getFbQf() {
        return fbQf;
    }

    public AddrIO getFbKm() {
        return fbKm;
    }

    public AddrIO getCmdFw() {
        return cmdFw;
    }
}
