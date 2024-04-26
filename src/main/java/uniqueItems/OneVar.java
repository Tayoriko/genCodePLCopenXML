package uniqueItems;

import enumerations.eVarAllocate;
import enumerations.eVarType;
import genXML.objects.ObjOneVar;
import org.apache.velocity.VelocityContext;
import system.GDB;
import system.GenXML;

/**
 * This class contain structure for one unique variable
 * Use this enumeration to work with variables as object
 */

public class OneVar extends GenXML {

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

    public String toXML()
    {
        ObjOneVar objOneVar = new ObjOneVar(this);

        return objOneVar.getXML().toString();

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

    public Boolean isEnableAddress ()
    {
        return address.isEnable();
    }

}
