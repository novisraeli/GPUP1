package engine;

import information.Information;
import target.Targets;

import java.util.List;

public interface engine{
    enum Dependence{DEPENDS_ON , REQUIRED_FOR}
    boolean ifLoadFile();
    void loadFile(String path) throws Exception;
    void printXml();
    Information targetsInformation();
    Information specificTargetInformation(String name) throws Exception;
    Information findAPathBetweenTwoTargets(String t1,String t2 , Dependence d) throws Exception;
    Information runTask(float time,boolean random,float success,float warning) throws Exception;
    Information circuitDetection(String name)throws Exception;

    void exit();




}
