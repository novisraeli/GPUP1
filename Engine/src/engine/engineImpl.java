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
    public void specificTargetInformation() throws Exception {

    }

    @Override
    public void FindAPathBetweenTwoTargets() throws Exception {

    }

    @Override
    public void runTask() throws Exception {

    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
