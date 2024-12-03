package appFx;

import codesys.CodesysGen;
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

        Button btnGenerate = new Button("Generate!");
        btnGenerate.setPrefWidth(550);
        btnGenerate.setAlignment(Pos.CENTER);

        root.add(btnGenerate,0,8);

        //Actions
        btnGenerate.setOnAction(e -> {
                    readData();
                    try {
                        System.out.println(GData.getDevices().toString());
                        DeviceCreator deviceCreator = new DeviceCreator(sourceFile, GData.getDevices());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    StringBuilder xml = new StringBuilder();
                    switch (GData.getPlc()){
                        case CODESYS -> xml = codesysGen.createXml();
                    }
                    //System.out.println(codesysGen.createXml());
                    XmlSaver.saveXml(xml);
                });

        // Настройка сцены и отображение окна
        Scene scene = new Scene(root, 550, 325);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readData() {
        GData.getDevices().clear();
        GData.getActions().clear();
        GData.setHmi(fxGrids.getHmi());
        GData.setPlc(fxGrids.getPlc());
        GData.setTemplate(fxGrids.getTemplate());
        if (plc.equals(ePLC.CODESYS)) GData.setVersion(fxGrids.getVersionCodesys());
        if (plc.equals(ePLC.TIA_PORTAL)) GData.setVersion(fxGrids.getVersionTiaPortal());
        sourceFile = FxOptions.getSourceFile();
        targetFolder = FxOptions.getTargetFolder();
        GData.setTargetFolder(targetFolder);
        GData.setProjectName(FxOptions.getProjectName());
        GData.setDevices(FxOptions.getDevices());
        GData.setActions(FxOptions.getActions());
    }


}
