package codesys;

import databases.GData;
import dev.AbstractDevice;
import enums.eDevType;
import enums.eTemplate;
import enums.eVarType;

public class CodesysGenVarTags extends CodesysGenAbstract {

    public CodesysGenVarTags() {
        super();
    }

    protected StringBuilder genId (AbstractDevice dev) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"ID_").append(dev.getDevName()).append("\">\n");
        xml.append(generateTagType(genrateType(eVarType.INT.getTypeName())));
        xml.append(generateTagInitialValue(String.valueOf(dev.getId())));
        xml.append("</variable>\n");
        xml = addPrefix(xml, GData.tab);
        return xml;
    }

    protected StringBuilder genIol (AbstractDevice dev) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(dev.getDevName()).append("\">\n");
        xml.append(selectVarTypeIol(dev.getDev()));
        xml.append(generateTagDocumentation(dev.getCommentIol()));
        xml.append("</variable>\n");
        xml = addPrefix(xml, GData.tab);
        return xml;
    }

    protected StringBuilder genNvl(AbstractDevice dev) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(dev.getDevName()).append("\">\n");
        xml.append(selectVarTypeNet(dev.getDev()));
        xml.append("</variable>\n");
        xml = addPrefix(xml, GData.tab);
        return xml;
    }

    protected StringBuilder genVar (String varName, String varType, String comment) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(varName).append("\">\n");
        xml.append(generateTagType(genrateType(varType)));
        xml.append(generateTagDocumentation(comment));
        xml.append("</variable>\n");
        xml = addPrefix(xml, GData.tab);
        return xml;
    }

    protected StringBuilder genArray(String varType, String cnt) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(varType).append("\">\n");
        StringBuilder array = generateTagArray(varType, cnt);
        StringBuilder type = generateTagType(String.valueOf(array));
        xml.append(type);
        xml.append("</variable>\n");
        xml = addPrefix(xml, GData.tab);
        return xml;
    }

    protected StringBuilder genArrayUdt(String name, String varType, String cnt) {
        StringBuilder xml = new StringBuilder();
        xml.append("<variable name=\"").append(name).append("\">\n");
        StringBuilder array = generateTagArrayUdt(varType, cnt);
        StringBuilder type = generateTagType(String.valueOf(array));
        StringBuilder documentation = generateTagDocumentation("Driver for standard " + varType);
        xml.append(type);
        xml.append(documentation);
        xml.append("</variable>\n");
        xml = addPrefix(xml, GData.tab);
        return xml;
    }

    private StringBuilder selectVarTypeIol(eDevType dev) {
        StringBuilder var = new StringBuilder();
        switch (dev.getVarType()){
            case IOLD, IOLA -> {
                var.append(generateTagTypeUdt("mt." + dev.getVarType().getTypeName()));
                break;
            }
            default -> {
                var.append(generateTagType(genrateType(dev.getVarType().getTypeName())));
                break;
            }
        }
        return var;
    }

    private StringBuilder selectVarTypeNet(eDevType dev) {
        StringBuilder var = new StringBuilder();
        switch (dev.getVarType()){
            case IOLD -> {
                var.append(generateTagTypeUdt("mt." + eVarType.NETD.getTypeName()));
                break;
            }
            case IOLA, REAL -> {
                var.append(generateTagTypeUdt("mt." + eVarType.NETA.getTypeName()));
                break;
            }
            default -> {
                var.append(generateTagType(genrateType(dev.getVarType().getTypeName())));
                break;
            }
        }
        return var;
    }

    private String genrateType(String type) {
        return ("  <" + type + " />\n");
    }

    // Метод для генерации тега <type>
    private StringBuilder generateTagType(String type) {
        StringBuilder tag = new StringBuilder();
        tag.append("<type>\n");
        tag.append(type);
        tag.append("</type>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <type>
    private StringBuilder generateTagTypeUdt(String varType) {
        StringBuilder tag = new StringBuilder();
        tag.append("<type>\n");
        if (GData.getTemplate().equals(eTemplate.CUSTOM_MV210)){
            if (varType.equals(eDevType.AI.getDrv()) || varType.equals(eDevType.AO.getDrv())){
                tag.append("  <derived name=\"").append(analogMV210(varType)).append("\" />\n");
            }
            else tag.append("  <derived name=\"").append(varType).append("\" />\n");
        }
        else tag.append("  <derived name=\"").append(varType).append("\" />\n");
        tag.append("</type>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    private String analogMV210(String varType){
        return varType + "_MV210";
    }

    // Метод для генерации тега <type>
    private StringBuilder generateTagBaseType(String type) {
        StringBuilder tag = new StringBuilder();
        tag.append("<baseType>\n");
        tag.append(type);
        tag.append("</baseType>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <type>
    private StringBuilder generateTagBaseTypeUdt(String varType) {
        StringBuilder tag = new StringBuilder();
        tag.append("<baseType>\n");
        tag.append("  <derived name=\"").append(varType).append("\" />\n");
        tag.append("</baseType>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <array>
    private StringBuilder generateTagArrayUdt (String varType, String cnt) {
        StringBuilder tag = new StringBuilder();
        tag.append("<array>\n");
        tag.append(generateTagDimension(cnt));
        tag.append(generateTagBaseTypeUdt(GData.libNameCodesys + varType));
        tag.append("</array>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <array>
    private StringBuilder generateTagArray (String varType, String cnt) {
        StringBuilder tag = new StringBuilder();
        tag.append("<array>\n");
        tag.append(generateTagDimension(cnt));
        tag.append(generateTagBaseType(varType));
        tag.append("</array>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для создания тега <dimension>
    private StringBuilder generateTagDimension(String cnt) {
        StringBuilder tag = new StringBuilder();
        tag.append(String.format(
                "<dimension lower=\"0\" upper=\"%s\" />\n",
                cnt
        ));
        tag = addPrefix(tag, GData.tab);
        return tag;
    }

    // Метод для генерации тега <initialValue>
    private StringBuilder generateTagInitialValue(String value) {
        StringBuilder tag = new StringBuilder();
        tag.append("<initialValue>\n");
        tag.append("  <simpleValue value=\"").append(value).append("\" />\n");
        tag.append("</initialValue>\n");
        tag = addPrefix(tag, GData.tab);
        return tag;
    }
}
