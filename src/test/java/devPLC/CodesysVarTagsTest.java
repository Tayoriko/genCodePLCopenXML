package devPLC;

import databases.GData;
import dev.DevAO;
import enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodesysVarTagsTest {
    int id = 1;
    String name = "Setpoint for UZ 1";
    String devName = "sp_UZ_1";
    String comment = "Conveyor for spread";
    String result = "A3.AO2";
    CodesysGenVarTags codesysVarTables = new CodesysGenVarTags();

    @BeforeAll
    static void init(){
        GData.setPlc(ePLC.CODESYS);
        GData.setTemplate(eTemplate.BASIC);
    }

    @Test
    void genId() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        GData.getActions().remove(eActions.MBS);
        StringBuilder actualResult = codesysVarTables.genId(devAO);
        String expectedResult = String.format(
                "<variable name=\"ID_sp_UZ_1\">\n" +
                "  <type>\n" +
                "    <INT />\n" +
                "  </type>\n" +
                "  <initialValue>\n" +
                "    <simpleValue value=\"1\" />\n" +
                "  </initialValue>\n" +
                "</variable>\n");
        Assertions.assertEquals(expectedResult, actualResult.toString());
    }

    @Test
    void genIol() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        GData.getActions().remove(eActions.MBS);
        StringBuilder actualResult = codesysVarTables.genIol(devAO);
        String expectedResult = String.format(
                "<variable name=\"sp_UZ_1\">\n" +
                "  <type>\n" +
                "    <derived name=\"mt.udtDevAnalog\" />\n" +
                "  </type>\n" +
                "  <documentation>\n" +
                "    <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">" + "#001 - Setpoint for UZ 1 - Conveyor for spread" + "</xhtml>\n" +
                "  </documentation>\n" +
                "</variable>\n");
        Assertions.assertEquals(expectedResult, actualResult.toString());
    }

    @Test
    void genNet() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        GData.getActions().remove(eActions.MBS);
        StringBuilder actualResult = codesysVarTables.genNvl(devAO);
        String expectedResult = String.format(
                "<variable name=\"sp_UZ_1\">\n" +
                "  <type>\n" +
                "    <derived name=\"mt.udtNetDataAnalog\" />\n" +
                "  </type>\n" +
                "</variable>\n");
        Assertions.assertEquals(expectedResult, actualResult.toString());
    }

    @Test
    void genVar() {
        String varName = "id";
        String varType = eVarType.INT.getTypeName();
        String comment = "ID for copies";
        StringBuilder actualResult = codesysVarTables.genVar(varName, varType, comment);
        String expectedResult = String.format(
                "<variable name=\"id\">\n" +
                "  <type>\n" +
                "    <INT />\n" +
                "  </type>\n" +
                "  <documentation>\n" +
                "    <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">ID for copies</xhtml>\n" +
                "  </documentation>\n" +
                "</variable>\n"
        );
        Assertions.assertEquals(expectedResult, actualResult.toString());
    }

    @Test
    void genArray() {
        String varName = "PT";
        String varType = eVarType.PT.getTypeName();
        String cnt = "4";
        String comment = "Positive front trigger";
        StringBuilder actualResult = codesysVarTables.genArray(varName, varType, cnt, comment);
        String expectedResult = String.format(
                "<variable name=\"PT\">\n" +
                "  <type>\n" +
                "    <array>\n" +
                "      <dimension lower=\"0\" upper=\"4\" />\n" +
                "      <baseType>\n" +
                "        <derived name=\"R_TRIG\" />\n" +
                "      </baseType>\n" +
                "    </array>\n" +
                "  </type>\n" +
                "  <documentation>\n" +
                "    <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">Positive front trigger</xhtml>\n" +
                "  </documentation>\n" +
                "</variable>\n"
        );

        Assertions.assertEquals(expectedResult, actualResult.toString());
    }

    @Test
    void genArrayUdt() {
        String varName = "DrvDI";
        String varType = "DrvDI";
        String cnt = "IDL.limitDI";
        StringBuilder actualResult = codesysVarTables.genArrayUdt(varName, varType,cnt);
        String expectedResult = String.format(
                "<variable name=\"DrvDI\">\n" +
                "  <type>\n" +
                "    <array>\n" +
                "      <dimension lower=\"0\" upper=\"IDL.limitDI\" />\n" +
                "      <baseType>\n" +
                "        <derived name=\"mt.DrvDI\" />\n" +
                "      </baseType>\n" +
                "    </array>\n" +
                "  </type>\n" +
                "  <documentation>\n" +
                "    <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">Driver for standard DrvDI</xhtml>\n" +
                "  </documentation>\n" +
                "</variable>\n"
        );
        Assertions.assertEquals(expectedResult, actualResult.toString());
    }
}