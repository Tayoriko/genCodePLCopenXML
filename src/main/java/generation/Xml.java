package generation;

import databases.GData;

public class Xml {
    public Xml() {
    }

    // create raw tag
    public static StringBuilder genTag(String tag, StringBuilder content) {
        return new StringBuilder()
                        .append("<").
                        append(tag).
                        append(">\n").
                        append(content).
                        append("</").
                        append(tag).
                        append(">\n");
    }

    // create raw tag
    public static StringBuilder genTagOne(String tag, String one, String oneData, StringBuilder content) {
        return new StringBuilder().
                        append("<").
                        append(tag).
                        append(" ").
                        append(one).
                        append("=\"").
                        append(oneData).
                        append("\">\n").
                        append(content).
                        append("</").
                        append(tag).
                        append(">\n");
    }

    public static StringBuilder genTagTwo(String tag, String one, String oneData, String two, String twoData, StringBuilder content) {
        return new StringBuilder().
                append("<").
                append(tag).
                append(" ").
                append(one).
                append("=\"").
                append(oneData).
                append("\" ").
                append(two).
                append("=\"").
                append(twoData).
                append("\">\n").
                append(content).
                append("</").
                append(tag).
                append(">\n");
    }

    public static StringBuilder genLineRaw (String content){
        return new StringBuilder()
                        .append("<")
                        .append(content).
                        append( " />\n");
    }

    public static StringBuilder genLineOne(String tag, String one, String oneData) {
        return new StringBuilder().
                        append("<").
                        append(tag).
                        append(" ").
                        append(one).
                        append("=\"").
                        append(oneData).
                        append("\" />\n");
    }

    public static StringBuilder genLineOneClose(String tag, String one, String oneData) {
        return new StringBuilder().
                append("<").
                append(tag).
                append(" ").
                append(one).
                append("=\"").
                append(oneData).
                append("\" /").
                append(tag).
                append(">\n");
    }

    public static StringBuilder genLineOneCloseContext(String tag, String one, String oneData, String context) {
        return new StringBuilder().
                append("<").
                append(tag).
                append(" ").
                append(one).
                append("=\"").
                append(oneData).
                append("\">").
                append(context).
                append("</").
                append(tag).
                append(">\n");
    }

    public static StringBuilder genLineTwo(String tag, String one, String oneData, String two, String twoData) {
        return new StringBuilder().
                        append("<").
                        append(tag).
                        append(" ").
                        append(one).
                        append("=\"").
                        append(oneData).
                        append("\" ").
                        append(two).
                        append("=\"").
                        append(twoData).
                        append("\" />\n");
    }


    public static StringBuilder addTab(StringBuilder content){
        String[] lines = content.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(GData.tab).append(line).append("\n");
        }
        return sb;
    }
}
