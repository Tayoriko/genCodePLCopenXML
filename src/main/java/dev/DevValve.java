package dev;

import enums.eDevType;

public class DevValve extends AbstractDevice{
    private final AddrIO fbQf;
    private final AddrIO fbOpen;
    private final AddrIO fbClose;
    private final AddrIO cmdOpen;
    private final AddrIO cmdClose;

    public DevValve(Integer id, String name, String devName, String comment, String fbQf, String fbOpen, String fbClose, String cmdOpen, String cmdClose){
        super(id, eDevType.VALVE, name, devName, comment);
        this.fbQf = new AddrIO(fbQf);
        this.fbOpen = new AddrIO(fbOpen);
        this.fbClose = new AddrIO(fbClose);
        this.cmdOpen = new AddrIO(cmdOpen);
        this.cmdClose = new AddrIO(cmdClose);
    }

    public DevValve(RawDev rawDev, String fbQf, String fbOpen, String fbClose, String cmdOpen, String cmdClose){
        super(rawDev, eDevType.VALVE);
        this.fbQf = new AddrIO(fbQf);
        this.fbOpen = new AddrIO(fbOpen);
        this.fbClose = new AddrIO(fbClose);
        this.cmdOpen = new AddrIO(cmdOpen);
        this.cmdClose = new AddrIO(cmdClose);
    }

    public AddrIO getFbQf() {
        return fbQf;
    }

    public AddrIO getFbOpen() {
        return fbOpen;
    }

    public AddrIO getFbClose() {
        return fbClose;
    }

    public AddrIO getCmdOpen() {
        return cmdOpen;
    }

    public AddrIO getCmdClose() {
        return cmdClose;
    }
}
