package devices;

public class IOLrecord {
    private final String name;
    private final String dataType;
    private final String comment;

    // Конструктор
    public IOLrecord(String name, String dataType, String comment) {
        this.name = name;
        this.dataType = dataType;
        this.comment = comment;
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

    public StringBuilder toXML () {
        return new StringBuilder(   "        <variable name=\"" + name + "\">\n" +
                                    "          <type>\n" +
                                    "            <" + dataType + " />\n" +
                                    "          </type>\n" +
                                    "          <documentation>\n" +
                                    "            <xhtml xmlns=\"http://www.w3.org/1999/xhtml\">" + comment + "</xhtml>\n" +
                                    "          </documentation>\n" +
                                    "        </variable>\n");
    }
}
