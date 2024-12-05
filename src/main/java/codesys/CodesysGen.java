package codesys;

import databases.GData;
import generation.Xml;

public class CodesysGen extends CodesysGenAbstract{

    CodesysGenPous codesysGenPous = new CodesysGenPous();
    CodesysGenVarLists codesysGenVarLists = new CodesysGenVarLists();
    CodesysGenStructure codesysGenStructure = new CodesysGenStructure();

    public CodesysGen() {
    }

    public StringBuilder createXml () {
        StringBuilder header = genHeader();
        StringBuilder xml = new StringBuilder();
        xml.append(header);
        xml.append(codesysGenPous.genPous());
        xml.append(generateTagInstances(generateTagConfigurationsVoid()));
        StringBuilder addData = new StringBuilder();
        addData.append(codesysGenVarLists.genVarLists());
        addData.append(codesysGenStructure.genProjectStructure());
        xml.append(generateTagAddData(addData));
        StringBuilder project = generateFirstRow();
        project.append(generateTagProject(xml));
        return project;
    }

    public StringBuilder genHeader() {
        StringBuilder fileHeader = generateTagFileHeader(
                "sintozone's studio",
                GData.getPlc().getValue(),
                GData.getPlc().getValue() + " V3.5 " + GData.getVersion(),
                GData.getTimeStamp());
        StringBuilder coordinateInfo = generateTagCoordinateInfo();
        StringBuilder data = generateTagData("projectinformation", "implementation", generateTagProjectInformation());
        StringBuilder addData = generateTagAddData(data);
        StringBuilder contentHeader = generateTagContentHeader(
                GData.getProjectName() + ".project",
                GData.getTimeStamp(),
                coordinateInfo,
                addData);
        StringBuilder result = new StringBuilder();
        result.append(fileHeader);
        result.append(contentHeader);
        return result;
    }

    // Метод для создания тега <fileHeader>
    private StringBuilder generateTagFileHeader(String companyName, String productName, String productVersion, String creationDateTime) {
        StringBuilder tag = new StringBuilder(String.format(
                "<fileHeader companyName=\"%s\" productName=\"%s\" productVersion=\"%s\" creationDateTime=\"%s\" />\n",
                companyName, productName, productVersion, creationDateTime
        ));
        tag = Xml.addTab(tag);
        return tag;
    }

    // Метод для создания тега <contentHeader>
    private StringBuilder generateTagContentHeader(String name, String modificationDateTime, StringBuilder coordinateInfo, StringBuilder addData) {
        StringBuilder tag = new StringBuilder();
        tag.append(String.format(
                "<contentHeader name=\"%s\" modificationDateTime=\"%s\">\n",
                name, modificationDateTime
        ));
        tag.append(coordinateInfo);
        tag.append(addData);
        tag.append("</contentHeader>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    // Метод для создания тега <coordinateInfo>
    private StringBuilder generateTagCoordinateInfo() {
        StringBuilder tag = new StringBuilder();
        tag.append("<coordinateInfo>\n");
        tag.append(generateTagFbdScaling(1, 1));
        tag.append(generateTagLdScaling(1, 1));
        tag.append(generateTagSfcScaling(1, 1));
        tag.append("</coordinateInfo>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    // Метод для создания тега <fbd> с <scaling>
    private StringBuilder generateTagFbdScaling(int x, int y) {
        return new StringBuilder(String.format(
                "  <fbd>\n    <scaling x=\"%d\" y=\"%d\" />\n  </fbd>\n",
                x, y
        ));

    }

    // Метод для создания тега <ld> с <scaling>
    private StringBuilder generateTagLdScaling(int x, int y) {
        return new StringBuilder(String.format(
                "  <ld>\n    <scaling x=\"%d\" y=\"%d\" />\n  </ld>\n",
                x, y
        ));
    }

    // Метод для создания тега <sfc> с <scaling>
    private StringBuilder generateTagSfcScaling(int x, int y) {
        return new StringBuilder(String.format(
                "  <sfc>\n    <scaling x=\"%d\" y=\"%d\" />\n  </sfc>\n",
                x, y
        ));
    }

    private StringBuilder generateTagProjectInformation() {
        return new StringBuilder().append("<ProjectInformation />");
    }

    private StringBuilder generateFirstRow () {
        StringBuilder tag = new StringBuilder();
        tag.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        return tag;
    }

    private StringBuilder generateTagProject(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<project xmlns=\"http://www.plcopen.org/xml/tc6_0200\">\n");
        tag.append(content);
        tag.append("</project>");
        return tag;
    }

    private StringBuilder generateTagInstances (StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<instances>\n");
        tag.append(content);
        tag.append("</instances>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    private StringBuilder generateTagConfigurationsVoid () {
        StringBuilder tag = new StringBuilder();
        tag.append("<configurations />\n");
        tag = Xml.addTab(tag);
        return tag;
    }
}
