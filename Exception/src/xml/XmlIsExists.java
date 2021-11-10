package xml;

public class XmlIsExists extends XmlException {

    public XmlIsExists(String name){
        super(name);
    }

    @Override
    public String toString(){
        return super.toString() + "The XML file not exists \n\r";

    }
}
