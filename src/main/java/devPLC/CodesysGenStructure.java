package devPLC;

import databases.GData;
import enums.eActions;
import generation.Xml;

public class CodesysGenStructure extends CodesysGenAbstract{

    public CodesysGenStructure() {
        super();
    }

    public StringBuilder genProjectStructure() {
        return generateTagData("projectstructure", "discard", generateProjectStructure());
    }

    private StringBuilder generateProjectStructure() {
        //global variables
        StringBuilder iol = generateObject("IOL", "");
        StringBuilder nvl = generateObject("NVL", "");
        StringBuilder varLists = new StringBuilder();
        StringBuilder folderVars = new StringBuilder();
        if (GData.getActions().contains(eActions.IOL)) varLists.append(iol);
        if (GData.getActions().contains(eActions.MBS)) varLists.append(nvl);
        if (GData.getActions().contains(eActions.IOL) || GData.getActions().contains(eActions.MBS)) {
            folderVars = generateFolder("Global Variables", varLists);
        }
        //pous
        StringBuilder pous = new StringBuilder();
        GData.getDevices().forEach(device -> pous.append(generateObject(device.getPou(), "")));
        StringBuilder folderPous = generateFolder("Local PRG", pous);

        StringBuilder structure = new StringBuilder();
        structure.append(folderVars);
        structure.append(folderPous);

        return generateProjectStructure(structure);
    }

    // Метод для создания тега Object
    private StringBuilder generateObject(String name, String objectId) {
        StringBuilder tag =  new StringBuilder(String.format(
                "<Object Name=\"%s\" ObjectId=\"%s\" />",
                name, objectId
        ));
        return Xml.addTab(tag);
    }

    // Метод для создания тега Folder
    private StringBuilder generateFolder(String folderName, StringBuilder objects) {
        StringBuilder tag =  new StringBuilder(String.format(
                "<Folder Name=\"%s\">\n%s</Folder>",
                folderName, objects.toString()
        ));
        return Xml.addTab(tag);
    }

    // Метод для создания ProjectStructure
    private StringBuilder generateProjectStructure(StringBuilder folder) {
        StringBuilder tag = new StringBuilder(String.format(
                "<ProjectStructure>\n%s</ProjectStructure>",
                folder.toString()
        ));
        return Xml.addTab(tag);
    }
}
