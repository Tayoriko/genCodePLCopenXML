package dev;

import org.apache.poi.ss.usermodel.Row;

public class RawDev {
    private final int id;
    private final String name;
    private final String devName;
    private final String comment;

    public RawDev(int id, String name, String devName, String comment) {
        this.id = id;
        this.name = name;
        this.devName = devName;
        this.comment = comment;
    }

    public RawDev(Row row) {
        this.id = (int) row.getCell(2).getNumericCellValue();
        this.name = row.getCell(0).getStringCellValue();
        this.devName = row.getCell(1).getStringCellValue();
        this.comment = row.getCell(9).getStringCellValue();
    }

    public int getId() {
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
}
