package genXML.objects;

import genXML.GenTagXML;
import system.GDB;
import uniqueItems.OneVar;

public class ObjOneVar {
    
    private final OneVar oneVar;
    private GenTagXML tagVariable = new GenTagXML("variable");
    private GenTagXML tagType = new GenTagXML("type");
    private GenTagXML tagDocumentation = new GenTagXML("documentation");
    private GenTagXML tagXtml = new GenTagXML("xhtml");
    
    public ObjOneVar (OneVar oneVar) {
        this.oneVar = oneVar;
    }
    
    public StringBuilder getXML ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(genHeader());
        sb.append(genType());
        sb.append(genDocumentation());
        sb.append(tagVariable.genClose());
        return sb;
    }
    
    private StringBuilder genHeader () {
        tagVariable.addAttribute("name", oneVar.getName());
        if (oneVar.isEnableAddress()) {tagVariable.addAttribute("address", oneVar.getAddress());}
        return tagVariable.genOpen();
    }
    
    private StringBuilder genType () {
        StringBuilder sbType = new StringBuilder();
        sbType.append(tagType.genOpen());
        sbType.append(tagType.genVoid(oneVar.getType().getValue()));
        sbType.append(tagType.genClose());
        return sbType;
    }
    
    private StringBuilder genDocumentation () {
        StringBuilder sbDoc = new StringBuilder();
        sbDoc.append(tagDocumentation.genOpen());
        if (!oneVar.getComment().isEmpty()) {
            tagXtml.addAttribute("xmlns", GDB.attributeXmlns);
            sbDoc.append(tagXtml.genOpen());
            sbDoc.delete(sbDoc.length()-1, sbDoc.length());
            sbDoc.append(oneVar.getComment()).append(tagXtml.genClose());
        }
        sbDoc.append(tagDocumentation.genClose());
        return sbDoc;
    }
}
