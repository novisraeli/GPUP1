package engine;

import information.Information;
import target.TargetException;
import task.TaskException;
import xml.XmlException;

public interface engine{

    public void loadFile(String path) throws Exception;
    public void printXml();
    public Information targetsInformation() throws Exception;
    public Information specificTargetInformation(String name) throws Exception;
    public Information FindAPathBetweenTwoTargets(String t1,String t2) throws Exception;
    public Information runTask(float time,boolean random,float success,float warning) throws Exception;
    public void exit();



}
