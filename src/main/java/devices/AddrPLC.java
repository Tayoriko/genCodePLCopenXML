package devices;

public class AddrPLC {
    private int word = 0;
    private int bit = 0;

    public AddrPLC(String word, String bit) {
        this.word = Integer.parseInt(word);
        this.bit = Integer.parseInt(bit);
    }

    public AddrPLC(String addr) {
        String[] splitAddress = splitAddress(addr);
        this.word = Integer.parseInt(splitAddress[0]);
        this.bit = Integer.parseInt(splitAddress[1]);
    }

    public String getAddrCodesys() {
        return "[" + word + "]." + bit;
    }

    private static String[] splitAddress(String address) {
        String[] parts = address.split("\\.");
        String word = parts[0].replaceAll("\\D", ""); // Убирает все символы, кроме цифр
        String bit = parts[1].replaceAll("\\D", "");  // Убирает все символы, кроме цифр
        return new String[]{word, bit};
    }
}
