package engine;

import target.TargetException;
import task.TaskException;
import xml.XmlException;

public interface engine{

    public void loadFile(String path) throws Exception;
    public void targetsInformation() throws Exception;
    public void specificTargetInformation(String name) throws Exception;
    public void FindAPathBetweenTwoTargets(String t1,String t2) throws Exception;
    public void runTask(float time,boolean random,float success,float warning) throws Exception;
    public void exit();


}
