package alarmsGen;

import enums.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static enums.eDevType.MOTOR;
import static org.junit.jupiter.api.Assertions.*;

class AlarmCreatorTest {

    @Test
    void createMessageCodesys() throws IOException {
        /*AlarmDatabase alarmDatabase = AlarmDatabase.getInstance();
        AlarmSets alarmSets = new AlarmSets(eTemplate.BASIC);
        AlarmCreator alarmCreator = new AlarmCreator(new File(FilePath.DEFAULT_SOURCE), eProtocol.CODESYS);
        AlarmDatabaseExporter.exportToExcelWeintek(alarmDatabase, String.valueOf(FilePath.DEFAULT_TARGET));
*/
        AlarmDatabase database = AlarmDatabase.getInstance();
        database.clear();
        eHMI hmi = eHMI.WEINTEK;// AlarmApp.getHmi();
        eProtocol protocol = eProtocol.CODESYS;// AlarmApp.getProtocol();
        eTemplate template = eTemplate.BASIC;//AlarmApp.getTemplate();

        // Загружаем аварии для каждого типа устройства из разных файлов
        AlarmSets alarmSets = new AlarmSets(template);

        //Создаём записи в базе
        AlarmCreator alarmCreator = new AlarmCreator(new File("C:\\Users\\enere\\IdeaProjects\\alarmsGen\\src\\main\\resources\\sources\\devices.xlsx"), protocol);

        // Экспортируем базу данных в Excel
        AlarmDatabaseExporter.exportToExcelWeintek(database, "C:\\Users\\enere\\IdeaProjects\\alarmsGen\\src\\main\\resources\\results\\test8.xlsx");
    }
}