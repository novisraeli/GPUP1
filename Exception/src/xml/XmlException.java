package xml;

public class XmlException extends Exception{

    private String xmlName;

    public XmlException(String name){
        xmlName = name;
    }

    @Override
    public String toString() {
        System.out.println("Exception with XML file:" + xmlName + "\n\r");
        return null;
    }
}
