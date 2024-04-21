package genXML;

import org.apache.velocity.VelocityContext;
import system.GDB;
import system.GenXML;

/**
 * Class for generate XML content header with format:
 *   <contentHeader name="wtf123.project" modificationDateTime="2024-04-20T13:00:40.94236856">
 *     <coordinateInfo>
 *       <fbd>
 *         <scaling x="1" y="1" />
 *       </fbd>
 *       <ld>
 *         <scaling x="1" y="1" />
 *       </ld>
 *       <sfc>
 *         <scaling x="1" y="1" />
 *       </sfc>
 *     </coordinateInfo>
 *     <addData>
 *       <data name="http://www.3s-software.com/plcopenxml/projectinformation" handleUnknown="implementation">
 *         <ProjectInformation />
 *       </data>
 *     </addData>
 *   </contentHeader>
 */
public class GenContentHeader extends GenXML {
    private static String projectName = GDB.projectName;

    public GenContentHeader() {}

    public String genContentHeader (int tabs) {
        // Создаем контекст Velocity
        VelocityContext context = new VelocityContext();

        // Заполняем контекст данными
        context.put("name", projectName);
        context.put("timeStamp", setTimeStamp());
        // Выводим результат
        return getXML(context, GDB.templateContentHeader, tabs);
    }
}
