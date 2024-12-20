package alarmsGen;

import databases.GData;
import enums.eDevType;
import enums.eTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlarmSets {

    // TODO: add language option from configuration

    private static Set<Map.Entry<String, String>> motorAlarmSet = Set.of();
    private static Set<Map.Entry<String, String>> valveAlarmSet = Set.of();
    private static Set<Map.Entry<String, String>> analogAlarmInputSet = Set.of();
    private static Set<Map.Entry<String, String>> analogAlarmOutputSet = Set.of();

    AlarmSets (eTemplate template) {
        motorAlarmSet = loadAlarmSet(template, eDevType.MOTOR);
        valveAlarmSet = loadAlarmSet(template, eDevType.VALVE);
        analogAlarmInputSet = loadAlarmSet(template, eDevType.AI);
        analogAlarmOutputSet = loadAlarmSet(template, eDevType.AO);
    }

    private static Set<Map.Entry<String, String>> loadAlarmSet(eTemplate template, eDevType type) {
        Set<Map.Entry<String, String>> set = Set.of();
        String filePath = "";
        filePath = loadFilePath(type, template);
        set = loadAlarmsFromCsv(filePath, "ru");
        return set;
    }

    public static Set<Map.Entry<String, String>> getMotorAlarmSet() {
        return motorAlarmSet;
    }

    public static Set<Map.Entry<String, String>> getValveAlarmSet() {
        return valveAlarmSet;
    }

    public static Set<Map.Entry<String, String>> getAnalogInputAlarmSet() {
        return analogAlarmInputSet;
    }

    public static Set<Map.Entry<String, String>> getAnalogAlarmOutputSet() {
        return analogAlarmOutputSet;
    }

    // Метод для чтения CSV-файла и создания структуры данных с выбором языка
    private static Set<Map.Entry<String, String>> loadAlarmsFromCsv(String filePath, String language) {
        Map<String, String> alarmMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Чтение файла построчно
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3); // Изменено на 3, чтобы учесть третий элемент

                // Проверка на корректность строки
                if (parts.length >= 3) {
                    String address = parts[0].trim();
                    String russianText = parts[1].trim().replace("\"", "");
                    String englishText = parts[2].trim().replace("\"", "");

                    // Выбор текста в зависимости от языка
                    String alarmText = language.equals("ru") ? russianText : englishText;
                    alarmMap.put(address, alarmText);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        // Возвращаем Set из Map
        return alarmMap.entrySet();
    }

    private static String loadFilePath(eDevType type, eTemplate template) {
        String filePath = "";
        switch (template) {
            case BASIC -> {
                filePath = GData.BASIC;
                switch (type) {
                    case MOTOR -> {
                        filePath += "motor.csv";
                        break;
                    }
                    case VALVE -> {
                        filePath += "valve.csv";
                        break;
                    }
                    case AI -> {
                        filePath += "analog_input.csv";
                        break;
                    }
                }
            }
            case CUSTOM_MV210 -> {
                filePath = GData.CUSTOM_MV210;
                switch (type) {
                    case MOTOR -> {
                        filePath += "motor.csv";
                        break;
                    }
                    case VALVE -> {
                        filePath += "valve.csv";
                        break;
                    }
                    case AI -> {
                        filePath += "analog_input.csv";
                        break;
                    }
                    case AO -> {
                        filePath += "analog_output.csv";
                        break;
                    }
                }
                break;
            }
        }
        return filePath;
    }
}
