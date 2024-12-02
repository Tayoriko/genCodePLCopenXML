package codesys;

import dev.DevDI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodesysDITest {
    int id = 1;
    String name = "External Reset";
    String devName = "keyReset";
    String comment = "Button for reset all alarms";
    String signal = "A2.DI4";
    String message = "Codesys - DevDI";

    @Test
    void createCodesysDevDi() {
        DevDI devDI = new DevDI(id, name, devName, comment, signal);
        CodesysCallDevices.setUseNetData(false);
        StringBuilder actualResult  = CodesysCallDevices.callDi(devDI);
        String expectedResult = String.format(
                "// #001 - External Reset\n" +
                "drvDI[1](\n" +
                "   signal      := SIG.listDI[2].4,\n" +
                "   cmd         := CVL.cmdDI[1],\n" +
                "   cfg         := RVL.cfgDI[1],\n" +
                "   result      => IOL.keyReset);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevDiNetData() {
        DevDI devDI = new DevDI(id, name, devName, comment, signal);
        CodesysCallDevices.setUseNetData(true);
        StringBuilder actualResult  = CodesysCallDevices.callDi(devDI);
        String expectedResult = String.format(
                "// #001 - External Reset\n" +
                "drvDI[1](\n" +
                "   signal      := SIG.listDI[2].4,\n" +
                "   cmd         := CVL.cmdDI[1],\n" +
                "   cfg         := RVL.cfgDI[1],\n" +
                "   netData     := NVL.keyReset,\n" +
                "   result      => IOL.keyReset);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

}