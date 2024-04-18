package CSV;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import system.RegexBase;
import uniqueItems.OneVar;

import java.util.Arrays;
import java.util.List;

class CsvReadVarsTest {

    CsvReadVars csvReadVars = new CsvReadVars();

    @Test
    void readLine() {
        String source = "pulseS1,BOOL,global trigger 1 second";
        String[] result = csvReadVars.convertLineToArray(source);
        Assertions.assertEquals(
                "[pulseS1, BOOL, global trigger 1 second]",
                Arrays.stream(result).toList().toString());
    }

    @Test
    void checkData() {
        String source = "pulseS1,BOOL,global trigger 1 second";
        String[] result = csvReadVars.convertLineToArray(source);
        Assertions.assertTrue(csvReadVars.checkData(result));
    }

    @Test
    void createVar() {
        String source = "pulseS1,BOOL,global trigger 1 second";
        OneVar var = csvReadVars.createVar(source);
        Assertions.assertEquals("pulseS1", var.getName());
    }

    @Test
    void readFile() {
        String filename = "varsGlobal";
        List<OneVar> varList = csvReadVars.readFile(RegexBase.filepathSource, filename);
        varList.forEach(item -> System.out.println(item.toString()));
        Assertions.assertEquals(12, varList.size());
    }
}