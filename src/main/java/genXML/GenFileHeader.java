package genXML;

import org.apache.velocity.VelocityContext;
import system.GDB;
import system.GenXML;

/**
 * Class for generate XML header with format:
 * <fileHeader companyName="" productName="CODESYS" productVersion="CODESYS V3.5 SP20"
 * creationDateTime="2024-04-20T13:01:00.2129876" />
 * contains:
 * - company name
 * - product name (constant)
 * - product version
 * - creation date and time
 *
 */

public class GenFileHeader extends GenXML {
    private static String companyName = GDB.companyName;
    private static String productName = GDB.productName;
    private static String productVersion = GDB.productVersion;

    public GenFileHeader() {}

    public String genFileHeader(int tabs)
    {
        // Создаем контекст Velocity
        VelocityContext context = new VelocityContext();

        // Заполняем контекст данными
        context.put("companyName", companyName);
        context.put("productName", productName);
        context.put("productVersion", productVersion);
        context.put("timeStamp", setTimeStamp());
        // Выводим результат
        return getXML(context, GDB.templateFileHeader, tabs);
    }


}
