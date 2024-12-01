package appFx;

import enums.eHMI;
import enums.ePLC;
import enums.eTemplate;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class FxToggles {

    private ToggleGroup groupHmi;
    private ToggleGroup groupTemplate;
    private ToggleGroup groupPlc;;
    private ComboBox<String> versionBoxCodesys;
    private ComboBox<String> versionBoxTiaPortal;

    public FxToggles() {

    }

    //generate line with hmi selector
    public GridPane getGridHMI () {
        // Выбор тип элемента
        groupHmi = new ToggleGroup();
        Label label = new Label("Select HMI:");
        label.setPrefWidth(100);
        // Создание переключателей для выбора HMI/SCADA
        RadioButton btnWeintek = new RadioButton(eHMI.WEINTEK.getValue());
        btnWeintek.setToggleGroup(groupHmi);
        btnWeintek.setSelected(true);
        // Расположение переключателей HMI/SCADA, протоколов и шаблонов в сетке
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label, 0, 0);
        grid.add(btnWeintek, 1, 0);
        return grid;
    }

    //generate line with template selector
    public GridPane getGridTemplate () {
        // Выбор тип элемента
        groupTemplate = new ToggleGroup();
        Label label = new Label("Select template:");
        label.setPrefWidth(100);
        // Создание переключателей для выбора шаблона
        RadioButton btnBasic = new RadioButton(eTemplate.BASIC.getValue());
        RadioButton btnMV210 = new RadioButton(eTemplate.CUSTOM_MV210.getValue());
        btnBasic.setToggleGroup(groupTemplate);
        btnMV210.setToggleGroup(groupTemplate);
        btnBasic.setSelected(true);
        // Расположение переключателей HMI/SCADA, протоколов и шаблонов в сетке
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label, 0, 0);
        grid.add(btnBasic, 1, 0);
        grid.add(btnMV210, 2, 0);
        return grid;
    }

    //generate line with plc selector
    public GridPane getGridPLC () {
        // Выбор тип элемента
        groupPlc = new ToggleGroup();
        Label label = new Label("Select PLC:");
        label.setPrefWidth(100);
        // Создание переключателей для выбора протокола
        RadioButton btnCodesys = new RadioButton(ePLC.CODESYS.getValue());
        RadioButton btnTiaPortal = new RadioButton(ePLC.TIA_PORTAL.getValue());
        btnCodesys.setToggleGroup(groupPlc);
        btnTiaPortal.setToggleGroup(groupPlc);
        btnCodesys.setSelected(true);
        // Расположение переключателей HMI/SCADA, протоколов и шаблонов в сетке
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(label, 0, 0);
        grid.add(btnCodesys, 1, 0);
        grid.add(comboBoxCodesys(), 2, 0);
        grid.add(btnTiaPortal, 3, 0);
        grid.add(comboBoxStep7(), 4, 0);
        return grid;
    }

    private ComboBox<String> comboBoxCodesys() {
        versionBoxCodesys = new ComboBox<>();
        // Заполнение выпадающего списка версиями CoDeSyS SP 10-20 с учетом наличия патчей
        versionBoxCodesys.setItems(FXCollections.observableArrayList(
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
        versionBoxCodesys.setValue("SP20");
        return versionBoxCodesys;
    }

    private ComboBox<String> comboBoxStep7() {
        versionBoxTiaPortal = new ComboBox<>();
        // Заполнение выпадающего списка версиями CoDeSyS SP 10-20 с учетом наличия патчей
        versionBoxTiaPortal.setItems(FXCollections.observableArrayList(
                "V13",
                "V13 SP1",
                "V14",
                "V14 SP1",
                "V15",
                "V15 SP1",
                "V16",
                "V16 SP1",
                "V17",
                "V18"
        ));
        versionBoxTiaPortal.setValue("V18");
        return versionBoxTiaPortal;
    }




    public eHMI getHmi() {
        Toggle btn = groupHmi.getSelectedToggle();
        if (groupHmi != null) {
            RadioButton selectedButton = (RadioButton) btn;
            return eHMI.findByValue(selectedButton.getText()); // Возвращает текст выбранной кнопки
        }
        return eHMI.EMPTY; // Если ничего не выбрано;
    }

    public ePLC getPlc() {
        Toggle btn = groupPlc.getSelectedToggle();
        if (groupPlc != null) {
            RadioButton selectedButton = (RadioButton) btn;
            return ePLC.findByValue(selectedButton.getText()); // Возвращает текст выбранной кнопки
        }
        return ePLC.EMPTY; // Если ничего не выбрано;
    }

    public eTemplate getTemplate() {
        Toggle btn = groupTemplate.getSelectedToggle();
        if (groupTemplate != null) {
            RadioButton selectedButton = (RadioButton) btn;
            return eTemplate.findByValue(selectedButton.getText()); // Возвращает текст выбранной кнопки
        }
        return eTemplate.EMPTY; // Если ничего не выбрано;
    }

    public String getVersionCodesys() {
        return versionBoxCodesys.getValue();
    }

    public String getVersionTiaPortal() {
        return versionBoxTiaPortal.getValue();
    }
}
