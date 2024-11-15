package devicesDB;

import devices.DevMotor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MotorDatabase {

    // Единственный экземпляр класса
    private static MotorDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<DevMotor> records;

    // Приватный конструктор для предотвращения создания экземпляров
    private MotorDatabase() {
        records = new ArrayList<DevMotor>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized MotorDatabase getInstance() {
        if (instance == null) {
            instance = new MotorDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevMotor record) {
        records.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevMotor record) {
        records.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevMotor> getRecords() {
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
