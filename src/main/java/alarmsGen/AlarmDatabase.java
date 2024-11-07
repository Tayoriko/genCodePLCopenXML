package alarmsGen;

import alarmsBase.AlarmMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlarmDatabase {

    // Единственный экземпляр класса
    private static AlarmDatabase instance;

    // Список для хранения объектов AlarmMessage
    private final List<AlarmMessage> alarms;

    // Приватный конструктор для предотвращения создания экземпляров
    private AlarmDatabase() {
        alarms = new ArrayList<AlarmMessage>();
    }

    // Метод для получения единственного экземпляра класса (Singleton)
    public static synchronized AlarmDatabase getInstance() {
        if (instance == null) {
            instance = new AlarmDatabase();
        }
        return instance;
    }

    // Метод для добавления аварийного сообщения в базу данных
    public void addAlarm(AlarmMessage alarm) {
        alarms.add(alarm);
    }

    // Метод для удаления аварийного сообщения из базы данных
    public void removeAlarm(AlarmMessage alarm) {
        alarms.remove(alarm);
    }

    // Метод для получения всех аварийных сообщений (неизменяемый список)
    public List<AlarmMessage> getAlarms() {
        return Collections.unmodifiableList(alarms);
    }

    // Метод для очистки базы данных
    public void clear() {
        alarms.clear();
    }

    // Пример метода для вывода всех аварий в консоль
    public void printAllAlarms() {
        alarms.forEach(alarm -> System.out.println(alarm.generateContent()));
    }
}
