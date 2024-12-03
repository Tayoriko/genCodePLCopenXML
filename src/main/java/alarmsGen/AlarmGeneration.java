package alarmsGen;

import databases.GData;
import enums.eHMI;
import enums.ePLC;
import enums.eTemplate;


import java.io.File;
import java.io.IOException;

import static enums.eDevType.*;

public class AlarmGeneration {

    public static void generateXlsx(File source, String target) throws IOException {

        generate(source, target);

        // Экспортируем базу данных в Excel
        AlarmDatabaseExporter.exportToExcelWeintek(AlarmDatabase.getInstance(), target);
    }

    private static void generate (File source, String target) throws IOException {
        AlarmDatabase database = AlarmDatabase.getInstance();
        database.clear();
        eHMI hmi = GData.getHmi();
        ePLC protocol = GData.getPlc();
        eTemplate template = GData.getTemplate();

        // Загружаем аварии для каждого типа устройства из разных файлов
        AlarmSets alarmSets = new AlarmSets(template);

        //Создаём записи в базе
        AlarmCreator recordsCreator = new AlarmCreator(source, protocol);
        recordsCreator.reviewAlarms(protocol, AI);
        recordsCreator.reviewAlarms(protocol, MOTOR);
        recordsCreator.reviewAlarms(protocol, VALVE);

    }
}
