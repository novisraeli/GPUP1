package engine;

import org.w3c.dom.Document;
import target.TargetException;
import task.TaskException;
import xml.Xml;
import xml.XmlException;
import xml.Xmlimpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class engineImpl implements engine{

    @Override
    public void loadFile(String path) throws Exception {
        try {
            Xmlimpl file = new Xmlimpl(path);
            file.checkXmlFile();
        }
        catch (Exception ex)
        {throw  ex;}
    }

    @Override
    public void targetsInformation() throws Exception {

    }

    @Override
    public void specificTargetInformation() throws Exception {

    }

    @Override
    public void FindAPathBetweenTwoTargets() throws Exception {

    }

    @Override
    public void runTask() throws Exception {

    }

    @Override
    public void exit() {

    }
}
