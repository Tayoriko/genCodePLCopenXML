package dev;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
        int id = -1;
        if (row.getCell(2).getCellType().equals(CellType.NUMERIC)){
            id = (int) row.getCell(2).getNumericCellValue();
        }
        if (row.getCell(2).getCellType().equals(CellType.STRING)){
            System.out.println(row.getCell(2).getStringCellValue());
            id = Integer.parseInt(String.valueOf(row.getCell(2).getStringCellValue()));
        }
        this.id = id;
        this.name = row.getCell(0).getStringCellValue();
        this.devName = row.getCell(1).getStringCellValue();
        this.comment = getCellAsString(row, 9);
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

    private String getCellAsString(Row row, int cellId) {
        Cell cell = row.getCell(cellId);
        String cellValue = null;
        if (cell != null && cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue();
            if (value != null && !value.isEmpty()) {
                cellValue = value;
            }
        }
        return (cellValue != null) ? cellValue : "unknown device";
    }

    @Override
    public String toString() {
        return "RawDev{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", devName='" + devName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
