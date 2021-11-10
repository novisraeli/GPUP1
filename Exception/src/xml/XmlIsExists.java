package xml;

public class XmlIsExists extends XmlException {

    public XmlIsExists(String name){
        super(name);
    }

    @Override
    public String toString(){
        System.out.println (super.toString());
        System.out.println("The XML file not exists \n\r");
        return null;
    }
}
