package devicesDB;

import devices.DevAnalogInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnalogInputDatabase {

    // Единственный экземпляр класса
    private static AnalogInputDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<DevAnalogInput> records;

    // Приватный конструктор для предотвращения создания экземпляров
    private AnalogInputDatabase() {
        records = new ArrayList<DevAnalogInput>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized AnalogInputDatabase getInstance() {
        if (instance == null) {
            instance = new AnalogInputDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevAnalogInput record) {
        records.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevAnalogInput record) {
        records.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevAnalogInput> getRecords() {
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
