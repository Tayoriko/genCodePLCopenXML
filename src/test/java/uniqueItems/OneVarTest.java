package uniqueItems;

import enumerations.eVarAllocate;
import enumerations.eVarList;
import enumerations.eVarType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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
    void toXML() throws TransformerException, ParserConfigurationException, IOException, SAXException {
        String[] varName = {"globalEmergency","BOOL","Emergency stop"};
        OneVar var = new OneVar(varName);
        System.out.println(var.toXML());
    }

    @Test
    void setAddress() {
        String[] varName = {"globalEmergency","BOOL","Emergency stop"};
        OneVar var = new OneVar(varName);
        var.setAddress(eVarAllocate.OUT,"3.14");
        System.out.println(var);
        Assertions.assertEquals("%QX3.14", var.getAddress());
        System.out.println(var.toXML());
    }
}