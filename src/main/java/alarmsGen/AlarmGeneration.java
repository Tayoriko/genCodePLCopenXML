package alarmsGen;

import enums.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class AlarmGeneration {

    public static void generate(File source, String target) throws IOException {
        AlarmDatabase database = AlarmDatabase.getInstance();
        database.clear();
        eHMI hmi = AlarmApp.getHmi();
        eProtocol protocol = AlarmApp.getProtocol();
        eTemplate template = AlarmApp.getTemplate();

        // Загружаем аварии для каждого типа устройства из разных файлов
        AlarmSets alarmSets = new AlarmSets(template);

        //Создаём записи в базе
        AlarmCreator alarmCreator = new AlarmCreator(source, protocol);

        // Экспортируем базу данных в Excel
        AlarmDatabaseExporter.exportToExcelWeintek(database, target);

    }



    static String loadFilePath(eDevType type) {
        String filePath = "";
        switch (type) {
            case MOTOR -> {
                filePath = FilePath.BASIC_MOTOR;
                break;
            }
            case VALVE -> {
                filePath = FilePath.BASIC_VALVE;
                break;
            }
            case AI -> {
                filePath = FilePath.BASIC_ANALOG_INPUT;
                break;
            }
        }
        return filePath;
    }
}
