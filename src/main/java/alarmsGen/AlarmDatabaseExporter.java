package alarmsGen;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class AlarmDatabaseExporter {

    public static void exportToExcel(AlarmDatabase alarmDatabase, String outputFilePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Alarms");

        // Заголовок столбцов в требуемом порядке
        String[] headers = {
                "Category", "Priority", "Address Type", "PLC Name (Read)", "Device Type (Read)", "System Tag (Read)", "User-defined Tag (Read)",
                "Address (Read)", "Index (Read)", "Data Format (Read)", "Enable Notification", "Set ON (Notification)",
                "PLC Name (Notification)", "Device Type (Notification)", "System Tag (Notification)", "User-defined Tag (Notification)",
                "Address (Notification)", "Index (Notification)", "Condition", "Trigger Value", "Content", "Use Label Library",
                "Label Name", "Font", "Color", "Acknowledge Value", "Enable Sound"
        };

        // Создание заголовка
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Заполнение данных из базы данных
        int rowNum = 1;
        for (AlarmConfig alarm : alarmDatabase.getAllAlarms()) {
            Row row = sheet.createRow(rowNum++);

            // Заполняем ячейки согласно порядку указанных столбцов
            row.createCell(0).setCellValue(alarm.getCategory());
            row.createCell(1).setCellValue(alarm.getPriority());
            row.createCell(2).setCellValue(alarm.getAddressType());
            row.createCell(3).setCellValue(alarm.getPlcNameRead());
            row.createCell(4).setCellValue(alarm.getDataTypeRead());
            row.createCell(5).setCellValue(String.valueOf(alarm.isSystemTagRead()));
            row.createCell(6).setCellValue(String.valueOf(alarm.isUserDefinedTagRead()));
            row.createCell(7).setCellValue(alarm.getAddressRead());
            row.createCell(8).setCellValue(alarm.getIndexRead());
            row.createCell(9).setCellValue(alarm.getDataTypeRead());
            row.createCell(10).setCellValue(String.valueOf(alarm.isEnableNotification()));
            row.createCell(11).setCellValue(""); // Set ON (Notification) - добавьте значение при необходимости
            row.createCell(12).setCellValue(""); // PLC Name (Notification) - добавьте значение при необходимости
            row.createCell(13).setCellValue(""); // Device Type (Notification) - добавьте значение при необходимости
            row.createCell(14).setCellValue(""); // System Tag (Notification) - добавьте значение при необходимости
            row.createCell(15).setCellValue(""); // User-defined Tag (Notification) - добавьте значение при необходимости
            row.createCell(16).setCellValue(""); // Address (Notification) - добавьте значение при необходимости
            row.createCell(17).setCellValue(""); // Index (Notification) - добавьте значение при необходимости
            row.createCell(18).setCellValue(alarm.getCondition());
            row.createCell(19).setCellValue(alarm.getTriggerValue());
            row.createCell(20).setCellValue(alarm.getAlarmMessage());
            row.createCell(21).setCellValue(String.valueOf(alarm.isUseLabelLibrary()));
            row.createCell(22).setCellValue(alarm.getLabelName());
            row.createCell(23).setCellValue(alarm.getFont());
            row.createCell(24).setCellValue(alarm.getColor());
            row.createCell(25).setCellValue(alarm.getAcknowledgeValue());
            row.createCell(26).setCellValue(String.valueOf(alarm.isEnableSound()));
        }

        // Запись файла на диск
        try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            workbook.write(fileOut);
            System.out.println("Данные успешно экспортированы в файл: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

