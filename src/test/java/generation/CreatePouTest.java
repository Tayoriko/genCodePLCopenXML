package generation;

import enums.FilePath;
import enums.eDevices;
import enums.ePLC;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

class CreatePouTest {

    @Test
    void createOne() throws IOException {
        CreatePou createPou = new CreatePou(new File(FilePath.DEFAULT_SOURCE), ePLC.CODESYS);
        StringBuilder data = new StringBuilder();
        StringBuilder result = new StringBuilder();
        result.append(createPou.createOne(eDevices.MOTOR));
        data.append(createPou.createData(eDevices.MOTOR));
        System.out.println(result);
        System.out.println(data);
    }

    @Test
    void testXMLcompose() throws IOException {
        Set<eDevices> selectedDevices = new HashSet<>();
        selectedDevices.add(eDevices.MOTOR);
        XmlCompose xmlCompose = new XmlCompose(
                new File(FilePath.DEFAULT_SOURCE),
                FilePath.DEFAULT_TARGET_FOLDER,
                "test",
                ePLC.CODESYS,
                "SP 20",
                selectedDevices);
        //MotorDatabase.getInstance().printAllRecords();
        //IOLdatabase.getInstance().printAllRecords();
        CreateVarList createVarList = new CreateVarList();
        StringBuilder varList = new StringBuilder();
        varList.append(createVarList.createOne());
        varList.append("\n");
        varList.append(createVarList.createData());
        System.out.println(varList);
    }

    @Test
    void testGenerateText () {
        String projectName = "SVT_Babhinskii_PLC_v0.0.0.1.project";
        String timeStamp = "timeStamp";
        VelocityContext context = new VelocityContext();
        context.put("timeStamp",    timeStamp);
        context.put("projectName",  projectName);
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate("./src/main/resources/xml/XMLopen.vm", "UTF-8", context, writer);
        System.out.println(writer.toString()); // Возвращаем сгенерированный текст для данного контекста
    }
}