package devicesDB;

import devices.IOLrecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IOLdatabase {

    // Единственный экземпляр класса
    private static IOLdatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<IOLrecord> devList;

    // Приватный конструктор для предотвращения создания экземпляров
    private IOLdatabase() {
        devList = new ArrayList<IOLrecord>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized IOLdatabase getInstance() {
        if (instance == null) {
            instance = new IOLdatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addRecord(IOLrecord record) {
        devList.add(record);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeRecord(IOLrecord record) {
        devList.remove(record);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<IOLrecord> getDevList() {
        return Collections.unmodifiableList(devList);
    }

    // Метод для очистки базы данных
    public void clear() {
        devList.clear();
    }

    // Пример метода для вывода всех аварий в консоль
    public void printAllRecords() {
        devList.forEach(System.out::println);
    }

    // Пример метода для вывода всех аварий в консоль
    public List<IOLrecord> getAllRecords() {
        return new ArrayList<>(devList);
    }
}
