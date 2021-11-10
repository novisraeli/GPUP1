package xml;

public class XmlCorrupt extends XmlException { // the xml file is corrupt -

    private String message;

    public XmlCorrupt(String name ,String messageFailed){
        super(name);
        message = messageFailed;
    }

    @Override
    public String toString() {
        System.out.println (super.toString());
        System.out.println(message + "\n\r");
        return null;
    }

}
