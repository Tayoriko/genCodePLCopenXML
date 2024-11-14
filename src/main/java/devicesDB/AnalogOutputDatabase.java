package devicesDB;

import devices.DevAnalogOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnalogOutputDatabase {

    // Единственный экземпляр класса
    private static AnalogOutputDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<DevAnalogOutput> records;

    // Приватный конструктор для предотвращения создания экземпляров
    private AnalogOutputDatabase() {
        records = new ArrayList<DevAnalogOutput>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized AnalogOutputDatabase getInstance() {
        if (instance == null) {
            instance = new AnalogOutputDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevAnalogOutput record) {
        records.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevAnalogOutput record) {
        records.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevAnalogOutput> getRecords() {
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
