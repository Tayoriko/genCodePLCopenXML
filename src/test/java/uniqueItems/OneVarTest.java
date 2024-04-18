package uniqueItems;

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
}