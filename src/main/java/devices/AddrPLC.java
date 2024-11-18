package devices;

public class AddrPLC {
    private int word = 0;
    private int bit = 0;
    private boolean use = true;
    private boolean intToReal = false;

    public AddrPLC() {
        use = false;
    }

    public AddrPLC(String word, String bit) {
        this.word = Integer.parseInt(word);
        this.bit = Integer.parseInt(bit);
    }

    public AddrPLC(String addr) {
        if (!addr.isEmpty()){
            String[] splitAddress = splitAddress(addr);
            this.word = Integer.parseInt(splitAddress[0]);
            this.bit = Integer.parseInt(splitAddress[1]);
        }
        else {
            this.word = -1;
            this.bit = 0;
        }
    }

    public String getAddrCodesysDiscrete() {
        String addr = "(*input address here*)";
        if (word == 0 && bit == 0) {return addr;}
        else return "[" + word + "]." + bit;
    }

    public String getAddrCodesysAnalog() {
        String addr = "(*input address here*)";
        if (word == 0) {return addr;}
        else return "[" + word + ", " + bit + "]";
    }

    private static String[] splitAddress(String address) {
        String[] parts = address.split("\\.");
        String word = parts[0].replaceAll("\\D", ""); // Убирает все символы, кроме цифр
        String bit = parts[1].replaceAll("\\D", "");  // Убирает все символы, кроме цифр
        return new String[]{word, bit};
    }

    public boolean isUse() {
        return use;
    }

    public boolean isIntToReal() {
        return intToReal;
    }

    public void setIntToReal(boolean intToReal) {
        this.intToReal = intToReal;
    }
}
