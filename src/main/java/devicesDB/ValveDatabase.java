package devicesDB;

import devices.DevMotor;
import devices.DevValve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValveDatabase {

    // Единственный экземпляр класса
    private static ValveDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<DevValve> records;

    // Приватный конструктор для предотвращения создания экземпляров
    private ValveDatabase() {
        records = new ArrayList<DevValve>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized ValveDatabase getInstance() {
        if (instance == null) {
            instance = new ValveDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevValve record) {
        records.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevValve record) {
        records.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevValve> getRecords() {
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
