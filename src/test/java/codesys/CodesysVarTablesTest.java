package codesys;

import dev.DevAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodesysVarTablesTest {
    int id = 1;
    String name = "Setpoint for UZ 1";
    String devName = "sp_UZ_1";
    String comment = "Conveyor for spread";
    String result = "A3.AO2";

    @Test
    void genId() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        CodesysCallDevices.setUseNetData(false);
        CodesysGenVarTags codesysVarTables = new CodesysGenVarTags();
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
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void genIol() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        CodesysCallDevices.setUseNetData(false);
        CodesysGenVarTags codesysVarTables = new CodesysGenVarTags();
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
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }

    @Test
    void genNet() {
        DevAO devAO = new DevAO(id, name, devName, comment, result);
        CodesysCallDevices.setUseNetData(false);
        CodesysGenVarTags codesysVarTables = new CodesysGenVarTags();
        StringBuilder actualResult = codesysVarTables.genNet(devAO);
        String expectedResult = String.format(
                "<variable name=\"sp_UZ_1\">\n" +
                "  <type>\n" +
                "    <derived name=\"mt.udtNetDataAnalog\" />\n" +
                "  </type>\n" +
                "</variable>\n");
        Assertions.assertEquals(actualResult.toString(), expectedResult.toString());
    }
}