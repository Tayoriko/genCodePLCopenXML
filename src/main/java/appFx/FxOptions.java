package appFx;

import enums.eActions;
import enums.eDevType;
import enums.eOptions;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FxOptions {

    private static File sourceFile;
    private static String targetFolder = System.getProperty("user.dir"); ;
    private static String projectName = "default";
    static GridPane gridDevices;
    static GridPane gridActions;
    static GridPane gridOptions;
    private static Set<eDevType> devices = new LinkedHashSet<>();
    private static Set<eActions> actions = new LinkedHashSet<>();
    private static Set<eOptions> options = new LinkedHashSet<>();

    public FxOptions() {
    }

    public GridPane getGridSourceFile (Stage primaryStage) {
        // Заголовок строки
        Label label = new Label("Select XLS:");
        label.setPrefWidth(100);
        Button btnOpenFile = new Button("Browse");
        btnOpenFile.setPrefWidth(75);
        btnOpenFile.setAlignment(Pos.CENTER);
        Label labelSource = new Label("select source file");
        labelSource.setAlignment(Pos.CENTER_LEFT);
        labelSource.setPrefWidth(350);
        labelSource.setStyle("-fx-border-color: lightgrey; -fx-padding: 5px;");
        btnOpenFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XLSX File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            sourceFile = fileChooser.showOpenDialog(primaryStage);
            if (sourceFile != null) {
                labelSource.setText(sourceFile.getName());
            }
        });
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label, 0, 0);
        grid.add(btnOpenFile, 1, 0);
        grid.add(labelSource, 2, 0);
        return grid;
    }

    public GridPane getGridTargetFolder (Stage primaryStage) {
        // Заголовок строки
        Label label = new Label("Select folder:");
        label.setPrefWidth(100);
        Button btnSelectFolder = new Button("Browse");
        btnSelectFolder.setPrefWidth(75);
        btnSelectFolder.setAlignment(Pos.CENTER);
        Label labelTarget = new Label(targetFolder);
        labelTarget.setAlignment(Pos.CENTER_LEFT);
        labelTarget.setPrefWidth(350);
        labelTarget.setStyle("-fx-border-color: lightgrey; -fx-padding: 5px;");
        // Обработчик кнопки выбора папки
        btnSelectFolder.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Custom Folder");
            directoryChooser.setInitialDirectory(new File(targetFolder));
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                targetFolder = selectedDirectory.getAbsolutePath();
                labelTarget.setText(targetFolder);
            }
        });
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label, 0, 0);
        grid.add(btnSelectFolder, 1, 0);
        grid.add(labelTarget, 2, 0);
        return grid;
    }

    public GridPane getGridProjectName() {
        // Заголовок строки
        Label label = new Label("Project name:");
        label.setPrefWidth(100);
        TextField projectNameField = new TextField();
        projectNameField.setPromptText(projectName);
        projectNameField.setPrefWidth(435);
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label, 0, 0);
        grid.add(projectNameField, 1, 0);
        return grid;
    }

    public GridPane getGridDevices() {
        // Заголовок строки
        Label label = new Label("Devices:");
        label.setPrefWidth(100);
        // Чекбоксы для выбора типов устройств
        CheckBox boxDi = new CheckBox(eDevType.DI.getName());
        CheckBox boxDo = new CheckBox(eDevType.DO.getName());
        CheckBox boxAi = new CheckBox(eDevType.AI.getName());
        CheckBox boxAo = new CheckBox(eDevType.AO.getName());
        CheckBox boxPid = new CheckBox(eDevType.PID.getName());
        CheckBox boxFlow = new CheckBox(eDevType.FLOW.getName());
        CheckBox boxMotor = new CheckBox(eDevType.MOTOR.getName());
        CheckBox boxValve = new CheckBox(eDevType.VALVE.getName());
        //default
        boxDi.setSelected(true);
        boxDo.setSelected(true);
        boxAi.setSelected(true);
        boxAo.setSelected(true);
        boxPid.setSelected(false);
        boxFlow.setSelected(false);
        boxMotor.setSelected(false);
        boxValve.setSelected(false);
        //grid
        gridDevices = new GridPane();
        gridDevices.setVgap(10);
        gridDevices.setHgap(10);
        gridDevices.add(label, 0, 0);
        gridDevices.add(boxDi, 1, 0);
        gridDevices.add(boxDo, 2, 0);
        gridDevices.add(boxAi, 3, 0);
        gridDevices.add(boxAo, 4, 0);
        gridDevices.add(boxPid, 5, 0);
        gridDevices.add(boxFlow, 6, 0);
        gridDevices.add(boxMotor, 7, 0);
        gridDevices.add(boxValve, 8, 0);
        return gridDevices;
    }

    public GridPane getGridActions() {
        // Заголовок строки
        Label label = new Label("Actions:");
        label.setPrefWidth(100);
        // Чекбоксы для выбора типов устройств
        CheckBox boxAlarm = new CheckBox(eActions.ALM.getValue());
        CheckBox boxPou = new CheckBox(eActions.PUO.getValue());
        CheckBox boxIOL = new CheckBox(eActions.IOL.getValue());
        CheckBox boxModBus = new CheckBox(eActions.MBS.getValue());
        //default
        boxAlarm.setSelected(false);
        boxPou.setSelected(true);
        boxIOL.setSelected(true);
        boxModBus.setSelected(true);
        //grid
        gridActions = new GridPane();
        gridActions.setVgap(10);
        gridActions.setHgap(10);
        gridActions.add(label, 0, 0);
        gridActions.add(boxAlarm, 1, 0);
        gridActions.add(boxPou, 2, 0);
        gridActions.add(boxIOL, 3, 0);
        gridActions.add(boxModBus, 4, 0);
        return gridActions;
    }

    public GridPane getGridOptions() {
        // Заголовок строки
        Label label = new Label("Options:");
        label.setPrefWidth(100);
        // Чекбоксы для выбора типов устройств
        CheckBox boxBit = new CheckBox(eOptions.BIT.getValue());
        //default
        boxBit.setSelected(false);
        //grid
        gridOptions = new GridPane();
        gridOptions.setVgap(10);
        gridOptions.setHgap(10);
        gridOptions.add(label, 0, 0);
        gridOptions.add(boxBit, 1, 0);
        return gridOptions;
    }

    public static File getSourceFile() {
        return sourceFile;
    }

    public static String getTargetFolder() {
        return targetFolder;
    }

    public static String getProjectName() {
        return projectName;
    }

    public static Set<eDevType> getDevices() {
        devices.clear();
        // Проходим по всем дочерним элементам GridPane
        for (Node node : gridDevices.getChildren()) {
            if (node instanceof CheckBox) { // Проверяем, является ли элемент CheckBox
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) { // Проверяем, выбран ли CheckBox
                    eDevType devType = eDevType.findByName(checkBox.getText());
                    devices.add(devType);
                    System.out.println("Selected devices: " + devType.getValue());
                }
            }
        }
        return devices;
    }

    public static Set<eActions> getActions() {
        actions.clear();
        // Проходим по всем дочерним элементам GridPane
        for (Node node : gridActions.getChildren()) {
            if (node instanceof CheckBox) { // Проверяем, является ли элемент CheckBox
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) { // Проверяем, выбран ли CheckBox
                    actions.add(eActions.findByValue(checkBox.getText()));
                    System.out.println("Selected action: " + checkBox.getText());
                }
            }
        }
        return actions;
    }

    public static Set<eOptions> getOptions() {
        options.clear();
        // Проходим по всем дочерним элементам GridPane
        for (Node node : gridOptions.getChildren()) {
            if (node instanceof CheckBox) { // Проверяем, является ли элемент CheckBox
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) { // Проверяем, выбран ли CheckBox
                    options.add(eOptions.findByValue(checkBox.getText()));
                    System.out.println("Selected options: " + checkBox.getText());
                }
            }
        }
        return options;
    }
}
