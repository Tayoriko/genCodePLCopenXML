package alarmsGen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlarmLoader {

    // Метод для чтения CSV-файла и создания структуры данных
    public static Set<Map.Entry<String, String>> loadAlarmsFromCsv(String filePath) {
        Map<String, String> alarmMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Чтение файла построчно
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);

                // Проверка на корректность строки
                if (parts.length == 2) {
                    String address = parts[0].trim();
                    String alarmText = parts[1].trim().replace("\"", "");
                    alarmMap.put(address, alarmText);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        // Возвращаем Set из Map
        return alarmMap.entrySet();
    }
}
