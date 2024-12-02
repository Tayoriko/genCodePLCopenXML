package dev;

import enums.eDevType;

public abstract class AbstractDevice {

    private final Integer id;
    private final String name;
    private final String devName;
    private final String comment;
    private String header;
    private final String cmd;
    private final String cfg;
    private final String state;
    private eDevType devType = eDevType.EMPTY;

    public AbstractDevice(Integer id, eDevType devType, String name, String devName, String comment) {
        this.id = id;
        this.devType = devType;
        this.name = name;
        this.devName = devName;
        this.comment = comment;
        this.header = "// " + setHeader(id, name);
        this.cmd = "CVL.cmd" + getDev().getName() + "[" + id + "]";
        this.cfg = "RVL.cfg" + getDev().getName()  + "[" + id + "]";
        this.state = "SVL.state" + getDev().getName()  + "[" + id + "]";
    }

    public AbstractDevice(RawDev devRawRow, eDevType devType) {
        this.id = devRawRow.getId();
        this.devType = devType;
        this.name = devRawRow.getName();
        this.devName = devRawRow.getDevName();
        this.comment = devRawRow.getComment();
        this.header = "// " + setHeader(id, name);
        this.cmd = "CVL.cmd" + getDev().getName() + "[" + id + "]";
        this.cfg = "RVL.cfg" + getDev().getName()  + "[" + id + "]";
        this.state = "SVL.state" + getDev().getName()  + "[" + id + "]";
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
}
