package codesys;

import dev.DevAI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodesysAITest {
    int id = 1;
    String name = "Temperature 1";
    String devName = "TE_1";
    String comment = "Temperature in tank 1";
    String signal = "A4.AI1";
    String message = "Codesys - DevAI";

    @Test
    void createCodesysDevAiReal() {
        DevAI devAI = new DevAI(id, name, devName, comment, signal);
        CodesysCallDevices.setUseNetData(false);
        StringBuilder actualResult  = CodesysCallDevices.callAi(devAI);
        String expectedResult = String.format(
                "// #001 - Temperature 1\n" +
                "drvAI[1](\n" +
                "   signal      := SIG.listAI[4, 1],\n" +
                "   cmd         := CVL.cmdAI[1],\n" +
                "   cfg         := RVL.cfgAI[1],\n" +
                "   state       := SVL.stateAI[1],\n" +
                "   result      => IOL.TE_1);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevAiInt() {
        DevAI devAI = new DevAI(id, name, devName, comment, signal);
        devAI.setSignalInt();
        CodesysCallDevices.setUseNetData(false);
        StringBuilder actualResult  = CodesysCallDevices.callAi(devAI);
        String expectedResult = String.format(
                "// #001 - Temperature 1\n" +
                "drvAI[1](\n" +
                "   signal      := INT_TO_REAL(SIG.listAI[4, 1]),\n" +
                "   cmd         := CVL.cmdAI[1],\n" +
                "   cfg         := RVL.cfgAI[1],\n" +
                "   state       := SVL.stateAI[1],\n" +
                "   result      => IOL.TE_1);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevAoNetData() {
        DevAI devAI = new DevAI(id, name, devName, comment, signal);
        CodesysCallDevices.setUseNetData(true);
        StringBuilder actualResult  = CodesysCallDevices.callAi(devAI);
        String expectedResult = String.format(
                "// #001 - Temperature 1\n" +
                "drvAI[1](\n" +
                "   signal      := SIG.listAI[4, 1],\n" +
                "   cmd         := CVL.cmdAI[1],\n" +
                "   cfg         := RVL.cfgAI[1],\n" +
                "   state       := SVL.stateAI[1],\n" +
                "   netData     := NVL.TE_1,\n" +
                "   result      => IOL.TE_1);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }
}