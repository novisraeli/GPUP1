package xml;

public class XmlNotLoad extends Exception{
    private  String messageFailed;
    public XmlNotLoad(){
        messageFailed = "Can not do this task, no file uploaded";
    }

    @Override
    public String toString() {
        return  messageFailed;
    }

}
