package devicesPou;

import enums.FilePath;
import enums.eDevType;
import enums.eRegex;
import enums.eProtocol;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class XmlCompose {
    private String projectName = "SVT_Babhinskii_PLC_v0.0.0.1.project";
    private String timeStamp;
    private String targetFolder = "";
    private eProtocol protocol = eProtocol.EMPTY;
    private File selectedFile = new File("");
    private Set<eDevType> selectedDevices = new HashSet<>();


    public XmlCompose(File selectedFile, String targetFolder, String projectName, eProtocol protocol, Set<eDevType> selectedDevices) throws IOException {
        this.selectedFile = selectedFile;
        this.projectName = projectName + ".project";
        this.timeStamp = getTimeStamp();
        this.targetFolder = targetFolder;
        this.protocol = protocol;
        this.selectedDevices = selectedDevices;

        // Создание StringBuilder для объединения всех фрагментов текста
        StringBuilder finalXML = composeXML();

        //Сохранение в файл
        saveFinalXMLToFile(finalXML);

    }

    private StringBuilder composeXML () throws IOException {
        // Создание StringBuilder для объединения всех фрагментов текста
        StringBuilder finalXML = new StringBuilder();

        // Контекст с переменными
        VelocityContext context = new VelocityContext();
        context.put("timeStamp",    timeStamp);
        context.put("projectName",  projectName);
        finalXML.append(generateText(context, FilePath.FILEPATH_XML_OPEN));

        //Создание POU
        CreatePou createPou = new CreatePou(selectedFile, protocol);
        StringBuilder allPou = processDevices(selectedDevices,createPou);
        finalXML.append(allPou);
        finalXML.append("\n");

        //Создание IOL
        CreateVarList createVarList = new CreateVarList();
        StringBuilder varList = new StringBuilder();
        finalXML.append(createVarList.createOne());
        finalXML.append("\n");

        //Создание раздела Data
        for (eDevType devType : selectedDevices) {
            finalXML.append(createPou.createData(devType));
        }
        finalXML.append(createVarList.createData());

        //Закрывающий фрагмент
        finalXML.append("        </ProjectStructure>\n" +
                "    </data>\n" +
                "  </addData>\n" +
                "</project>");

        return finalXML;
    }

    private void saveFinalXMLToFile(StringBuilder finalXML)  {
        try (FileWriter fileWriter = new FileWriter(genFileName())) {
            fileWriter.write(finalXML.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для генерации текста на основе контекста и шаблона
    private static String generateText(VelocityContext context, String templatePath) {
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(templatePath, "UTF-8", context, writer);
        return writer.toString(); // Возвращаем сгенерированный текст для данного контекста
    }

    private String genFileName () {
        return this.targetFolder + "\\" + projectName + "_devices.xml";
    }

    private String getTimeStamp () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(eRegex.timeStampCodesys.getValue());
        return now.format(formatter);
    }

    public StringBuilder processDevices(Set<eDevType> selectedDevices, CreatePou createPou) throws IOException {
        StringBuilder allPou = new StringBuilder();
        for (eDevType devType : selectedDevices) {
            allPou.append(createPou.createOne(devType));
        }
        return allPou;
    }
}
