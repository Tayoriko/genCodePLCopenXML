package devPLC;

import databases.GData;
import generation.Xml;

public abstract class CodesysGenAbstract {

    public CodesysGenAbstract() {
    }

    // Метод для генерации тега <documentation>
    protected StringBuilder generateTagDocumentation(String documentation) {
        return Xml.addTab(
                Xml.genTag(
                        eCtags.doc.getTag(),
                        Xml.addTab(
                                Xml.genLineOneCloseContext(
                                        eCtags.xhtml.getTag(),
                                        eCtags.xmlns.getTag(),
                                        GData.codesysXmlns,
                                        documentation))));
    }

    // Метод для создания тега <XHTML>
    protected StringBuilder generateTagXhtml(StringBuilder content) {
        return Xml.addTab(
                Xml.genTagOne(
                        eCtags.xhtml.getTag(),
                        eCtags.xmlns.getTag(),
                        GData.codesysXmlns,
                        content));
    }

    // Метод для создания тега <addData>
    protected StringBuilder generateTagAddData(StringBuilder content) {
        return Xml.addTab(
                Xml.genTag(
                        eCtags.addData.getTag(),
                        content));
    }

    protected StringBuilder generateTagData(String data, String handle, StringBuilder content) {
        return Xml.addTab(
                Xml.genTagTwo(
                        eCtags.data.getTag(),
                        eCtags.name.getTag(),
                        GData.codesysPlcOpenLink + data,
                        eCtags.handleUnknown.getTag(),
                        handle,
                        content));
    }

    protected StringBuilder generateTagAttributes(StringBuilder content) {
        return Xml.addTab(
                Xml.genTag(
                        eCtags.attrs.getTag(),
                        content));
    }

    protected StringBuilder generateTagAttribute(String name, String value) {
        return Xml.addTab(
                Xml.genLineTwo(
                        eCtags.attrs.getTag(),
                        eCtags.name.getTag(),
                        name,
                        eCtags.value.getTag(),
                        value));
    }

    protected StringBuilder generateTagObjectId(String id) {
        return Xml.addTab(
                Xml.genTag(
                        eCtags.objectId.getTag(),
                        new StringBuilder(id)));
    }


}
