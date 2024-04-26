package genXML;

import CSV.CsvReadVars;
import org.apache.velocity.VelocityContext;
import system.GDB;
import system.GenXML;
import uniqueItems.OneVar;

import java.util.ArrayList;
import java.util.List;

public class GenAddData extends GenXML {

    private List<OneVar> varList = new ArrayList<>();

    private List<OneVar> readFromFile (String filename) {
        CsvReadVars csvReadVars = new CsvReadVars();
        return csvReadVars.readFile(GDB.filepathSource, filename);
    }

    public GenAddData () {}

    public String getAddData (int tabs) {
        varList = readFromFile("varsGlobal");
        StringBuilder sb = new StringBuilder();
        sb.append(genOpen(tabs) + "\n");
        sb.append("<globalVars name=\"GVL\">").append("\n");
        for (OneVar oneVar : varList) {
            sb.append(oneVar.toXML()).append("\n");
        }
        sb.append("</globalVars>").append("\n");
        sb.append(genClose(tabs)).append("\n");
        return sb.toString();
    }

    private String genOpen (int tabs) {
        // Создаем контекст Velocity
        VelocityContext context = new VelocityContext();

        // Выводим результат
        return getXML(context, "genAddDataOpen", tabs);
    }

    private String genClose (int tabs) {
        // Создаем контекст Velocity
        VelocityContext context = new VelocityContext();

        // Выводим результат
        return getXML(context, "genAddDataClose", tabs);
    }
}
