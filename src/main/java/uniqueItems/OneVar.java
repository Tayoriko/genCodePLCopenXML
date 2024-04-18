package uniqueItems;

import CSV.VelocityEngineSingleton;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import system.RegexBase;

import java.io.IOException;
import java.io.StringWriter;

/**
 * This class contain structure for one unique variable
 * Use this enumeration to work with variables as object
 */

public class OneVar {

    private String name = "noName";
    private String type = "emptyType";
    private String comment = "emptyComment";
    private String address = "";

    public OneVar (String[] value){
        this.name = value[0];
        this.type = value[1];
        this.comment = value[2];
    }

    public OneVar(){    }

    public String toString()
    {
        StringBuilder var;
        var = new StringBuilder(this.name);
        //add tab
        int len = this.name.length() / 4;
        var.append("\t".repeat(Math.max(0, RegexBase.tabVarName - len)));
        //add type
        var.append(":").append(this.type).append(";");
        //add tab to comment
        len = this.type.length() / 4;
        for (int i = 0; i < RegexBase.tabToComment - len; i++) {
            var.append("\t");
        }
        //add comment
        var.append("//").append(this.comment);

        return var.toString();
    }

    public String toXML(int tabs)
    {
        VelocityEngine velocityEngine = VelocityEngineSingleton.getInstance();

        // Создаем контекст Velocity
        VelocityContext context = new VelocityContext();

        // Заполняем контекст данными
        context.put("tab", RegexBase.tab.repeat(Math.max(0, tabs)));
        context.put("name", this.name);
        context.put("address", this.address);
        context.put("type", this.type);
        context.put("documentation", this.comment);

        // Получаем шаблон
        Template template;
        try {
            template = velocityEngine.getTemplate(RegexBase.filepathTemplate + "OneVar" + RegexBase.fileFormatTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Записываем данные из контекста в StringWriter с использованием шаблона
        StringWriter writer = new StringWriter();
        try {
            template.merge(context, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Выводим результат
        return writer.toString();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public String getAddress() {
        return address;
    }
}
