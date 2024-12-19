package databases;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DatabaseRegistry {
    private static final Map<Class<?>, GenericDatabase<?>> registry = new HashMap<>();

    public static synchronized <T> GenericDatabase<T> getInstance(Class<T> clazz) {
        if (!registry.containsKey(clazz)) {
            registry.put(clazz, new GenericDatabase<>());
        }
        return (GenericDatabase<T>) registry.get(clazz);
    }

    public static Collection<GenericDatabase<?>> getAllDatabases() {
        return registry.values();
    }

    public static void clearAllDatabases() {
        // Перебираем все зарегистрированные базы данных
        DatabaseRegistry.getAllDatabases().forEach(GenericDatabase::clear);
    }

}
