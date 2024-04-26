package genXML;

import system.GDB;


public class GenBodyXML {

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
