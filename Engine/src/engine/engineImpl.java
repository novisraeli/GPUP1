package engine;

import information.Information;
import target.Targets;
import xml.Xmlimpl;

public class engineImpl implements engine{

    private Targets t;
    @Override
    public void loadFile(String path) throws Exception {
        try {
            Xmlimpl file = new Xmlimpl(path);
            file.checkXmlFile();

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
