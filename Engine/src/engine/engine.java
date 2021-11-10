package engine;

import target.TargetException;
import task.TaskException;
import xml.XmlException;

public interface engine{
/*
        System.out.println("1.Load file");
        System.out.println("2.Show info about the targets");
        System.out.println("3.Show info about a specific target");
        System.out.println("4.Find a path between 2 targets");
        System.out.println("5.Run task");
        System.out.println("6.Exit");
*/

    public void loadFile(String path) throws XmlException;
    public void targetsInformation() throws TargetException;
    public void specificTargetInformation() throws TargetException;
    public void FindAPathBetweenTwoTargets() throws TargetException;
    public void runTask() throws TaskException;
    public void exit();


}
