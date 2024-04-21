package genXML;

import enumerations.eXMLpath;
import system.GDB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GenBodyXML {

    private Map<eXMLpath, String> xmlBody = new HashMap<>();
    private int tabs = 0;

    public GenBodyXML () {}

    public String getXML () {
        GenAddData genAddData = new GenAddData();
        StringBuilder sb = new StringBuilder();
        tabs = 0;
        sb.append(getOpenTags()).append("\n");
        tabs++;
        sb.append(getFileHeader()).append("\n");
        tabs++;
        sb.append(getContentHeader()).append("\n");
        sb.append(getTmp()).append("\n");
        sb.append(genAddData.getAddData(tabs)).append("\n");
        sb.append("</project>");
        return sb.toString();
    }

    private String getOpenTags () {
        String result = GDB.xmlVersion + "\n";
        result += "<project xmlns=\"http://www.plcopen.org/xml/tc6_0200\">";
        return result;
    }

    private String getFileHeader () {
        GenFileHeader genFileHeader = new GenFileHeader();
        return genFileHeader.genFileHeader(this.tabs);
    }

    private String getContentHeader () {
        GenContentHeader genContentHeader = new GenContentHeader();
        return genContentHeader.genContentHeader(this.tabs);
    }

    private String getTmp () {
        return "  <types>\n" +
                "    <dataTypes />\n" +
                "    <pous />\n" +
                "  </types>\n" +
                "  <instances>\n" +
                "    <configurations />\n" +
                "  </instances>";
    }

    private String getAddData () {
        GenAddData genAddData = new GenAddData();
        return genAddData.getAddData(this.tabs);
    }

}
