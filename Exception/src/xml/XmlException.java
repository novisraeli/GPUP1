package xml;

public class XmlException extends Exception{

    private String xmlName;

    public XmlException(String name){
        xmlName = name;
    }
    public void printXmlNameException(){
        System.out.println("Exception with XML file:" + xmlName + "\n\r");
    }
}
