package codesys;

import dev.AddrIO;


public class CodesysAddressing {
    private static final String varList = "SIG";
    private static String netList = "NVL";
    private static String ioList = "IOL";
    private static String signalDi = varList + ".listDI";
    private static String signalAi = varList + ".listAI";
    private static String signalDo = varList + ".listDO";
    private static String signalAo = varList + ".listAO";
    public static final String noData = "(*input address here*)";

    public CodesysAddressing() {

    }

    public static String getAddrDi(AddrIO addrIO) {
        String data = signalDi;
        if (addrIO.getWord() == 0 && addrIO.getBit() == 0) {
            return noData;
        }
        else return data += "[" + addrIO.getWord() + "]." + addrIO.getBit();
    }

    public static String getAddrAi(AddrIO addrIO) {
        String data = signalAi;
        if (addrIO.getWord() == 0) {
            return noData;
        }
        else return data += "[" + addrIO.getWord() + ", " + addrIO.getBit() + "]";
    }

    public static String getAddrDo(AddrIO addrIO) {
        String data = signalDo;
        if (addrIO.getWord() == 0 && addrIO.getBit() == 0) {
            return noData;
        }
        else return data += "[" + addrIO.getWord() + "]." + addrIO.getBit();
    }

    public static String getAddrAo(AddrIO addrIO) {
        String data = signalAo;
        if (addrIO.getWord() == 0) {
            return noData;
        }
        else return data += "[" + addrIO.getWord() + ", " + addrIO.getBit() + "]";
    }

    public static String getIoList(String devName){
        String data = ioList + "." + devName;
        return data;
    }

    public static String getNetList(String devName){
        String data = netList +"." + devName;
        return data;
    }
}
