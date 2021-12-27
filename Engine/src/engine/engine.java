package engine;

import information.GraphInformation;
import information.Information;
import target.Target;
import target.Targets;
import java.util.*;

import java.util.List;

public interface engine{
    enum Dependence{DEPENDS_ON , REQUIRED_FOR}

    Map<String, Target> getMap();
    public void whatIf(String t1, List<String>  newList, Dependence dependence);
    void loadFile(String path) throws Exception;
    GraphInformation targetsInFormation() throws Exception ;
    Information specificTargetInformation(String name) throws Exception;
    Information findAPathBetweenTwoTargets(String t1,String t2 , Dependence d) throws Exception;
    List<Information> runTask(int time, boolean random, float success, float warning,boolean keepLastRun) throws Exception;
    Information circuitDetection(String name)throws Exception;
    void writeTargetsAndInformationToTextFile(String path)throws Exception;
    void readTargetsAndInformationToTextFile(String path) throws Exception;
    boolean ifRunTask();
    void exit();
    Map<String,Set<String>> getSerialSets();
    Map<String,Set<String>> getAllSerialSetsWithYou(String t);
}
