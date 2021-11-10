package engine;

import target.TargetException;
import task.TaskException;
import xml.XmlException;

public interface engine{

    public void loadFile(String path) throws Exception;
    public void targetsInformation() throws Exception;
    public void specificTargetInformation() throws Exception;
    public void FindAPathBetweenTwoTargets() throws Exception;
    public void runTask() throws Exception;
    public void exit();


}
