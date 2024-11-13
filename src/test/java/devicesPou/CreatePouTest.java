package devicesPou;

import devicesDB.IOLdatabase;
import devicesDB.MotorDatabase;
import enums.FilePath;
import enums.eDevType;
import enums.eProtocol;
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
        CreatePou createPou = new CreatePou(new File(FilePath.DEFAULT_SOURCE), eProtocol.CODESYS);
        StringBuilder data = new StringBuilder();
        StringBuilder result = new StringBuilder();
        result.append(createPou.createOne(eDevType.MOTOR));
        data.append(createPou.createData(eDevType.MOTOR));
        System.out.println(result);
        System.out.println(data);
    }

    @Test
    void testXMLcompose() throws IOException {
        Set<eDevType> selectedDevices = new HashSet<>();
        selectedDevices.add(eDevType.MOTOR);
        XmlCompose xmlCompose = new XmlCompose(
                new File(FilePath.DEFAULT_SOURCE),
                FilePath.DEFAULT_TARGET_FOLDER,
                "test",
                eProtocol.CODESYS,
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