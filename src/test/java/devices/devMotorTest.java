package devices;

import devicesDB.DeviceCreator;
import devicesDB.MotorDatabase;
import enums.FilePath;
import enums.eProtocol;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static enums.eDevType.MOTOR;

class devMotorTest {

    int id = 4;
    AddrPLC qf = new AddrPLC("1", "0");
    AddrPLC km = new AddrPLC("1", "1");
    String name = "Насос Нот 1.2";
    String devName = "Not_1_2";
    AddrPLC cmdFw = new AddrPLC("0", "0");

    @Test
    void testDevMotorDefault() {
        DevMotor motor = new DevMotor(id, name, devName, qf, km, cmdFw);
        System.out.println(motor.toString());
        System.out.println(motor.toString());
    }

    @Test
    void testDevMotorFromOne() {
        DevOne devOne = new DevOne(name, devName, qf, km, cmdFw);
        DevMotor motor = new DevMotor(id, devOne);
        System.out.println(motor.toString());
    }

    @Test
    void testAddMotor() {
        DevOne devOne = new DevOne(name, devName, qf, km, cmdFw);
        DevMotor motor = new DevMotor(id, devOne);
        MotorDatabase.getInstance().addRecord(motor);
        MotorDatabase.getInstance().printAllRecords();
    }

    @Test
    void testAddFromXLS() throws IOException {
        eProtocol protocol = eProtocol.CODESYS;
        MotorDatabase.getInstance().clear();
        DeviceCreator deviceCreator = new DeviceCreator(new File(FilePath.DEFAULT_SOURCE), protocol);
        deviceCreator.reviewDevice(protocol, MOTOR);
        MotorDatabase.getInstance().printAllRecords();
    }

}
