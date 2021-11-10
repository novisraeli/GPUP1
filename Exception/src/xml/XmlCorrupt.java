package xml;

public class XmlCorrupt extends XmlException { // the xml file is corrupt -

    private String message;

    public XmlCorrupt(String name ,String messageFailed){
        super(name);
        message = messageFailed;
    }

    @Override
    public void printXmlNameException(){
        super.printXmlNameException();
        System.out.println(message + "\n\r");
    }
}
