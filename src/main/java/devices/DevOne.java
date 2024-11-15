package devices;

public class DevOne {
    private final String name;
    private String devName;
    private AddrPLC qf;
    private AddrPLC km;
    private AddrPLC fbOpen;
    private AddrPLC fbClose;
    private AddrPLC cmdFw;
    private AddrPLC cmdOpen;
    private AddrPLC cmdClose;
    private AddrPLC signal;

    // Конструктор для инициализации всех полей
    public DevOne(String name, String devName, AddrPLC qf, AddrPLC km, AddrPLC fbOpen, AddrPLC fbClose, AddrPLC cmdFw, AddrPLC cmdOpen, AddrPLC cmdClose) {
        this.name = name;
        this.devName = devName;
        this.qf = qf;
        this.km = km;
        this.fbOpen = fbOpen;
        this.fbClose = fbClose;
        this.cmdFw = cmdFw;
        this.cmdOpen = cmdOpen;
        this.cmdClose = cmdClose;
    }

    // Конструктор для инициализации мотора
    public DevOne(String name, String devName, AddrPLC qf, AddrPLC km, AddrPLC cmdFw) {
        this.name = name;
        this.devName = devName;
        this.qf = qf;
        this.km = km;
        this.cmdFw = cmdFw;
    }

    // Конструктор для инициализации клапана
    public DevOne(String name, String devName, AddrPLC qf, AddrPLC fbOpen, AddrPLC fbClose, AddrPLC cmdOpen, AddrPLC cmdClose) {
        this.name = name;
        this.devName = devName;
        this.qf = qf;
        this.fbOpen = fbOpen;
        this.fbClose = fbClose;
        this.cmdOpen = cmdOpen;
        this.cmdClose = cmdClose;
    }

    // Конструктор для аналогового/дискретного сигнала
    public DevOne(String name, String devName, AddrPLC signal) {
        this.name = name;
        this.devName = devName;
        this.signal = signal;
    }

    // Геттеры для каждого поля
    public String getName() {
        return name;
    }

    public String getDevName() {
        return devName;
    }

    public AddrPLC getQf() {
        return qf;
    }

    public AddrPLC getKm() {
        return km;
    }

    public AddrPLC getFbOpen() {
        return fbOpen;
    }

    public AddrPLC getFbClose() {
        return fbClose;
    }

    public AddrPLC getCmdFw() {
        return cmdFw;
    }

    public AddrPLC getCmdOpen() {
        return cmdOpen;
    }

    public AddrPLC getCmdClose() {
        return cmdClose;
    }

    public AddrPLC getSignal() {
        return signal;
    }

    // Метод для отображения информации об устройстве
    @Override
    public String toString() {
        return String.format(
                "Device Parameters:\n" +
                        "Name: %s\n" +
                        "QF: %s\n" +
                        "KM: %s\n" +
                        "FB Open: %s\n" +
                        "FB Close: %s\n" +
                        "CMD FW: %s\n" +
                        "CMD Open: %s\n" +
                        "CMD Close: %s\n",
                name, qf, km, fbOpen, fbClose, cmdFw, cmdOpen, cmdClose
        );
    }
}

