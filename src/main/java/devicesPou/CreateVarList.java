package devicesPou;

import devices.IOLrecord;
import devicesDB.IOLdatabase;
import enums.FilePath;
import enums.eDevType;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Properties;

public class CreateVarList {

    private String folder = "Global Variables";
    private String name = "IOL";
    private StringBuilder vars = new StringBuilder();

    public CreateVarList() {
        IOLdatabase database = IOLdatabase.getInstance();

        for (IOLrecord record : database.getAllRecords()) {
            vars.append(record.toXML());
        }
    }

    public StringBuilder createOne () {
        // Настройка Velocity
        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);

        VelocityContext context = new VelocityContext();
        context.put("name",         "IOL");
        context.put("vars",         vars);

        StringBuilder varList = new StringBuilder();
        varList.append(generateText(context, FilePath.FILEPATH_XML_VARS));
        return varList;
    }

    public StringBuilder createData() {
        //Загрузка данных
        StringBuilder data = new StringBuilder();
        // Настройка Velocity
        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);
        VelocityContext context = new VelocityContext();
        context.put("folder",      folder);
        context.put("name",        name);
        data.append(generateText(context, FilePath.FILEPATH_XML_DATA));
        return data;
    }

    // Метод для генерации текста на основе контекста и шаблона
    private static String generateText(VelocityContext context, String templatePath) {
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(templatePath, "UTF-8", context, writer);
        return writer.toString(); // Возвращаем сгенерированный текст для данного контекста
    }

}
