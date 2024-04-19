package uniqueItems;

import check.Check;
import check.CheckVar;
import enumerations.eVarAllocate;
import enumerations.eVarType;
import enumerations.eVarList;
import system.VelocityEngineSingleton;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import system.GDB;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class contain structure for one unique variable
 * Use this enumeration to work with variables as object
 */

public class OneVar {

    private String name = "noName";
    private eVarType type = eVarType.EMPTY;
    private String comment = GDB.empty;
    private Address address = new Address();

    public OneVar (String[] value){
        this.name = value[0];
        this.type = eVarType.findByValue(value[1]);
        this.comment = value[2];
    }

    public OneVar(){    }

    public String toString()
    {
        StringBuilder var;
        var = new StringBuilder(this.name);
        //add tab
        int len = this.name.length() / 4;
        if (address.isEnable()) {
            var.append("\t".repeat(Math.max(0, GDB.tabVarName - len - 3)));
        } else {var.append("\t".repeat(Math.max(0, GDB.tabVarName - len)));}
        //add addr
        if (address.isEnable()) {
            var.append("AT " + this.address.getAddress() + GDB.tab);
        }
        //add type
        var.append(":").append(this.type).append(";");
        //add tab to comment
        len = this.address.getAddress().length() / 4;
        for (int i = 0; i < GDB.tabToComment - len; i++) {
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
        context.put("tab", GDB.tab.repeat(Math.max(0, tabs)));
        context.put("name", this.name);
        context.put("address", this.address.getAddress());
        context.put("type", this.type.getValue());
        context.put("documentation", this.comment);

        // Получаем шаблон
        Template template;
        try {
            if (address.isEnable()) {
                template = velocityEngine.getTemplate(GDB.filepathTemplate +
                        GDB.templateOneVar +
                        GDB.fileFormatTemplate);
            }
            else {
                template = velocityEngine.getTemplate(GDB.filepathTemplate +
                        GDB.templateOneVarNoAddr +
                        GDB.fileFormatTemplate);
            }
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

    public void setAddress(eVarAllocate varAllocate, String address) {
        int word = 0;
        int bit = 0;
        if (!varAllocate.equals(eVarAllocate.EMPTY)) {
            if (type.equals(eVarType.BOOL)) {
                String[] value = address.split(GDB.splitVarAddress);
                word = Integer.parseInt(value[0]);
                bit = Integer.parseInt(value[1]);
                this.address = new Address(type, varAllocate, word, bit);
            } else {
                word = Integer.parseInt(address);
                this.address = new Address(type, varAllocate, word);
            }
        } else {
            this.address.deleteAddress();
        }
    }

    public void deleteAddress(){
        this.address.deleteAddress();
    }

    public String getName() {
        return name;
    }

    public eVarType getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public String getAddress() {
        return address.getAddress();
    }

}
