package devices;

public class IOLrecord {
    private final String name;
    private final String dataType;
    private final String comment;
    private final Integer id;

    // Конструктор
    public IOLrecord(String name, String dataType, String comment, int id) {
        this.name = name;
        this.dataType = dataType;
        this.comment = comment;
        this.id = id;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public String getComment() {
        return comment;
    }

    // Метод для строкового представления объекта
    @Override
    public String toString() {
        return "    " + name + "         :" + dataType + ";     //" + comment;
    }

    public StringBuilder toPLCopenXML() {
        return new StringBuilder(   "        <variable name=\"" + name + "\">\n" +
                                    "          <type>\n" +
                                    "            <" + dataType + " />\n" +
                                    "          </type>\n" +
                                    "          <documentation>\n" +
                                    "            <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">" + comment + "</xhtml>\n" +
                                    "          </documentation>\n" +
                                    "        </variable>\n");
    }

    public StringBuilder toPLCopenXMLid() {
        return new StringBuilder(   "        <variable name=\"ID_" + name + "\">\n" +
                "          <type>\n" +
                "            <INT />\n" +
                "          </type>\n" +
                "          <initialValue>\n" +
                "            <simpleValue value=\"" + id + "\" />\n" +
                "          </initialValue>\n" +
                "        </variable>\n");

    }
}
