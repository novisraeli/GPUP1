package xml;
/// send error message if there is an error with the xml file
public class XmlException extends Exception{

    private String xmlName;

    public XmlException(String name){
        xmlName = name;
    }

    @Override
    public String toString() {
        return "Exception with XML file:" + xmlName + "\n\r";

    }
}
