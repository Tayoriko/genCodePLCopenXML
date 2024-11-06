package alarmsGen;

import enums.eDevType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlarmDataAggregator {

    public static void aggregateAlarmsToDatabase(
            String filePathDevices,
            String filePathMotorAlarms,
            String filePathValveAlarms,
            String filePathAnalogInputAlarms,
            AlarmDatabase alarmDatabase) throws IOException {

        // Загружаем аварии для каждого типа устройства из разных файлов
        Set<Map.Entry<String, String>> motorAlarmSet = AlarmLoader.loadAlarmsFromCsv(filePathMotorAlarms);
        Set<Map.Entry<String, String>> valveAlarmSet = AlarmLoader.loadAlarmsFromCsv(filePathValveAlarms);
        Set<Map.Entry<String, String>> analogInputAlarmSet = AlarmLoader.loadAlarmsFromCsv(filePathAnalogInputAlarms);

        // Обрабатываем данные для каждого типа устройства
        List<AlarmConfig> motorAlarms = AlarmCreator.createAlarmsFromExcel(filePathDevices, eDevType.MOTOR, motorAlarmSet);
        if (!motorAlarms.isEmpty()) {
            for (AlarmConfig alarm : motorAlarms) {
                alarmDatabase.addAlarm(alarm);
            }
        }

        List<AlarmConfig> valveAlarms = AlarmCreator.createAlarmsFromExcel(filePathDevices, eDevType.VALVE, valveAlarmSet);
        if (!valveAlarms.isEmpty()) {
            for (AlarmConfig alarm : valveAlarms) {
                alarmDatabase.addAlarm(alarm);
            }
        }

        List<AlarmConfig> analogInputAlarms = AlarmCreator.createAlarmsFromExcel(filePathDevices, eDevType.AI, analogInputAlarmSet);
        if (!analogInputAlarms.isEmpty()) {
            for (AlarmConfig alarm : analogInputAlarms) {
                alarmDatabase.addAlarm(alarm);
            }
        }
    }
}


