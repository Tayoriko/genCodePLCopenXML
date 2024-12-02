package codesys;

import enums.eDevType;
import enums.eVarType;

public class CodesysGenPous {

    CodesysGenVarTags codesysVarTags = new CodesysGenVarTags();

    public CodesysGenPous() {

    }

    public StringBuilder generateDrvPou(eDevType devType){
        //body section
        StringBuilder code = genCode(devType);
        StringBuilder xhtml = generateTagXhtml(code);
        StringBuilder st = generateTagSt(xhtml);
        StringBuilder body = generateTagBody(st);
        //var section
        StringBuilder drv = codesysVarTags.genArray("mt." + devType.getDrv(), "IDL." + devType.getCnt());
        drv.append(genVarsDefault());
        StringBuilder localVars = generateTagLocalVars(drv);
        StringBuilder intrface = generateTagInterface(localVars);
        //combine
        StringBuilder combine = intrface.append(body);
        StringBuilder pou = generateTagPou(devType.getPou(), "program", combine);
        return pou;
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
        StringBuilder pt = codesysVarTags.genArray(eVarType.PT.getTypeName(), "1");
        StringBuilder nt = codesysVarTags.genArray(eVarType.NT.getTypeName(), "1");
        vars.append(step);
        vars.append(cnt);
        vars.append(id);
        vars.append(pt);
        vars.append(nt);
        return vars;
    }

    private StringBuilder genCode(eDevType devType) {
        //st code with called devices
        StringBuilder code = new StringBuilder();
        code.append("\n");
        code.append(devType.getLoader());
        code.append("\n");
        code.append(String.format("//Call to execute %s processing\n", devType.getValue()));
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
        return tag;
    }

    // Метод для создания тега <interface>
    private StringBuilder generateTagInterface(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<interface>\n");
        tag.append(content);
        tag.append("</interface>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    private StringBuilder generateTagLocalVars(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<localVars>\n");
        tag.append(content);
        tag.append("</localVars>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для создания тега <body>
    private StringBuilder generateTagBody(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<body>\n");
        tag.append(content);
        tag.append("</body>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для создания тега <ST>
    private StringBuilder generateTagSt(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<ST>\n");
        tag.append(content);
        tag.append("</ST>\n");
        tag = addPrefix(tag, "  ");
        return tag;
    }

    // Метод для создания тега <XHTML>
    private StringBuilder generateTagXhtml(StringBuilder content) {
        StringBuilder tag = new StringBuilder();
        tag.append("<xhtml xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        tag.append(content);
        tag.append("</xhtml>\n");
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
