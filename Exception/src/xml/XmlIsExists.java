package xml;

public class XmlIsExists extends XmlException {

    public XmlIsExists(String name){
        super(name);
    }

    @Override
    public void printXmlNameException(){
        super.printXmlNameException();
        System.out.println("The XML file not exists \n\r");
    }
}
