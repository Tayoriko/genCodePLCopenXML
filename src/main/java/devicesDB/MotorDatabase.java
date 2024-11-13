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
    private final List<DevMotor> motors;

    // Приватный конструктор для предотвращения создания экземпляров
    private MotorDatabase() {
        motors = new ArrayList<DevMotor>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized MotorDatabase getInstance() {
        if (instance == null) {
            instance = new MotorDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(DevMotor motor) {
        motors.add(motor);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(DevMotor motor) {
        motors.remove(motor);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<DevMotor> getRecords() {
        return Collections.unmodifiableList(motors);
    }

    // Метод для очистки базы данных
    public void clear() {
        motors.clear();
    }

    // Пример метода для вывода всех аварий в консоль
    public void printAllRecords() {
        motors.forEach(motors -> System.out.println(motors.toString()));
    }

    // Пример метода для вывода всех аварий в консоль
    public String getAllRecords() {
        return motors.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }
}
