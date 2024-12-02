package codesys;

import dev.DevAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodesysAOTest {
    int id = 1;
    String name = "Setpoint for UZ 1";
    String devName = "sp_UZ_1";
    String comment = "Speed for UZ 1 - conveyor for spread";
    String result = "A3.AO2";
    String message = "Codesys - DevAO";

    @Test
    void createCodesysDevAoReal() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        CodesysCallDevices.setUseNetData(false);
        StringBuilder actualResult  = CodesysCallDevices.callAo(devAO);
        String expectedResult = String.format(
                "// #001 - Setpoint for UZ 1\n" +
                "drvAO[1](\n" +
                "   command     := IOL.sp_UZ_1,\n" +
                "   cmd         := CVL.cmdAO[1],\n" +
                "   cfg         := RVL.cfgAO[1],\n" +
                "   state       := SVL.stateAO[1],\n" +
                "   resultR     => SIG.listAO[3, 2]);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevAoInt() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        devAO.setResultInt();
        CodesysCallDevices.setUseNetData(false);
        StringBuilder actualResult  = CodesysCallDevices.callAo(devAO);
        String expectedResult = String.format(
                "// #001 - Setpoint for UZ 1\n" +
                "drvAO[1](\n" +
                "   command     := IOL.sp_UZ_1,\n" +
                "   cmd         := CVL.cmdAO[1],\n" +
                "   cfg         := RVL.cfgAO[1],\n" +
                "   state       := SVL.stateAO[1],\n" +
                "   resultI     => SIG.listAO[3, 2]);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevAoNetData() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        CodesysCallDevices.setUseNetData(true);
        StringBuilder actualResult  = CodesysCallDevices.callAo(devAO);
        String expectedResult = String.format(
                "// #001 - Setpoint for UZ 1\n" +
                "drvAO[1](\n" +
                "   command     := IOL.sp_UZ_1,\n" +
                "   cmd         := CVL.cmdAO[1],\n" +
                "   cfg         := RVL.cfgAO[1],\n" +
                "   state       := SVL.stateAO[1],\n" +
                "   netData     := NVL.sp_UZ_1,\n" +
                "   resultR     => SIG.listAO[3, 2]);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }
}