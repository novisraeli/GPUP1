package engine;

import generated.GPUPTarget;
import generated.GPUPTargets;
import information.Information;
import target.Target;
import target.Targets;
import xml.Xmlimpl;

import java.util.HashMap;
import java.util.Map;

public class engineImpl implements engine{

    private Targets t;
    @Override
    public void loadFile(String path) throws Exception {
        try {
            Xmlimpl file = new Xmlimpl(path);
            file.checkXmlFile();
            Map <String , Target > bar = new HashMap<>();
            for (GPUPTarget t:file.Targets111.getGPUPTargets().getGPUPTarget())
            {
                Target Tnew = new Target(t.getName() , t.getGPUPUserData());
                bar.put(Tnew.getName() , Tnew);
            }

            System.out.println(bar);
        }
        catch (Exception ex)
        {throw  ex;}
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
