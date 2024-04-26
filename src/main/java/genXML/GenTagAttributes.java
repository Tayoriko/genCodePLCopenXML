package genXML;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenTagAttributes {

    private StringBuilder sb = new StringBuilder();
    private Map<String, String> attributesMap = new LinkedHashMap<>();

    public GenTagAttributes() {

    }

    public StringBuilder genAttributes(Map<String, String> attributesMap) {
        this.attributesMap = attributesMap;
        for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
            sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }
        return sb;
    }

}
