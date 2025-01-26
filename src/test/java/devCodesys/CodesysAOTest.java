package devCodesys;

import devPLC.CodesysCallDevices;
import databases.GData;
import dev.DevAO;
import enums.eActions;
import enums.ePLC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodesysAOTest {
    int id = 1;
    String name = "Setpoint for UZ 1";
    String devName = "sp_UZ_1";
    String comment = "Speed for UZ 1 - conveyor for spread";
    String result = "A3.AO2";
    String message = "Codesys - DevAO";

    @BeforeAll
    static void init(){
        GData.setPlc(ePLC.CODESYS);
    }

    @Test
    void createCodesysDevAoReal() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        GData.getActions().remove(eActions.MBS);
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
        GData.getActions().remove(eActions.MBS);
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
        GData.getActions().add(eActions.MBS);
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