package codesys;

import databases.GData;

public abstract class CodesysGenAbstract {

    public CodesysGenAbstract() {
    }

    protected StringBuilder addPrefix(StringBuilder content, String prefix){
        String[] lines = content.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(prefix).append(line).append("\n");
        }
        return sb;
    }

    // Метод для генерации тега <documentation>
    protected StringBuilder generateTagDocumentation(String documentation) {
        StringBuilder tag = new StringBuilder();
        tag.append("<documentation>\n");
        tag.append("  <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">").append(documentation).append("</xhtml>\n");
        tag .append("</documentation>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <XHTML>
    protected StringBuilder generateTagXhtml(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<xhtml xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        tag.append(content);
        tag.append("</xhtml>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <addData>
    protected StringBuilder generateTagAddData(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<addData>\n");
        tag.append(content);
        tag.append("</addData>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    protected StringBuilder generateTagData(String data, String handle, StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<data name=\"http://www.3s-software.com/plcopenxml/" + data + "\" handleUnknown=\"" + handle + "\">\n");
        tag.append(content);
        tag.append("</data>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    protected StringBuilder generateTagAttributes(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<Attributes>\n");
        tag.append(content);
        tag.append("</Attributes>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    protected StringBuilder generateTagAttribute(String name, String value) {
        StringBuilder tag = new StringBuilder();
        tag.append("<Attribute Name = \"");
        tag.append(name);
        tag.append("\" Value=\"");
        tag.append(value);
        tag.append("\" />");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    protected StringBuilder generateTagObjectId(String id) {
        StringBuilder tag = new StringBuilder();
        tag.append("<ObjectId>");
        tag.append(id);
        tag.append("</ObjectId>");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }


}
