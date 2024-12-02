package codesys;

import dev.AbstractDevice;
import enums.eDevType;
import enums.eVarType;

public class CodesysGenVarTags {

    public CodesysGenVarTags() {
    }

    protected StringBuilder genId (AbstractDevice dev) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"ID_").append(dev.getDevName()).append("\">\n");
        xml.append(generateTagType(eVarType.INT.getTypeName()));
        xml.append(generateTagInitialValue(String.valueOf(dev.getId())));
        xml.append("</variable>\n");
        return xml;
    }

    protected StringBuilder genIol (AbstractDevice dev) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(dev.getDevName()).append("\">\n");
        xml.append(selectVarTypeIol(dev.getDev()));
        xml.append(generateTagDocumentation(dev.getCommentIol()));
        xml.append("</variable>\n");
        return xml;
    }

    protected StringBuilder genNet (AbstractDevice dev) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(dev.getDevName()).append("\">\n");
        xml.append(selectVarTypeNet(dev.getDev()));
        xml.append("</variable>\n");
        return xml;
    }

    protected StringBuilder genVar (String varName, String varType, String comment) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(varName).append("\">\n");
        xml.append(generateTagType(varType));
        xml.append(generateTagDocumentation(comment));
        xml.append("</variable>\n");
        return xml;
    }

    protected StringBuilder genArray (String varType, String cnt) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(varType).append("\">\n");
        StringBuilder array = generateTagArray(varType, cnt);
        StringBuilder type = generateTagTypeSb(array);
        StringBuilder documentation = generateTagDocumentation("Driver for standard " + varType);
        xml.append(type);
        xml.append(documentation);
        xml.append("</variable>");
        return xml;
    }

    private StringBuilder selectVarTypeIol(eDevType dev) {
        StringBuilder var = new StringBuilder();
        switch (dev.getVarType()){
            case IOLD, IOLA -> {
                var.append(generateTagUdtType("mt." + dev.getVarType().getTypeName()));
                break;
            }
            default -> {
                var.append(generateTagType(dev.getVarType().getTypeName()));
                break;
            }
        }
        return var;
    }

    private StringBuilder selectVarTypeNet(eDevType dev) {
        StringBuilder var = new StringBuilder();
        switch (dev.getVarType()){
            case IOLD -> {
                var.append(generateTagUdtType("mt." + eVarType.NETD.getTypeName()));
                break;
            }
            case IOLA, REAL -> {
                var.append(generateTagUdtType("mt." + eVarType.NETA.getTypeName()));
                break;
            }
            default -> {
                var.append(generateTagType(dev.getVarType().getTypeName()));
                break;
            }
        }
        return var;
    }

    // Метод для генерации тега <type>
    private StringBuilder generateTagType(String type) {
        StringBuilder tag = new StringBuilder();
        tag.append("<type>\n");
        tag.append("    <").append(type).append(" />\n");
        tag.append("</type>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для генерации тега <type>
    private StringBuilder generateTagTypeSb(StringBuilder type) {
        StringBuilder tag = new StringBuilder();
        tag.append("<type>\n");
        tag.append(type);
        tag.append("</type>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для создания тега <array>
    public StringBuilder generateTagArray(String varType, String cnt) {
        StringBuilder tag = new StringBuilder();
        tag.append("<array>\n");
        tag.append(generateTagDimension(cnt));
        tag.append(generateTagUdtType(varType));
        tag.append("</array>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для создания тега <dimension>
    public StringBuilder generateTagDimension(String cnt) {
        StringBuilder tag = new StringBuilder();
        tag.append(String.format(
                "<dimension lower=\"0\" upper=\"%s\" />\n",
                cnt
        ));
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для создания тега <baseType>
    public StringBuilder generateTagUdtType(String varType) {
        StringBuilder tag = new StringBuilder();
        tag.append("<baseType>\n");
        tag.append("  <derived name=\"").append(varType).append("\" />\n");
        tag.append("</baseType>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для генерации тега <initialValue>
    private StringBuilder generateTagInitialValue(String value) {
        StringBuilder tag = new StringBuilder();
        tag.append("<initialValue>\n");
        tag.append("  <simpleValue value=\"").append(value).append("\" />\n");
        tag.append("</initialValue>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для генерации тега <documentation>
    private StringBuilder generateTagDocumentation(String documentation) {
        StringBuilder tag = new StringBuilder();
        tag.append("<documentation>\n");
        tag.append("    <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">").append(documentation).append("</xhtml>\n");
        tag .append("</documentation>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    private StringBuilder addPrefix(StringBuilder content, String prefix){
        String[] lines = content.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(prefix).append(line).append("\n");
        }
        return sb;
    }

}
