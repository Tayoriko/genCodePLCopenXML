package genXML;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenFileHeaderTest {

    GenFileHeader genFileHeader = new GenFileHeader();
    @Test
    void genFileHeader() {
        System.out.println(genFileHeader.genFileHeader(1));
    }
}