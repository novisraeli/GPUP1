package engine;

import information.Information;
import target.Targets;
import java.util.*;

import java.util.List;

public interface engine{
    enum Dependence{DEPENDS_ON , REQUIRED_FOR}
    void loadFile(String path) throws Exception;
    Information targetsInFormation() throws Exception ;
    Information specificTargetInformation(String name) throws Exception;
    Information findAPathBetweenTwoTargets(String t1,String t2 , Dependence d) throws Exception;
    List<Information> runTask(int time, boolean random, float success, float warning,boolean keepLastRun) throws Exception;
    Information circuitDetection(String name)throws Exception;
    void writeTargetsAndInformationToTextFile(String path)throws Exception;
    void readTargetsAndInformationToTextFile(String path) throws Exception;
    void exit();




}
