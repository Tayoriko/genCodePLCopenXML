package devicesPou;

import devicesDB.DeviceCreator;
import devicesDB.MotorDatabase;
import enums.FilePath;
import enums.eDevType;
import enums.eProtocol;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import static enums.eDevType.MOTOR;

public class CreatePou {
    private String device = "";
    private String pouName = "";
    private String drvName = "";
    private String cntName = "";
    private String folder = "Local PRG";
    private eProtocol protocol = eProtocol.EMPTY;
    private File selectedFile = new File("");

    public CreatePou(File selectedFile, eProtocol protocol) throws IOException {
        this.selectedFile = selectedFile;
        this.protocol = protocol;
    }

    public StringBuilder createOne(eDevType devType) throws IOException {
        //Загрузка данных
        StringBuilder data = getDeviceFromDB(devType);
        StringBuilder pou = new StringBuilder();
        loadTypeData(devType);

        // Настройка Velocity
        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);

        VelocityContext context = new VelocityContext();
        context.put("pouName",      pouName);
        context.put("drvName",      drvName);
        context.put("device",       device);
        context.put("cntName",      cntName);
        context.put("loader",       devType.getLoader());
        context.put("device",       devType.getValue());
        context.put("data",         data);

        pou.append(generateText(context, FilePath.FILEPATH_XML_POU));
        return pou;
    }

    public StringBuilder createData(eDevType devType) {
        //Загрузка данных
        StringBuilder data = new StringBuilder();
        // Настройка Velocity
        Properties properties = new Properties();
        properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);
        VelocityContext context = new VelocityContext();
        context.put("folder",      folder);
        context.put("name",        devType.getPou());
        data.append(generateText(context, FilePath.FILEPATH_XML_DATA));
        return data;
    }

    private StringBuilder getDeviceFromDB(eDevType devType) throws IOException {
        StringBuilder device = new StringBuilder();
        switch (devType){
            case MOTOR ->{
                MotorDatabase.getInstance().clear();
                DeviceCreator deviceCreator = new DeviceCreator(selectedFile, protocol);
                deviceCreator.reviewDevice(protocol, MOTOR);
                device.append(MotorDatabase.getInstance().getAllRecords());
            }
        }
        return device;
    }

    private void loadTypeData (eDevType devType) {
        device = devType.getName();
        pouName = devType.getPou();
        drvName = devType.getDrv();
        cntName = devType.getCnt();
    }

    // Метод для генерации текста на основе контекста и шаблона
    private static String generateText(VelocityContext context, String templatePath) {
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(templatePath, "UTF-8", context, writer);
        return writer.toString(); // Возвращаем сгенерированный текст для данного контекста
    }
}
