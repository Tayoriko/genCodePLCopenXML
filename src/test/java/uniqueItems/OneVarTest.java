package uniqueItems;

import enumerations.eVarAllocate;
import enumerations.eVarList;
import enumerations.eVarType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OneVarTest {

    @org.junit.jupiter.api.Test
    void testToString() {
        String[] varName = {"globalEmergency","BOOL","Emergency stop"};
        OneVar var = new OneVar(varName);
        System.out.println(var);
        varName = new String[]{"pulseS1", "BOOL", "global trigger 1 second"};
        var = new OneVar(varName);
        System.out.println(var);
    }

    @Test
    void toXML() {
        String[] varName = {"globalEmergency","BOOL","Emergency stop"};
        OneVar var = new OneVar(varName);
        System.out.println(var.toXML(1));
    }

    @Test
    void setAddress() {
        String[] varName = {"globalEmergency","BOOL","Emergency stop"};
        OneVar var = new OneVar(varName);
        var.setAddress(eVarAllocate.OUT,"3.14");
        System.out.println(var);
        Assertions.assertEquals("%QX3.14", var.getAddress());
    }
}