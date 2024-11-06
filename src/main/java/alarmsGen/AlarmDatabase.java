package alarmsGen;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class AlarmDatabase {
    private List<AlarmConfig> alarms;

    // Конструктор, инициализирующий пустую базу данных аварий
    public AlarmDatabase() {
        this.alarms = new ArrayList<>();
    }

    // Метод для добавления новой аварии с параметрами addressRead и content (старый метод)
    public void addAlarm(String addressRead, String content) {
        AlarmConfig alarm = new AlarmConfig(addressRead, content);
        alarms.add(alarm);
    }

    // Новый метод для добавления аварии через объект AlarmConfig
    public void addAlarm(AlarmConfig alarm) {
        alarms.add(alarm);
    }

    // Метод для получения аварии по индексу
    public AlarmConfig getAlarm(int index) {
        if (index >= 0 && index < alarms.size()) {
            return alarms.get(index);
        } else {
            throw new IndexOutOfBoundsException("Неверный индекс аварии: " + index);
        }
    }

    // Метод для получения всех аварий
    public List<AlarmConfig> getAllAlarms() {
        return new ArrayList<>(alarms);
    }

    // Метод для вывода всех аварий в консоль
    public void printAllAlarms() {
        for (AlarmConfig alarm : alarms) {
            System.out.println(alarm);
        }
    }

    // Метод для получения количества аварий в базе данных
    public int getAlarmCount() {
        return alarms.size();
    }
}
