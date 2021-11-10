package xml;

import generated.GPUPConfiguration;
import generated.GPUPDescriptor;
import generated.GPUPTargets;
import org.w3c.dom.Document;
import java.io.FileInputStream;
import java.io.InputStream;
import target.Targets;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



public class Xmlimpl implements Xml{

    public GPUPDescriptor Targets111;
    private final static String JAXB_XML_PACKAGE_NAME = "generated";

    public Xmlimpl(String path) throws Exception {

        try {
            InputStream inputStream = new FileInputStream(new File(path));
            Targets111 = deserializeFrom(inputStream);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        catch (Exception ex) {
            throw new XmlIsExists(path);
        }
    }


    public void checkXmlFile() throws Exception {
        /// check if the file is contsin legal content

    }

    private static GPUPDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (GPUPDescriptor) u.unmarshal(in);
    }
}
