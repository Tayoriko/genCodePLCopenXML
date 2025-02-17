package dev;

import databases.GData;
import enums.eDevType;

public abstract class AbstractDevice {

    private final Integer id;
    private final String name;      //name for user
    private final String devName;   //name for program
    private final String comment;
    private String header;
    private String cmd;
    private String cfg;
    private String state;
    private eDevType devType = eDevType.EMPTY;

    public AbstractDevice(Integer id, eDevType devType, String name, String devName, String comment) {
        this.id = id;
        this.devType = devType;
        this.name = name;
        this.devName = devName;
        this.comment = comment;
        this.header = "// " + setHeader(id, name);
        ioGen();
    }

    public AbstractDevice(RawDev devRawRow, eDevType devType) {
        this.id = devRawRow.getId();
        this.devType = devType;
        this.name = devRawRow.getName();
        this.devName = devRawRow.getDevName();
        this.comment = devRawRow.getComment();
        this.header = "// " + setHeader(id, name);
        ioGen();
    }

    private String ioCodesys(String varList, String option) {
        return varList + "." + option + getDev().getName()  + "[" + this.id + "]";
    }

    private void ioGen() {
        switch (GData.getPlc()) {
            case CODESYS -> {
                this.cmd = ioCodesys("CVL","cmd");
                this.cfg = ioCodesys("RVL","cfg");
                this.state = ioCodesys("SVL","state");
            }
        }
    }

    private String setHeader (int id, String name) {
        String text = "#";
        if (id < 100) {text += "0";}
        if (id < 10) {text += "0";}
        return text + id + " - " + name;
    }

    public eDevType getDev(){
        return devType;
    }

    public String getCommentIol() {
        return header.substring(3) + " - " + comment;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDevName() {
        return devName;
    }

    public String getComment() {
        return comment;
    }

    public String getHeader() {
        return header;
    }

    public String getCmd() {
        return cmd;
    }

    public String getCfg() {
        return cfg;
    }

    public String getState() {
        return state;
    }

    public eDevType getDevType() {
        return devType;
    }

    @Override
    public String toString() {
        return "AbstractDevice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", devName='" + devName + '\'' +
                ", comment='" + comment + '\'' +
                ", header='" + header + '\'' +
                ", cmd='" + cmd + '\'' +
                ", cfg='" + cfg + '\'' +
                ", state='" + state + '\'' +
                ", devType=" + devType +
                '}';
    }
}
