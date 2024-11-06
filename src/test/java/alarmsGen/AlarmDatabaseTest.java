package alarmsGen;

import enums.eDevType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class AlarmDatabaseTest {
    AlarmDatabase alarmDatabase = new AlarmDatabase();
    String filePathMotor = "./src/main/resources/sources/motor.csv";
    String filePathValve = "./src/main/resources/sources/valve.csv";
    String filePathAI = "./src/main/resources/sources/analog_input.csv";
    String filePathDevices = "./src/main/resources/sources/devices.xlsx";
    String filePathResult = "./src/main/resources/results/test.xlsx";

    @Test
    void testDB() {
        // Добавляем несколько аварий
        alarmDatabase.addAlarm("Application.GVL.warnOt_1", "Требуется ТО1");
        alarmDatabase.addAlarm("Application.GVL.warnOt_2", "Ошибка датчика температуры");

        // Вывод всех аварий
        alarmDatabase.printAllAlarms();

        // Получение аварии по индексу
        AlarmConfig alarm = alarmDatabase.getAlarm(1);
        System.out.println("Получена авария: " + alarm);

        // Количество аварий
        System.out.println("Всего аварий: " + alarmDatabase.getAlarmCount());
    }

    @Test
    void testFileRead() {
        // Загружаем список аварий из CSV файла
        Set<Map.Entry<String, String>> alarmSet = AlarmLoader.loadAlarmsFromCsv(filePathMotor);

        // Вывод списка аварий
        for (Map.Entry<String, String> entry : alarmSet) {
            System.out.println("Address: " + entry.getKey() + ", Alarm Text: " + entry.getValue());
        }
    }

    @Test
    void testGetDataFromFile() {
        try {
            Set<Map.Entry<String, String>> alarmSet = AlarmLoader.loadAlarmsFromCsv(filePathMotor);

            List<AlarmConfig> alarms = AlarmCreator.createAlarmsFromExcel(filePathDevices, eDevType.MOTOR, alarmSet);

            for (AlarmConfig alarm : alarms) {
                System.out.println(alarm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testExportDB() {
        try {

            // Создаем базу данных для хранения всех аварий
            AlarmDatabase alarmDatabase = new AlarmDatabase();

            // Сохраняем все аварии для всех типов устройств в базу данных
            AlarmDataAggregator.aggregateAlarmsToDatabase(
                    filePathDevices,
                    filePathMotor,
                    filePathValve,
                    filePathAI,
                    alarmDatabase
            );

            // Экспортируем базу данных в Excel
            AlarmDatabaseExporter.exportToExcel(alarmDatabase, filePathResult);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}