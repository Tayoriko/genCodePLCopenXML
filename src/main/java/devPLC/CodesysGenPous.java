package devPLC;

import databases.GData;
import enums.eDevType;
import enums.eTemplate;
import enums.eVarType;
import generation.Xml;

public class CodesysGenPous extends CodesysGenAbstract {

    CodesysGenVarTags codesysVarTags = new CodesysGenVarTags();

    public CodesysGenPous() {
        super();
    }

    public StringBuilder genPous() {
        StringBuilder allPous = new StringBuilder();
        GData.getDevices().forEach(device -> allPous.append(generateDrvPou(device)));
        StringBuilder pous = generateTagPous(allPous);
        return generateTagTypes(pous);
    }

    private StringBuilder generateDrvPou(eDevType devType){
        //var section
        StringBuilder drv;
        if (!GData.getTemplate().equals(eTemplate.BASIC)){
            switch (devType){
                case AI -> drv = codesysVarTags.genArrayUdt(devType.getDrv(), devType.getDrv() + "_" + GData.getTemplate().getValue(), "IDL." + devType.getCnt());
                case AO -> drv = codesysVarTags.genArrayUdt(devType.getDrv(), devType.getDrv() + "_" + GData.getTemplate().getValue(), "IDL." + devType.getCnt());
                default -> drv = codesysVarTags.genArrayUdt(devType.getDrv(),devType.getDrv(), "IDL." + devType.getCnt());
            }
        } else drv = codesysVarTags.genArrayUdt(devType.getDrv(),devType.getDrv(), "IDL." + devType.getCnt());
        drv.append(genVarsDefault());
        StringBuilder localVars = generateTagLocalVars(drv);
        StringBuilder intrface = generateTagInterface(localVars);
        //body section
        StringBuilder code = genCode(devType);
        StringBuilder xhtml = generateTagXhtml(code);
        StringBuilder st = generateTagSt(xhtml);
        StringBuilder body = generateTagBody(st);
        //addData
        StringBuilder objectId = generateTagObjectId("");
        StringBuilder data = generateTagData("objectid", "discard", objectId);
        StringBuilder addData = generateTagAddData(data);
        //combine
        StringBuilder combine = intrface.append(body);
        combine.append(addData);
        return generateTagPou(devType.getPou(), "program", combine);
    }

    private StringBuilder genVarsDefault() {
        StringBuilder vars = new StringBuilder();
        StringBuilder step = codesysVarTags.genVar(
                "step",
                eVarType.INT.getTypeName(),
                "step for process");
        StringBuilder cnt = codesysVarTags.genVar(
                "cnt",
                eVarType.INT.getTypeName(),
                "counter for FOR");
        StringBuilder id = codesysVarTags.genVar(
                "id",
                eVarType.INT.getTypeName(),
                "ID for copies");
        StringBuilder empty = codesysVarTags.genVar(
                "empty",
                eVarType.BOOL.getTypeName(),
                "empty boolean value");
        StringBuilder zero = codesysVarTags.genVar(
                "zero",
                eVarType.REAL.getTypeName(),
                "zero real value");
        StringBuilder pt = codesysVarTags.genArrayUdt("PT", eVarType.PT.getTypeName(), "1");
        StringBuilder nt = codesysVarTags.genArrayUdt("NT", eVarType.NT.getTypeName(), "1");
        vars.append(step);
        vars.append(cnt);
        vars.append(id);
        vars.append(pt);
        vars.append(nt);
        vars.append(empty);
        vars.append(zero);
        return vars;
    }

    private StringBuilder genCode(eDevType devType) {
        //st code with called devices
        StringBuilder code = new StringBuilder();
        code.append(String.format("//Call to execute %s processing\n", devType.getValue()));
        code.append(devType.getLoader());
        code.append("\n");
        code.append(CodesysCallDevices.generateDeviceData(devType));
        return code;
    }

    // Метод для создания тега <pou>
    private StringBuilder generateTagPou(String pouName, String pouType, StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append(String.format("<pou name=\"%s\" pouType=\"%s\">\n", pouName, pouType));
        tag.append(content);
        tag.append("</pou>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    // Метод для создания тега <interface>
    private StringBuilder generateTagInterface(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<interface>\n");
        tag.append(content);
        tag.append("</interface>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    private StringBuilder generateTagLocalVars(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<localVars>\n");
        tag.append(content);
        tag.append("</localVars>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    // Метод для создания тега <body>
    private StringBuilder generateTagBody(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<body>\n");
        tag.append(content);
        tag.append("</body>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    // Метод для создания тега <ST>
    private StringBuilder generateTagSt(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<ST>\n");
        tag.append(content);
        tag.append("</ST>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    private StringBuilder generateTagTypes(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<types>\n");
        tag.append("  <dataTypes />\n");
        tag.append(content);
        tag.append("</types>\n");
        tag = Xml.addTab(tag);
        return tag;
    }

    private StringBuilder generateTagPous(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<pous>\n");
        tag.append(content);
        tag.append("</pous>\n");
        tag = Xml.addTab(tag);
        return tag;
    }
}
