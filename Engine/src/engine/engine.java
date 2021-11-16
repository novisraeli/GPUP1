package engine;

import information.Information;

import java.util.List;

public interface engine{
    enum Depend{DEPENDS_ON , REQUIRED_FOR}
    void loadFile(String path) throws Exception;
    void printXml();
    Information targetsInformation();
    Information specificTargetInformation(String name) throws Exception;
    Information FindAPathBetweenTwoTargets(String t1,String t2 , Depend d) throws Exception;
    Information runTask(float time,boolean random,float success,float warning) throws Exception;
    List<String> circuitDetection(String name)throws Exception;

    void exit();




}
