package devHMI;

import databases.DatabaseRegistry;
import databases.GData;
import databases.GenericDatabase;
import dev.*;
import enums.eDevType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlarmGeneration {
    private static String varList;
    private static String varName;
    private static String varPath;

    public AlarmGeneration () {
        DatabaseRegistry.getInstance(AlarmMessage.class).clear();

        Set<eDevType> deviceTypes = GData.getDevices(); // Set с выбранными типами устройств

        deviceTypes.forEach(devType -> {
            // Retrieve the corresponding database for the current device type
            GenericDatabase<?> deviceDatabase = null;
            List<String[]> alarmList = new ArrayList<>();
            switch (devType) {
                case DI -> deviceDatabase = DatabaseRegistry.getInstance(DevAI.class);
                case AI -> deviceDatabase = DatabaseRegistry.getInstance(DevAI.class);
                case AO -> deviceDatabase = DatabaseRegistry.getInstance(DevAO.class);
                case MOTOR -> deviceDatabase = DatabaseRegistry.getInstance(DevMotor.class);
                case VALVE -> deviceDatabase = DatabaseRegistry.getInstance(DevValve.class);
            }

            // If the database is found, proceed to process its records
            if (deviceDatabase != null) {
                if (!devType.equals(eDevType.DI))
                alarmList = loadAlarmsFromCsv(getAlarmFilePath(devType.getValue()));
                for (Object record : deviceDatabase.getRecords()) {
                    // Perform operations on each record
                    generateAlarms((AbstractDevice) record, alarmList);
                }
            } else {
                for (Object record : deviceDatabase.getRecords()) {
                    generateAlarmDI((DevDI) record);
                }
            }
        });
    }

    private void generateAlarmDI(DevDI device){
        if (device.isAlarm()){
            // Создание объекта AlarmMessage
            AlarmMessage newAlarm = new AlarmMessage(
                    generateAddressDI(device), // Генерация адреса аварии
                    generateContentDI(device) // Генерация содержимого аварии
            );
            // Добавляем в базу данных
            DatabaseRegistry.getInstance(AlarmMessage.class).addRecord(newAlarm);
        }
    }

    private void generateAlarms(AbstractDevice device, List<String[]> alarmList) {
        alarmList.forEach(alarm -> {
            String alarmName = alarm[0];
            String alarmMessage = alarm[1];

            // Создание объекта AlarmMessage
            AlarmMessage newAlarm = new AlarmMessage(
                    generateAddress(device, alarmName), // Генерация адреса аварии
                    generateContent(device, alarmMessage) // Генерация содержимого аварии
            );

            // Добавляем в базу данных
            DatabaseRegistry.getInstance(AlarmMessage.class).addRecord(newAlarm);
        });
    }

    private String generateAddress(AbstractDevice device, String alarmName) {
        // Пример генерации адреса аварии
        String address = varList + "." + varName + device.getDevType().getName() + "[" + device.getId() + "]";
        address += "." + varPath + "." + alarmName;
        return address;
    }

    private String generateAddressDI(AbstractDevice device) {
        // Пример генерации адреса аварии
        return "IOL." + device.getDevName();
    }

    private String generateContent(AbstractDevice device, String alarmMessage) {
        // Пример генерации текста аварии
        return String.format("%s - %s", device.getName(), alarmMessage);
    }

    private String generateContentDI(AbstractDevice device) {
        return device.getName();
    }


    private List<String[]> loadAlarmsFromCsv(String filePath) {
        List<String[]> alarms = new ArrayList<>();
        System.out.println(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    parts[1] = parts[1].replace("\"", "");
                    alarms.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alarms;
    }



    private String getAlarmFilePath (String fileName) {
        String filePath = GData.RESOURCES;
        switch (GData.getPlc()){
            case CODESYS -> {
                varList = "SVL";
                varName = "state";
                varPath = "alarm";
            }
        }
        switch (GData.getLang()){
            case RU -> filePath += "ru/";
            case EN -> filePath += "en/";
        }
        switch (GData.getTemplate()){
            case BASIC -> filePath += "basic/";
            case CUSTOM_MV210 -> filePath += "customMV210/";
        }
        filePath += fileName + ".csv";
        return filePath;
    }
}
