package xml;

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
