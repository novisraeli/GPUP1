package engine;

import generated.GPUPTarget;
import information.GraphInformation;
import information.Information;
import information.PathBetweenTwoTargetsInfo;
import information.TargetInformation;
import target.Target;
import target.TargetIsExists;
import target.Type;
import xml.Xmlimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


enum Depend{DEPENDS_ON , REQUIRED_FOR}

public class engineImpl implements engine {
    private Xmlimpl file;
    private Map<String, Target> targetMap;

    @Override
    public void loadFile(String path) throws Exception {
        try {
            file = new Xmlimpl(path);
            file.checkXmlFile();
            targetMap = file.makeAMap();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void printXml() {
        System.out.println(targetMap);
    }

    @Override
    public Information targetsInformation() {
        int amountOfTargets = (int) targetMap.entrySet().stream().count();
        int levies = (int) targetMap.entrySet().stream().filter(e -> e.getValue().getType().equals(Type.LEAF)).count();
        int roots = (int) targetMap.entrySet().stream().filter(e -> e.getValue().getType().equals(Type.ROOT)).count();
        int middles = (int) targetMap.entrySet().stream().filter(e -> e.getValue().getType().equals(Type.MIDDLE)).count();
        int independents = (int) targetMap.entrySet().stream().filter(e -> e.getValue().getType().equals(Type.INDEPENDENTS)).count();
        return new GraphInformation(amountOfTargets, levies, middles, roots, independents);
    }

    @Override
    public Information specificTargetInformation(String name) throws Exception {
        Target target = targetMap.get(name);
        if (target == null)
            throw new TargetIsExists(name);
        else {
            Type type = target.getType();
            Set<String> dependsOn = target.getSetDependsOn();
            Set<String> requiredFor = target.getSetRequiredFor();
            String data = target.getUserData();
            return new TargetInformation(name, type, dependsOn, requiredFor, data);
        }
    }

    @Override

    public Information FindAPathBetweenTwoTargets(String t1, String t2, int i) throws Exception {
        Target target1 = targetMap.get(t1);
        Target target2 = targetMap.get(t2);
        List<String> list = new ArrayList<>();
        if (null == target1)
            throw new TargetIsExists(t1);
        if (null == target2)
            throw new TargetIsExists(t2);

        Depend d = Depend.DEPENDS_ON;

        if (FindAPathBetweenTwoTargetsHelper(target1,target2,d,list))
            return new PathBetweenTwoTargetsInfo(t1 , t2 , d.name() , list);
        else
        {
            return new PathBetweenTwoTargetsInfo("no" , "no" , d.name() , list);
        }



    }

    @Override
    public Information runTask(float time, boolean random, float success, float warning) throws Exception {
        return null;
    }

    @Override
    public void exit() {
        System.exit(0);
    }


    public boolean FindAPathBetweenTwoTargetsHelper(Target t1, Target t2, Depend d, List<String> listSt) throws Exception {
        if (d == Depend.DEPENDS_ON) {
            if (t1.getSetDependsOn().size() == 0)
                return false;
            if (t1.getSetDependsOn().contains(t2.getName()))
                return true;
        }

        if (d == Depend.REQUIRED_FOR) {
            if (t1.getSetRequiredFor().size() == 0)
                return false;
            if (t1.getSetRequiredFor().contains(t2.getName()))
                return true;
        }

        for (String st : t1.getSetDependsOn()) {
            if (FindAPathBetweenTwoTargetsHelper(targetMap.get(st), t2, d, listSt)) {
                listSt.add(st);
                return true;
            }
        }
        return false;
    }
}
