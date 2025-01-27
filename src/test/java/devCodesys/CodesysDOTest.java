package devCodesys;

import devPLC.CodesysCallDevices;
import databases.GData;
import dev.DevDO;
import enums.eActions;
import enums.ePLC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodesysDOTest {
    int id = 1;
    String name = "Control KC 1";
    String devName = "KC_1";
    String comment = "Clean air for refrigerator";
    String signal = "A5.DO2";
    String message = "Codesys - DevDO";

    @BeforeAll
    static void init(){
        GData.getInstance().setPlc(ePLC.CODESYS);
    }

    @Test
    void createCodesysDevDo() {
        DevDO devDO = new DevDO(id, name, devName, comment, signal);
        GData.getInstance().getActions().remove(eActions.MBS);
        StringBuilder actualResult  = CodesysCallDevices.callDo(devDO);
        String expectedResult = String.format(
                "// #001 - Control KC 1\n" +
                "drvDO[1](\n" +
                "   command     := IOL.KC_1,\n" +
                "   cmd         := CVL.cmdDO[1],\n" +
                "   cfg         := RVL.cfgDO[1],\n" +
                "   result      => SIG.listDO[5].2);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevDoNetData() {
        DevDO devDO = new DevDO(id, name, devName, comment, signal);
        GData.getInstance().getActions().add(eActions.MBS);
        StringBuilder actualResult  = CodesysCallDevices.callDo(devDO);
        String expectedResult = String.format(
                "// #001 - Control KC 1\n" +
                "drvDO[1](\n" +
                "   command     := IOL.KC_1,\n" +
                "   cmd         := CVL.cmdDO[1],\n" +
                "   cfg         := RVL.cfgDO[1],\n" +
                "   netData     := NVL.KC_1,\n" +
                "   result      => SIG.listDO[5].2);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

}