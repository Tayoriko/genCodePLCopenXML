package genXML;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenBodyXMLTest {

    GenBodyXML genBodyXML = new GenBodyXML();

    @Test
    void getXML() {
        System.out.println(genBodyXML.getXML());
    }
}