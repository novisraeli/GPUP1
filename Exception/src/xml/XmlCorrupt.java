package xml;
/// send error message if the xml file is corrupt
public class XmlCorrupt extends XmlException { // the xml file is corrupt -

    private String message;

    public XmlCorrupt(String name ,String messageFailed){
        super(name);
        message = messageFailed;
    }

    @Override
    public String toString() {
        return  super.toString() + message + "\n\r";
    }

}
