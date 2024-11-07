package alarmsGen;

import enums.eDevType;
import enums.eTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static alarmsGen.AlarmGeneration.loadFilePath;

public class AlarmSets {

    private static Set<Map.Entry<String, String>> motorAlarmSet = Set.of();
    private static Set<Map.Entry<String, String>> valveAlarmSet = Set.of();
    private static Set<Map.Entry<String, String>> analogAlarmSet = Set.of();

    AlarmSets (eTemplate template) {
        motorAlarmSet = loadAlarmSet(template, eDevType.MOTOR);
        valveAlarmSet = loadAlarmSet(template, eDevType.VALVE);
        analogAlarmSet = loadAlarmSet(template, eDevType.AI);
    }

    private static Set<Map.Entry<String, String>> loadAlarmSet(eTemplate template, eDevType type) {
        Set<Map.Entry<String, String>> set = Set.of();
        String filePath = "";
        switch (template) {
            case eTemplate.BASIC -> {
                filePath = loadFilePath(type);
                set = loadAlarmsFromCsv(filePath);
                break;
            }
        }
        return set;
    }

    public static Set<Map.Entry<String, String>> getMotorAlarmSet() {
        return motorAlarmSet;
    }

    public static Set<Map.Entry<String, String>> getValveAlarmSet() {
        return valveAlarmSet;
    }

    public static Set<Map.Entry<String, String>> getAnalogAlarmSet() {
        return analogAlarmSet;
    }

    // Метод для чтения CSV-файла и создания структуры данных
    private static Set<Map.Entry<String, String>> loadAlarmsFromCsv(String filePath) {
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
