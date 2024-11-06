package alarmsGen;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class AlarmApp extends Application {

    private File inputFile;
    private File outputFile;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Alarm Export Tool");

        // Кнопка для выбора входного файла Excel
        Button selectInputFileButton = new Button("Select Input Excel File");
        selectInputFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Input Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            inputFile = fileChooser.showOpenDialog(primaryStage);
            if (inputFile != null) {
                System.out.println("Selected input file: " + inputFile.getAbsolutePath());
            }
        });

        // Кнопка для выбора места сохранения выходного файла
        Button selectOutputFileButton = new Button("Select Output Location and Name");
        selectOutputFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Output Location and Name");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            outputFile = fileChooser.showSaveDialog(primaryStage);
            if (outputFile != null) {
                System.out.println("Selected output file: " + outputFile.getAbsolutePath());
            }
        });

        // Кнопка для запуска экспорта
        Button exportButton = new Button("Export Alarms");
        exportButton.setOnAction(e -> {
            if (inputFile != null && outputFile != null) {
                try {
                    executeExport(inputFile, outputFile);
                    System.out.println("Export completed successfully!");
                } catch (IOException ex) {
                    System.err.println("Error during export: " + ex.getMessage());
                }
            } else {
                System.out.println("Please select both input and output files.");
            }
        });

        VBox vbox = new VBox(10, selectInputFileButton, selectOutputFileButton, exportButton);
        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void executeExport(File inputFile, File outputFile) throws IOException {
        // Загружаем данные для каждого типа устройств из CSV-файлов (их пути могут быть фиксированы или динамичны)
        String filePathMotor = "./src/main/resources/sources/motor.csv";
        String filePathValve = "./src/main/resources/sources/valve.csv";
        String filePathAI = "./src/main/resources/sources/analog_input.csv";
        String filePathDevices = "./src/main/resources/sources/devices.xlsx";

        // Создаем базу данных для хранения всех аварий
        AlarmDatabase alarmDatabase = new AlarmDatabase();

        // Заполняем базу данных
        AlarmDataAggregator.aggregateAlarmsToDatabase(
                inputFile.getAbsolutePath(),
                filePathMotor,
                filePathValve,
                filePathAI,
                alarmDatabase
        );

        // Экспортируем базу данных в Excel
        AlarmDatabaseExporter.exportToExcel(alarmDatabase, outputFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
