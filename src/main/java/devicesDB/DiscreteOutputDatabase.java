package devicesDB;

import devices.DevAnalogOutput;
import devices.DevDiscreteOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DiscreteOutputDatabase {

    // Единственный экземпляр класса
    private static DiscreteOutputDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<DevDiscreteOutput> records;

    // Приватный конструктор для предотвращения создания экземпляров
    private DiscreteOutputDatabase() {
        records = new ArrayList<DevDiscreteOutput>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized DiscreteOutputDatabase getInstance() {
        if (instance == null) {
            instance = new DiscreteOutputDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevDiscreteOutput record) {
        records.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevDiscreteOutput record) {
        records.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevDiscreteOutput> getRecords() {
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
