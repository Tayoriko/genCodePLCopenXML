package devPLC;

import databases.DatabaseRegistry;
import databases.GData;
import dev.DevDI;
import enums.*;
import generation.DeviceCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class CodesysGenTest {

    CodesysGenPous codesysGenPous = new CodesysGenPous();
    CodesysGenVarLists codesysGenVarLists = new CodesysGenVarLists();
    CodesysGenStructure codesysGenStructure = new CodesysGenStructure();
    CodesysGen codesysGen = new CodesysGen();
    Set<eDevType> devices = new HashSet<>();
    Set<eActions> actions = new HashSet<>();

    void initData() {
        GData.setHmi(eHMI.WEINTEK);
        GData.setPlc(ePLC.CODESYS);
        GData.setTemplate(eTemplate.BASIC);
        GData.setVersion("SP20");
        //sourceFile = FxOptions.getSourceFile();
        //targetFolder = FxOptions.getTargetFolder();
        devices.add(eDevType.DI);
        GData.setDevices(devices);
        GData.setActions(actions);
    }

    @Test
    void header() {
        initData();
        System.out.println(codesysGen.genHeader());
    }

    @Test
    void projectStructure() {
        initData();
        System.out.println(codesysGenStructure.genProjectStructure());
    }

    @Test
    void createXml() throws IOException {
        initData();
        actions.add(eActions.PUO);
        actions.add(eActions.IOL);
        actions.add(eActions.MBS);
        DeviceCreator deviceCreator = new DeviceCreator(new File(GData.DEFAULT_SOURCE), GData.getDevices());
        System.out.println(codesysGen.createXml());
    }

    @Test
    void deviceCreator() throws IOException {
        initData();
        DeviceCreator deviceCreator = new DeviceCreator(new File(GData.DEFAULT_SOURCE), GData.getDevices());
        DevDI devDI = DatabaseRegistry.getInstance(DevDI.class).getRecords().get(1);
        String expectedResult = "AbstractDevice{id=2, name='Start process', devName='keyStart', comment='Key - start auto mode', header='// #002 - Start process', cmd='CVL.cmdDI[2]', cfg='RVL.cfgDI[2]', state='SVL.stateDI[2]', devType=DI}";
        String actualResult = devDI.toString();
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void createPou() throws IOException {
        initData();
        actions.add(eActions.PUO);
        DeviceCreator deviceCreator = new DeviceCreator(new File(GData.DEFAULT_SOURCE), GData.getDevices());
        System.out.println(codesysGenPous.genPous());
    }

    @Test
    void createIol() throws IOException {
        initData();
        actions.add(eActions.IOL);
        DeviceCreator deviceCreator = new DeviceCreator(new File(GData.DEFAULT_SOURCE), GData.getDevices());
        System.out.println(codesysGenVarLists.genVarLists());
    }

    @Test
    void createNvl() throws IOException {
        initData();
        actions.add(eActions.MBS);
        DeviceCreator deviceCreator = new DeviceCreator(new File(GData.DEFAULT_SOURCE), GData.getDevices());
        System.out.println(codesysGenVarLists.genVarLists());
    }

    @Test
    void createIolNvl() throws IOException {
        initData();
        actions.add(eActions.MBS);
        actions.add(eActions.IOL);
        DeviceCreator deviceCreator = new DeviceCreator(new File(GData.DEFAULT_SOURCE), GData.getDevices());
        System.out.println(codesysGenVarLists.genVarLists());
    }


}