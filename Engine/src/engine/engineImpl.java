package engine;

import generated.GPUPTarget;
import information.Information;
import target.Target;
import xml.Xmlimpl;

import java.util.HashMap;
import java.util.Map;

public class engineImpl implements engine{
    private Xmlimpl file;
    private Map<String, Target> targetMap;

    @Override
    public void loadFile(String path) throws Exception {
        try {
            file = new Xmlimpl(path);
            file.checkXmlFile();
            targetMap = file.makeAMap();
        }
        catch (Exception ex)
        {throw  ex;}
    }

    public void printXml(){
        System.out.println(targetMap);
    }

    @Override
    public Information targetsInformation() throws Exception {

        return null;
    }

    @Override
    public Information specificTargetInformation(String name) throws Exception {
        return null;
    }

    @Override
    public Information FindAPathBetweenTwoTargets(String t1, String t2) throws Exception {
        return null;
    }

    @Override
    public Information runTask(float time, boolean random, float success, float warning) throws Exception {
        return null;
    }

    @Override
    public void exit() {

    }
}
