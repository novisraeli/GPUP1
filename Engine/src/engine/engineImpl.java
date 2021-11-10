package engine;

import xml.Xmlimpl;

public class engineImpl implements engine{

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
    public void targetsInformation() throws Exception {

    }

    @Override
    public void specificTargetInformation(String name) throws Exception {

    }

    @Override
    public void FindAPathBetweenTwoTargets(String t1,String t2) throws Exception {

    }

    @Override
    public void runTask(float time,boolean random,float success,float warning) throws Exception{

    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
