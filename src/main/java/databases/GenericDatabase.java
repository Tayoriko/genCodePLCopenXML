package databases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenericDatabase<T> {

        // Единственный экземпляр класса
        private static final GenericDatabase<?> INSTANCE = new GenericDatabase<>();

        // Список для хранения объектов
        private final List<T> records;

        // Приватный конструктор для предотвращения создания экземпляров
        GenericDatabase() {
            records = new ArrayList<>();
        }

        // Метод для получения экземпляра базы данных с типом T
        @SuppressWarnings("unchecked")
        public static <T> GenericDatabase<T> getInstance() {
            return (GenericDatabase<T>) INSTANCE;
        }

        // Метод для добавления записи в базу данных
        public void addRecord(T record) {
            records.add(record);
        }

        // Метод для удаления записи из базы данных
        public void removeRecord(T record) {
            records.remove(record);
        }

        // Метод для получения всех записей (неизменяемый список)
        public List<T> getRecords() {
            return Collections.unmodifiableList(records);
        }

        // Метод для очистки базы данных
        public void clear() {
            records.clear();
        }

        // Пример метода для вывода всех записей в консоль
        public void printAllRecords() {
            records.forEach(record -> System.out.println(record.toString()));
        }

        // Пример метода для получения всех записей в виде строки
        public String getAllRecordsAsString() {
            return records.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n"));
        }

}
