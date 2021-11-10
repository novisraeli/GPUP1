package xml;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Xmlimpl implements Xml{

    Document XmlDoc;

    public Xmlimpl(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream xmlFileInputStream = new FileInputStream(new File(path));
            XmlDoc = builder.parse(xmlFileInputStream);
        }

        catch (Exception ex) {
            throw new XmlIsExists(path);
        }
    }


    @Override
    public void checkXmlFile() throws Exception {
        /// check if the file is contsin legal content

    }
}
