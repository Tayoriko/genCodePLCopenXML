package CSV;

import org.apache.velocity.app.VelocityEngine;

public class VelocityEngineSingleton {
    // Приватное статическое поле для хранения единственного экземпляра VelocityEngine
    private static volatile VelocityEngine instance;

    // Приватный конструктор, чтобы предотвратить создание экземпляров класса извне
    private VelocityEngineSingleton() {}

    // Метод для получения единственного экземпляра VelocityEngine
    public static VelocityEngine getInstance() {
        // "Ленивая" инициализация: если экземпляр еще не создан, создаем его
        if (instance == null) {
            synchronized (VelocityEngineSingleton.class) {
                if (instance == null) {
                    // Создаем движок Velocity
                    instance = new VelocityEngine();
                    try {
                        // Здесь можно выполнить настройку объекта VelocityEngine по вашему усмотрению
                        instance.init();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
