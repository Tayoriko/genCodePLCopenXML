package devicesDB;

import devices.DevAnalogInput;
import devices.DevFlowMeter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FlowMetersDatabase {

    // Единственный экземпляр класса
    private static FlowMetersDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<DevFlowMeter> records;

    // Приватный конструктор для предотвращения создания экземпляров
    private FlowMetersDatabase() {
        records = new ArrayList<DevFlowMeter>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized FlowMetersDatabase getInstance() {
        if (instance == null) {
            instance = new FlowMetersDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevFlowMeter record) {
        records.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevFlowMeter record) {
        records.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevFlowMeter> getRecords() {
        return Collections.unmodifiableList(records);
    }

    // Метод для очистки базы данных
    public void clear() {
        records.clear();
    }

    // Пример метода для вывода всех аварий в консоль
    public void printAllRecords() {
        records.forEach(record -> System.out.println(record.toString()));
    }

    // Пример метода для вывода всех аварий в консоль
    public String getAllRecords() {
        return records.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }
}
