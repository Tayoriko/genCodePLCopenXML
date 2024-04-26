package genXML;

import system.GDB;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class GenTagXML {

    private String tagName = "";
    private String tagPrefix = "";
    private static int tabs = 0;
    private Map<String, String> attributesMap = new LinkedHashMap<>();

    public GenTagXML () {}

    public GenTagXML (String tagName) {
        this.tagName = tagName;
    }

    public GenTagXML (String tagName, String tagPrefix) {
        this.tagName = tagName;
        this.tagPrefix = tagPrefix;
    }

    public GenTagXML (String tagName, String tagPrefix, Map<String, String> attributesMap) {
        this.tagName = tagName;
        this.tagPrefix = tagPrefix;
        this.attributesMap = attributesMap;
    }

    public void addAttribute (String name, String value) {
        attributesMap.put(name, value);
    }

    public StringBuilder genOpen () {
        StringBuilder sbOpen = new StringBuilder();
        sbOpen.append("<");
        if (!tagPrefix.isEmpty()) {sbOpen.append(tagPrefix).append(":");}
        sbOpen.append(tagName);
        if (!attributesMap.isEmpty()) {
            GenTagAttributes tagAttributes = new GenTagAttributes();
            sbOpen.append(tagAttributes.genAttributes(attributesMap));
        }
        sbOpen.append(">");
        return sbOpen.append("\n");
    }

    public StringBuilder genClose () {
        StringBuilder sbClose = new StringBuilder();
        sbClose.append("</");
        if (!tagPrefix.isEmpty()) {sbClose.append(tagPrefix).append(":");}
        sbClose.append(tagName).append(">");
        return sbClose.append("\n");
    }

    public StringBuilder genVoid (String value) {
        StringBuilder sbVoid = new StringBuilder();
        sbVoid.append("<").append(value).append(" />");
        return sbVoid.append("\n");
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagPrefix() {
        return tagPrefix;
    }

    public void setTagPrefix(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }
}
