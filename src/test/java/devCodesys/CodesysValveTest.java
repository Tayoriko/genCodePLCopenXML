package devCodesys;

import devPLC.CodesysCallDevices;
import databases.GData;
import dev.DevValve;
import enums.eActions;
import enums.ePLC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodesysValveTest {
    int id = 34;
    String name = "Valve KE 34";
    String devName = "KE_34";
    String comment = "Valve for filling tank 6";
    String qf = "";
    String fbOpen = "A1.DI3";
    String fbClose = "A1.DI4";
    String cmdOpen = "A4.DO11";
    String cmdClose = "A4.DO12";
    String message = "Codesys - DevValve";

    @BeforeAll
    static void init(){
        GData.getInstance().setPlc(ePLC.CODESYS);
    }

    @Test
    void createCodesysDevValve() {
        DevValve devValve = new DevValve(id, name, devName, comment, qf, fbOpen, fbClose, cmdOpen, cmdClose);
        GData.getInstance().getActions().remove(eActions.MBS);
        StringBuilder actualResult  = CodesysCallDevices.callValve(devValve);
        String expectedResult = String.format(
                "// #034 - Valve KE 34\n" +
                "drvV[34](\n" +
                "   devState    := IOL.KE_34,\n" +
                "   cmd         := CVL.cmdV[34],\n" +
                "   cfg         := RVL.cfgV[34],\n" +
                "   state       := SVL.stateV[34],\n" +
                "   fbQF        := (*input address here*),\n" +
                "   fbOpen      := SIG.listDI[1].3,\n" +
                "   fbClose     := SIG.listDI[1].4,\n" +
                "   cmdOpen     => SIG.listDO[4].11,\n" +
                "   cmdClose    => SIG.listDO[4].12);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void createCodesysDevValveNetData() {
        DevValve devValve = new DevValve(id, name, devName, comment, qf, fbOpen, fbClose, cmdOpen, cmdClose);
        GData.getInstance().getActions().add(eActions.MBS);
        StringBuilder actualResult  = CodesysCallDevices.callValve(devValve);
        String expectedResult = String.format(
                "// #034 - Valve KE 34\n" +
                "drvV[34](\n" +
                "   devState    := IOL.KE_34,\n" +
                "   cmd         := CVL.cmdV[34],\n" +
                "   cfg         := RVL.cfgV[34],\n" +
                "   state       := SVL.stateV[34],\n" +
                "   netData     := NVL.KE_34,\n" +
                "   fbQF        := (*input address here*),\n" +
                "   fbOpen      := SIG.listDI[1].3,\n" +
                "   fbClose     := SIG.listDI[1].4,\n" +
                "   cmdOpen     => SIG.listDO[4].11,\n" +
                "   cmdClose    => SIG.listDO[4].12);\n\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }
}