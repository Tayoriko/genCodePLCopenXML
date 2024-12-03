package appFx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import databases.GData;

public class XmlSaver {

    public static void saveXml(StringBuilder content) {
        // Получаем путь и имя файла из GData
        String folderPath = GData.getTargetFolder(); // Пример: "C:/output/"
        String fileName = GData.getProjectName() + "_PLCopenXML.xml";     // Пример: "example.xml"

        // Проверяем, что путь заканчивается на "/", и формируем полный путь
        if (!folderPath.endsWith(File.separator)) {
            folderPath += File.separator;
        }

        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Создаем папки, если их нет
        }

        File file = new File(folderPath + fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
            System.out.println("XML файл успешно сохранён: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении XML файла: " + e.getMessage());
        }
    }
}