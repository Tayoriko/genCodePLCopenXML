package devHMI;

public class AlarmMessage {
    private String address;
    private String message;

    public AlarmMessage(String address, String message) {
        this.address = address;
        this.message = message;
    }

    // Геттер для адреса аварии
    public String getAddress() {
        return address;
    }

    // Сеттер для адреса аварии
    public void setAddress(String address) {
        this.address = address;
    }

    // Геттер для текста сообщения
    public String getMessage() {
        return message;
    }

    // Сеттер для текста сообщения
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Address: " + address + ", Message: " + message;
    }

    public String generateContent() {
        return String.format("AlarmMessage [Address=%s, Message=%s]",
                address, message);
    }
}
