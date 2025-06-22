package devCodesys;

import devPLC.CodesysCallDevices;
import databases.GData;
import dev.DevMotor;
import enums.eActions;
import enums.ePLC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodesysMotorTest {
    int id = 10;
    String name = "Pump UZ 10";
    String devName = "UZ_10";
    String comment = "Pump for control level in tank 4";
    String qf = "A1.DI2";
    String km = "A1.DI3";
    String cmdFw = "A4.DO11";
    String message = "Codesys - DevMotor";

    @BeforeAll
    static void init(){
        GData.getInstance().setPlc(ePLC.CODESYS);
    }

    @Test
    void createCodesysDevMotor() {
        DevMotor devMotor = new DevMotor(id, name, devName, comment, qf, km ,cmdFw);
        GData.getInstance().getActions().remove(eActions.MBS);
        StringBuilder actualResult  = CodesysCallDevices.callMotor(devMotor);
        String expectedResult = String.format(
                "// #010 - Pump UZ 10\n" +
                "drvM[10](\n" +
                "   devState    := IOL.UZ_10,\n" +
                "   cmd         := CVL.cmdM[10],\n" +
                "   cfg         := RVL.cfgM[10],\n" +
                "   state       => SVL.stateM[10],\n" +
                "   fbQF        := SIG.listDI[1].2,\n" +
                "   fbKM        := SIG.listDI[1].3,\n" +
                "   cmdFW       => SIG.listDO[4].11);\n\n");
        Assertions.assertEquals(expectedResult.toString(),actualResult.toString());
    }

    @Test
    void createCodesysDevMotorNetData() {
        DevMotor devMotor = new DevMotor(id, name, devName, comment, qf, km ,cmdFw);
        GData.getInstance().getActions().add(eActions.MBS);
        StringBuilder actualResult  = CodesysCallDevices.callMotor(devMotor);
        String expectedResult = String.format(
                "// #010 - Pump UZ 10\n" +
                "drvM[10](\n" +
                "   devState    := IOL.UZ_10,\n" +
                "   cmd         := CVL.cmdM[10],\n" +
                "   cfg         := RVL.cfgM[10],\n" +
                "   state       => SVL.stateM[10],\n" +
                "   netData     => NVL.UZ_10,\n" +
                "   fbQF        := SIG.listDI[1].2,\n" +
                "   fbKM        := SIG.listDI[1].3,\n" +
                "   cmdFW       => SIG.listDO[4].11);\n\n");
        Assertions.assertEquals(expectedResult.toString(),actualResult.toString());
    }
}