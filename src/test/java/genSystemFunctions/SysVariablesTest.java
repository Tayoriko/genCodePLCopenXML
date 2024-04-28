package genSystemFunctions;

import org.junit.jupiter.api.*;
import uniqueItems.OneVar;

class SysVariablesTest {



    @Test
    void setSysVariablesTest () {
        SysVariables sysVariables = new SysVariables();
        System.out.println(sysVariables.mapSysVar.get("sysClock").getFirst().toXML());
    }

}