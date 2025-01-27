package appFx;

import devHMI.AlarmGeneration;
import devHMI.Weintek;
import devPLC.CodesysGen;
import databases.DatabaseRegistry;
import databases.GData;
import enums.*;
import generation.DeviceCreator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AppFX extends Application {

    private ePLC plc = ePLC.EMPTY;
    private static File sourceFile = null;
    private static String targetFolder = System.getProperty("user.dir"); ;

    private static final FxToggles fxGrids = new FxToggles();
    private static final FxOptions fxOptions = new FxOptions();

    CodesysGen codesysGen = new CodesysGen();

    public AppFX() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Alarm and code generation app for PLC/HMI/SCADA");


        // Основная компоновка
        GridPane root = new GridPane();
        root.setVgap(15);
        root.setAlignment(Pos.TOP_LEFT);
        root.add(fxGrids.getGridHMI(), 0, 0);
        root.add(fxGrids.getGridPLC(), 0, 1);
        root.add(fxGrids.getGridTemplate(), 0, 2);
        root.add(fxOptions.getGridSourceFile(primaryStage), 0, 3);
        root.add(fxOptions.getGridTargetFolder(primaryStage), 0, 4);
        root.add(fxOptions.getGridProjectName(),0,5);
        root.add(fxOptions.getGridDevices(),0,6);
        root.add(fxOptions.getGridActions(),0,7);
        root.add(fxOptions.getGridOptions(),0,8);

        Button btnGenerate = new Button("Generate!");
        btnGenerate.setPrefWidth(550);
        btnGenerate.setAlignment(Pos.CENTER);

        root.add(btnGenerate,0,9);

        //Actions
        btnGenerate.setOnAction(e -> {
                    readData();
                    //filling database
                    try {
                        System.out.println(GData.getInstance().getDevices().toString());
                        DatabaseRegistry.clearAllDatabases();
                        DeviceCreator deviceCreator = new DeviceCreator(sourceFile, GData.getInstance().getDevices());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //generate code
                    if (GData.getInstance().getActions().contains(eActions.PUO) || GData.getInstance().getActions().contains(eActions.IOL) || GData.getInstance().getActions().contains(eActions.MBS)) {
                        StringBuilder xml = new StringBuilder();
                        switch (GData.getInstance().getPlc()) {
                            case CODESYS -> xml = codesysGen.createXml();
                        }
                        XmlSaver.saveXml(xml);
                    }
                    //generate alarms
                    if (GData.getInstance().getActions().contains(eActions.ALM)){
                        AlarmGeneration alarmGeneration = new AlarmGeneration();
                        switch (GData.getInstance().getHmi()){
                            case WEINTEK -> {
                                Weintek weintek = new Weintek();
                                weintek.exportToExcelWeintek(GData.getInstance().getTargetFolder());
                            }
                        }
                    }
                    GData.getInstance().clear();
        });

        // Настройка сцены и отображение окна
        Scene scene = new Scene(root, 550, 355);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readData() {
        GData.getInstance().getDevices().clear();
        GData.getInstance().getActions().clear();
        GData.getInstance().setHmi(fxGrids.getHmi());
        GData.getInstance().setPlc(fxGrids.getPlc());
        GData.getInstance().setTemplate(fxGrids.getTemplate());
        if (plc.equals(ePLC.CODESYS)) GData.getInstance().setVersion(fxGrids.getVersionCodesys());
        if (plc.equals(ePLC.TIA_PORTAL)) GData.getInstance().setVersion(fxGrids.getVersionTiaPortal());
        sourceFile = FxOptions.getSourceFile();
        targetFolder = FxOptions.getTargetFolder();
        GData.getInstance().setTargetFolder(targetFolder);
        GData.getInstance().setProjectName(FxOptions.getProjectName());
        GData.getInstance().setDevices(FxOptions.getDevices());
        GData.getInstance().setActions(FxOptions.getActions());
        GData.getInstance().setOptions(FxOptions.getOptions());
    }
}
