package org.example;

import alarmsGen.AlarmGeneration;
import generation.XmlCompose;
import enums.eDevType;
import enums.eHMI;
import enums.eProtocol;
import enums.eTemplate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AppFX extends Application {

    private Label statusLabel;
    private File selectedFile;
    private String saveToFile;
    private static ToggleGroup hmiGroup = new ToggleGroup();
    private static ToggleGroup protocolGroup = new ToggleGroup();
    private static ToggleGroup templateGroup = new ToggleGroup();
    private String customFolderPath = System.getProperty("user.dir"); // Путь по умолчанию — папка проекта
    private Label customFolderLabel;
    private Button selectFolderButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Alarm generation app for HMI/SCADA");

        // Создание переключателей для выбора HMI/SCADA
        RadioButton weintekBtn = new RadioButton(eHMI.WEINTEK.getValue());
        RadioButton oniBtn = new RadioButton(eHMI.ONI.getValue());
        RadioButton omronNbBtn = new RadioButton(eHMI.OMRON_NB.getValue());
        RadioButton winccBtn = new RadioButton(eHMI.WINCC.getValue());
        RadioButton tiaPanelsBtn = new RadioButton(eHMI.TIA.getValue()); // Новый пункт
        weintekBtn.setToggleGroup(hmiGroup);
        oniBtn.setToggleGroup(hmiGroup);
        omronNbBtn.setToggleGroup(hmiGroup);
        winccBtn.setToggleGroup(hmiGroup);
        tiaPanelsBtn.setToggleGroup(hmiGroup);
        weintekBtn.setSelected(true);

        // Создание переключателей для выбора протокола
        RadioButton codesysBtn = new RadioButton(eProtocol.CODESYS.getValue());
        RadioButton opcBtn = new RadioButton(eProtocol.OPC.getValue());
        RadioButton omronBtn = new RadioButton(eProtocol.OMRON.getValue());
        RadioButton modbusBtn = new RadioButton(eProtocol.MODBUS.getValue());
        RadioButton step7Btn = new RadioButton(eProtocol.STEP7.getValue()); // Новый пункт
        codesysBtn.setToggleGroup(protocolGroup);
        opcBtn.setToggleGroup(protocolGroup);
        omronBtn.setToggleGroup(protocolGroup);
        modbusBtn.setToggleGroup(protocolGroup);
        step7Btn.setToggleGroup(protocolGroup);
        codesysBtn.setSelected(true);

        // Создание переключателей для выбора шаблона
        RadioButton basicTemplateBtn = new RadioButton(eTemplate.BASIC.getValue());
        RadioButton extendedTemplateBtn = new RadioButton(eTemplate.EXT.getValue());
        RadioButton customTemplateBtn = new RadioButton(eTemplate.CUSTOM.getValue());
        basicTemplateBtn.setToggleGroup(templateGroup);
        extendedTemplateBtn.setToggleGroup(templateGroup);
        customTemplateBtn.setToggleGroup(templateGroup);
        basicTemplateBtn.setSelected(true);

        // Поле для ввода имени проекта
        Label projectNameLabel = new Label("Project name:");
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Enter project name");

        // Лейбл и кнопка для выбора кастомной папки, изначально скрытые
        customFolderLabel = new Label("Save to Folder: " + customFolderPath);
        selectFolderButton = new Button("Select Folder");
        customFolderLabel.setPrefWidth(500);

        // 2. Создание выпадающего списка для выбора версии CoDeSyS 3.5
        Label codesysVersionLabel = new Label("CoDeSyS v");
        ComboBox<String> codesysVersionComboBox = new ComboBox<>();

// Заполнение выпадающего списка версиями CoDeSyS SP 10-20 с учетом наличия патчей
        codesysVersionComboBox.setItems(FXCollections.observableArrayList(
                "SP10", "SP10 Patch 1",
                "SP11", "SP11 Patch 1",
                "SP12",
                "SP13", "SP13 Patch 1",
                "SP14",
                "SP15",
                "SP16", "SP16 Patch 1",
                "SP17", "SP17 Patch 1",
                "SP18",
                "SP19", "SP19 Patch 1", "SP19 Patch 2",
                "SP20"
        ));

// Установка значения по умолчанию
        codesysVersionComboBox.setValue("SP20");

        // Обработчик кнопки выбора папки
        selectFolderButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Custom Folder");
            directoryChooser.setInitialDirectory(new File(customFolderPath));
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                customFolderPath = selectedDirectory.getAbsolutePath();
                customFolderLabel.setText("Save to Folder: " + customFolderPath);
            }
        });

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

        // Метка для отображения статуса с фиксированной шириной
        statusLabel = new Label("Ready");
        statusLabel.setPrefWidth(150);
        statusLabel.setAlignment(Pos.CENTER_LEFT);
        statusLabel.setStyle("-fx-border-color: lightgrey; -fx-padding: 5px;");

        // Кнопка для генерации
        Button generateBtn = new Button("Generate Alarms");
        generateBtn.setOnAction(e -> {
            if (selectedFile == null) {
                updateStatus("Error: No file selected");
                return;
            }
            // Получаем имя проекта
            String projectName = projectNameField.getText();
            if (projectName.isEmpty()) {
                updateStatus("Error: Project name is required");
                return;
            }
            try {
                System.out.println("generate started");
                saveToFile = customFolderPath + "\\" + projectName + "_HMI_alarms_" + getHmi().getValue() + ".xlsx";
                AlarmGeneration.generateXlsx(getSelectedFile(), saveToFile);
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


        // Чекбоксы для выбора типов устройств
        CheckBox motorsCheckBox = new CheckBox(eDevType.MOTOR.getValue());
        CheckBox valvesCheckBox = new CheckBox(eDevType.VALVE.getValue());
        CheckBox aiCheckBox = new CheckBox(eDevType.AI.getName());
        CheckBox aoCheckBox = new CheckBox(eDevType.AO.getName());
        CheckBox diCheckBox = new CheckBox(eDevType.DI.getName());
        CheckBox doCheckBox = new CheckBox(eDevType.DO.getName());

        // Кнопка для генерации в нижнем ряду
        Button lowerGenerateBtn = new Button("Generate POUs");
        lowerGenerateBtn.setOnAction(e -> {
                    updateStatus("Generation...");
                    Set<eDevType> selectedDevices = new HashSet<>();

                    if (motorsCheckBox.isSelected()) selectedDevices.add(eDevType.MOTOR);
                    if (valvesCheckBox.isSelected()) selectedDevices.add(eDevType.VALVE);
                    if (aiCheckBox.isSelected()) selectedDevices.add(eDevType.AI);
                    if (aoCheckBox.isSelected()) selectedDevices.add(eDevType.AO);
                    if (diCheckBox.isSelected()) selectedDevices.add(eDevType.DI);
                    if (doCheckBox.isSelected()) selectedDevices.add(eDevType.DO);

                    // Проверяем, была ли выбрана папка
                    if (customFolderPath == null || customFolderPath.isEmpty()) {
                        updateStatus("Error: folder not selected");
                        return;
                    }

                    // Получаем имя проекта
                    String projectName = projectNameField.getText();
                    if (projectName.isEmpty()) {
                        updateStatus("Error: No project name");
                        return;
                    }

                    if (selectedFile == null) {
                        updateStatus("Error: No file selected");
                        return;
                    }

                    // Передаем папку, выбранные устройства и имя проекта в PouDevice
            XmlCompose pouDevice = null;
            try {
                pouDevice = new XmlCompose(selectedFile, customFolderPath, projectName, getProtocol(), codesysVersionComboBox.getValue(), selectedDevices);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            updateStatus("Generation complete.");
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
        mainGrid.add(tiaPanelsBtn, 5, 0);

        mainGrid.add(new Label("Select protocol:"), 0, 1);
        mainGrid.add(codesysBtn, 1, 1);
        mainGrid.add(opcBtn, 2, 1);
        mainGrid.add(omronBtn, 3, 1);
        mainGrid.add(modbusBtn, 4, 1);
        mainGrid.add(step7Btn, 5, 1);

        mainGrid.add(new Label("Select template:"), 0, 2);
        mainGrid.add(basicTemplateBtn, 1, 2);
        mainGrid.add(extendedTemplateBtn, 2, 2);
        mainGrid.add(customTemplateBtn, 3, 2);

        // Добавляем кастомную папку и кнопку выбора папки в сетку
        mainGrid.add(customFolderLabel, 0, 3, 5, 1);
        mainGrid.add(selectFolderButton, 5, 3);

        // Нижний ряд с кнопками и статусом
        HBox bottomRow = new HBox(10, openFileBtn, statusLabel, generateBtn);
        bottomRow.setAlignment(Pos.CENTER);

        // Нижняя строка с CheckBox для выбора устройств и кнопкой Generate
        HBox deviceSelectionRow = new HBox(10, motorsCheckBox, valvesCheckBox, aiCheckBox, aoCheckBox, diCheckBox, doCheckBox, lowerGenerateBtn);
        deviceSelectionRow.setAlignment(Pos.CENTER);

        // Строка для ввода имени проекта
        HBox projectNameRow = new HBox(10, projectNameLabel, projectNameField);
        projectNameRow.setAlignment(Pos.CENTER);
        projectNameField.setPrefWidth(250); // Устанавл
        // Добавление поля и выпадающего списка в интерфейс
        projectNameRow.getChildren().addAll(codesysVersionLabel, codesysVersionComboBox);// иваем предпочтительную ширину

        // Основная компоновка
        GridPane root = new GridPane();
        root.setVgap(15);
        root.setAlignment(Pos.CENTER);
        root.add(mainGrid, 0, 0);
        root.add(bottomRow, 0, 1);
        root.add(deviceSelectionRow, 0, 2);
        root.add(projectNameRow, 0, 3);

        // Настройка сцены и отображение окна
        Scene scene = new Scene(root, 525, 250);
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

    public String getCustomFolderPath() {
        return customFolderPath;
    }
}
