package devPLC;

import databases.GData;
import dev.AbstractDevice;
import enums.eDevType;
import enums.eTemplate;
import enums.eVarType;
import generation.Xml;

public class CodesysGenVarTags extends CodesysGenAbstract {

    public CodesysGenVarTags() {
        super();
    }

    protected StringBuilder genId (AbstractDevice dev) {
        return Xml.genTagOne(
                eCtags.variable.getTag(),
                eCtags.name.getTag(),
                "ID_" + dev.getDevName(),
                generateTagType(genrateType(eVarType.INT.getTypeName())).
                        append(generateTagInitialValue(String.valueOf(dev.getId()))));
    }

    protected StringBuilder genIol (AbstractDevice dev) {
        return Xml.genTagOne(
                eCtags.variable.getTag(),
                eCtags.name.getTag(),
                dev.getDevName(),
                selectVarTypeIol(dev.getDev()).
                        append(generateTagDocumentation(dev.getCommentIol())));
    }

    protected StringBuilder genNvl(AbstractDevice dev) {
        return Xml.genTagOne(
                eCtags.variable.getTag(),
                eCtags.name.getTag(),
                dev.getDevName(),
                selectVarTypeNet(dev.getDev()));
    }

    protected StringBuilder genVar (String varName, String varType, String comment) {
        return Xml.genTagOne(
                eCtags.variable.getTag(),
                eCtags.name.getTag(),
                varName,
                generateTagType(genrateType(varType)).append(generateTagDocumentation(comment)));
    }

    protected StringBuilder genArray(String varName, String varType, String cnt, String comment) {
        return Xml.genTagOne(
                eCtags.variable.getTag(),
                eCtags.name.getTag(),
                varName,
                generateTagType(
                        Xml.addTab(
                                generateTagArray(varType, cnt)))
                        .append(generateTagDocumentation(comment)));
    }

    protected StringBuilder genArrayUdt(String varName, String varType, String cnt) {
        String text = "Driver for standard " + varType;
        return Xml.genTagOne(
                eCtags.variable.getTag(),
                eCtags.name.getTag(),
                varName,
                generateTagType(
                        Xml.addTab(
                                generateTagArrayUdt(varType, cnt)))
                        .append(generateTagDocumentation(text)));
    }

    private StringBuilder selectVarTypeIol(eDevType dev) {
        StringBuilder var = new StringBuilder();
        switch (dev.getVarType()){
            case IOLD, IOLA -> {
                var.append(generateTagTypeUdt(GData.codesysLibName + dev.getVarType().getTypeName()));
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
                var.append(generateTagTypeUdt(GData.codesysLibName + eVarType.NETD.getTypeName()));
                break;
            }
            case IOLA, REAL -> {
                var.append(generateTagTypeUdt(GData.codesysLibName + eVarType.NETA.getTypeName()));
                break;
            }
            default -> {
                var.append(generateTagType(genrateType(dev.getVarType().getTypeName())));
                break;
            }
        }
        return var;
    }

    private StringBuilder genrateType(String type) {
        return Xml.addTab(Xml.genLineRaw(type));
    }

    // Метод для генерации тега <type>
    private StringBuilder generateTagType(StringBuilder type) {
        return Xml.addTab(Xml.genTag(eCtags.type.getTag(), new StringBuilder(type)));
    }

    // Метод для создания тега <type>
    private StringBuilder generateTagTypeUdt(String varType) {
        String var = "";
        if (GData.getTemplate().equals(eTemplate.CUSTOM_MV210)){
            if (varType.equals(eDevType.AI.getDrv()) || varType.equals(eDevType.AO.getDrv())){
                var = analogMV210(varType);
            } else var = varType;
        } else var = varType;
        return Xml.addTab(
                Xml.genTag(
                    eCtags.type.getTag(),
                    Xml.addTab(
                            Xml.genLineOne(
                                eCtags.derived.getTag(),
                                eCtags.name.getTag(),
                                var))));
    }

    private String analogMV210(String varType){
        return varType + "_MV210";
    }

    // Метод для генерации тега <type>
    private StringBuilder generateTagBaseType(String type) {
        return Xml.genTag(
                eCtags.baseType.getTag(),
                Xml.addTab(
                        Xml.genLineOne(
                                eCtags.derived.getTag(),
                                eCtags.name.getTag(),
                                type)));
    }

    // Метод для создания тега <type>
    private StringBuilder generateTagBaseTypeUdt(String varType) {
        return Xml.genTag(
                eCtags.baseType.getTag(),
                Xml.addTab(
                        Xml.genLineOne(
                            eCtags.derived.getTag(),
                            eCtags.name.getTag(),
                            varType)));
    }

    // Метод для создания тега <array>
    private StringBuilder generateTagArrayUdt (String varType, String cnt) {
        return Xml.genTag(
                eCtags.array.getTag(),
                Xml.addTab(
                        generateTagDimension(cnt)
                                .append(generateTagBaseTypeUdt(
                                        GData.codesysLibName + varType))));
    }

    // Метод для создания тега <array>
    private StringBuilder generateTagArray (String varType, String cnt) {
        return Xml.genTag(
                eCtags.array.getTag(),
                Xml.addTab(
                        generateTagDimension(cnt)
                                .append(generateTagBaseType(varType))));
    }

    // Метод для создания тега <dimension>
    private StringBuilder generateTagDimension(String cnt) {
        return Xml.genLineTwo(
                eCtags.dimension.getTag(),
                eCtags.lower.getTag(),
                "0",
                eCtags.upper.getTag(),
                cnt);
    }

    // Метод для генерации тега <initialValue>
    private StringBuilder generateTagInitialValue(String value) {
        return Xml.addTab(
                Xml.genTag(
                    eCtags.initialValue.getTag(),
                    Xml.addTab(
                            Xml.genLineOne(
                                eCtags.simpleValue.getTag(),
                                eCtags.value.getTag(),
                                value))));
    }
}
