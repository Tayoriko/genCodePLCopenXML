package dev;

public class AddrIO {

    private int word = 0;
    private int bit = 0;
    private boolean enable = false;

    public AddrIO(String addr) {
        if (!addr.isEmpty() && !addr.equals("empty")){
            String[] splitAddress = splitAddress(addr);
            this.word = Integer.parseInt(splitAddress[0]);
            this.bit = Integer.parseInt(splitAddress[1]);
            this.enable = true;
        }
    }

    private String[] splitAddress(String address) {
        String[] parts = address.split("\\.");
        String word = parts[0].replaceAll("\\D", ""); // Убирает все символы, кроме цифр
        String bit = parts[1].replaceAll("\\D", "");  // Убирает все символы, кроме цифр
        return new String[]{word, bit};
    }

    public int getWord() {
        return word;
    }

    public int getBit() {
        return bit;
    }

    public boolean isEnable() {
        return enable;
    }
}
