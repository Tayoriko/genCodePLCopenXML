package system;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class GenXML {
    //generate XML with template and context
    protected String getXML(VelocityContext context, String templateXML, int tabs) {
        context.put("tab", GDB.tab.repeat(Math.max(0, tabs)));
        VelocityEngine velocityEngine = VelocityEngineSingleton.getInstance();
        Template template;
        try {
            template = velocityEngine.getTemplate(GDB.filepathTemplate +
                    templateXML +
                    GDB.fileFormatTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        StringWriter writer = new StringWriter();
        try {
            template.merge(context, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    //generate time stamp
    protected String setTimeStamp () {
        // Получаем текущую дату и время
        LocalDateTime dateTime = LocalDateTime.now();

        // Создаем объект DateTimeFormatter для форматирования
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

        // Форматируем дату и время в нужный формат
        return dateTime.format(formatter);
    }
}
