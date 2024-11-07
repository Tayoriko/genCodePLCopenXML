package alarmsGen;

import enums.eHMI;
import enums.eProtocol;
import enums.eTemplate;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AlarmApp extends Application {

    private Label statusLabel;
    private File selectedFile;
    private String saveToFile;
    private static ToggleGroup hmiGroup = new ToggleGroup();
    private static ToggleGroup protocolGroup = new ToggleGroup();
    private static ToggleGroup templateGroup = new ToggleGroup();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Alarm generation app for HMI/SCADA");

        // Создание переключателей для выбора HMI/SCADA
        RadioButton weintekBtn = new RadioButton(eHMI.WEINTEK.getValue());
        RadioButton oniBtn = new RadioButton(eHMI.ONI.getValue());
        RadioButton omronNbBtn = new RadioButton(eHMI.OMRON_NB.getValue());
        RadioButton winccBtn = new RadioButton(eHMI.WINCC.getValue());
        weintekBtn.setToggleGroup(hmiGroup);
        oniBtn.setToggleGroup(hmiGroup);
        omronNbBtn.setToggleGroup(hmiGroup);
        winccBtn.setToggleGroup(hmiGroup);
        weintekBtn.setSelected(true);

        // Создание переключателей для выбора протокола
        RadioButton codesysBtn = new RadioButton(eProtocol.CODESYS.getValue());
        RadioButton opcBtn = new RadioButton(eProtocol.OPC.getValue());
        RadioButton omronBtn = new RadioButton(eProtocol.OMRON.getValue());
        RadioButton modbusBtn = new RadioButton(eProtocol.MODBUS.getValue());
        codesysBtn.setToggleGroup(protocolGroup);
        opcBtn.setToggleGroup(protocolGroup);
        omronBtn.setToggleGroup(protocolGroup);
        modbusBtn.setToggleGroup(protocolGroup);
        codesysBtn.setSelected(true);

        // Создание переключателей для выбора шаблона
        RadioButton basicTemplateBtn = new RadioButton(eTemplate.BASIC.getValue());
        RadioButton extendedTemplateBtn = new RadioButton(eTemplate.EXT.getValue());
        RadioButton customTemplateBtn = new RadioButton(eTemplate.CUSTOM.getValue());
        basicTemplateBtn.setToggleGroup(templateGroup);
        extendedTemplateBtn.setToggleGroup(templateGroup);
        customTemplateBtn.setToggleGroup(templateGroup);
        basicTemplateBtn.setSelected(true);

        // Кнопка для открытия файла
        Button openFileBtn = new Button("Open XLSX");
        openFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XLSX File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                updateStatus("File: " + selectedFile.getName());
                System.out.println("load from: " + selectedFile.getAbsolutePath());
            }
        });

        // Кнопка для выбора файла для сохранения
        Button saveToFileBtn = new Button("Save to...");
        saveToFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save to...");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                saveToFile = selectedFile.getAbsolutePath(); // Сохраняем путь вместо создания файла
                updateStatus("Saving to: " + selectedFile.getName());
                System.out.println("save to: " + saveToFile);
            }
        });

        // Метка для отображения статуса с фиксированной шириной
        statusLabel = new Label("Ready");
        statusLabel.setPrefWidth(150);
        statusLabel.setAlignment(Pos.CENTER_LEFT);
        statusLabel.setStyle("-fx-border-color: lightgrey; -fx-padding: 5px;");

        // Кнопка для генерации
        Button generateBtn = new Button("Generate!");
        generateBtn.setOnAction(e -> {
            if (selectedFile == null) {
                updateStatus("Error: No file selected");
                return;
            }
            if (saveToFile == null) {
                updateStatus("Error: No save location");
                return;
            }
            try {
                System.out.println("generate started");
                AlarmGeneration.generate(getSelectedFile(), saveToFile);
                updateStatus("File saved successfully.");
            } catch (IOException ex) {
                updateStatus("Error: Could not save the file.");
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

            // Вызываем функцию генерации аварий, передавая выбранные параметры
            try {
                //generateAlarms(selectedFile, saveToFile, hmiSelection, protocolSelection, templateSelection);
                updateStatus("Success!");
            } catch (Exception ex) {
                updateStatus("Error: Check logs");
                ex.printStackTrace(); // Логгируем ошибку для отладки
            }
        });

        // Расположение переключателей HMI/SCADA, протоколов и шаблонов в сетке
        GridPane mainGrid = new GridPane();
        mainGrid.setVgap(10);
        mainGrid.setHgap(10);

        mainGrid.add(new Label("Select HMI/SCADA:"), 0, 0);
        mainGrid.add(weintekBtn, 1, 0);
        mainGrid.add(oniBtn, 2, 0);
        mainGrid.add(omronNbBtn, 3, 0);
        mainGrid.add(winccBtn, 4, 0);

        mainGrid.add(new Label("Select protocol:"), 0, 1);
        mainGrid.add(codesysBtn, 1, 1);
        mainGrid.add(opcBtn, 2, 1);
        mainGrid.add(omronBtn, 3, 1);
        mainGrid.add(modbusBtn, 4, 1);

        mainGrid.add(new Label("Select template:"), 0, 2);
        mainGrid.add(basicTemplateBtn, 1, 2);
        mainGrid.add(extendedTemplateBtn, 2, 2);
        mainGrid.add(customTemplateBtn, 3, 2);

        // Нижний ряд с кнопками и статусом
        HBox bottomRow = new HBox(10, openFileBtn, saveToFileBtn, statusLabel, generateBtn);
        bottomRow.setAlignment(Pos.CENTER);

        // Основная компоновка
        GridPane root = new GridPane();
        root.setVgap(15);
        root.setAlignment(Pos.CENTER);
        root.add(mainGrid, 0, 0);
        root.add(bottomRow, 0, 1);

        // Настройка сцены и отображение окна
        Scene scene = new Scene(root, 600, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static eHMI getHmi() {
        // Получаем выбранный HMI/SCADA
        RadioButton selectedHmi = (RadioButton) hmiGroup.getSelectedToggle();
        return eHMI.findByValue(selectedHmi.getText());  // Например, "Weintek", "ONI", и т.д.
    }

    public static eProtocol getProtocol () {
        // Получаем выбранный протокол
        RadioButton selectedProtocol = (RadioButton) protocolGroup.getSelectedToggle();
        return eProtocol.findByValue(selectedProtocol.getText());  // Например, "CoDeSyS", "OPC", и т.д.
    }

    public static eTemplate getTemplate() {
        // Получаем выбранный шаблон
        RadioButton selectedTemplate = (RadioButton) templateGroup.getSelectedToggle();
        return eTemplate.findByValue(selectedTemplate.getText());  // Например, "Basic", "Extended", "Custom"
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
