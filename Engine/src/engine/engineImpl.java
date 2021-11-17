package engine;

import information.*;
import target.Target;
import target.TargetIsExists;
import target.Targets;
import xml.Xmlimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class engineImpl implements engine {
    //// member
    private Xmlimpl file;
    private boolean loadFile;
    private Map<String, Target> targetMap;


    /// methods
    @Override
    public boolean ifLoadFile(){return loadFile;}

    @Override
    public void loadFile(String path) throws Exception {/// option 2 in the menu
        Map<String, Target> targetMapTemp;
        try {
            file = new Xmlimpl(path);                       // load XML file
            targetMapTemp = file.makeAMap();// check if the XML file is proper and crate map (key - target name, val - target) from file
            loadFile = true;
            targetMap = targetMapTemp;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void printXml() {
        System.out.println(targetMap);
    } // print all the Xml file

    @Override
    public Information targetsInformation() { /// option 2 in the menu
        int amountOfTargets = (int) targetMap.entrySet().stream().count();          // count all the targets in the map

        int levies = (int) targetMap.entrySet()                                     // count all the levies targets in the map
                .stream()
                .filter(e -> e.getValue().getType().equals(Target.Type.LEAF))
                .count();

        int roots = (int) targetMap.entrySet()                                      // count all the roots targets in the map
                .stream()
                .filter(e -> e.getValue().getType().equals(Target.Type.ROOT))
                .count();

        int middles = (int) targetMap.entrySet()                                    // count all the middles targets in the map
                .stream()
                .filter(e -> e.getValue().getType().equals(Target.Type.MIDDLE))
                .count();

        int independents = (int) targetMap.entrySet()                               // count all the independents targets in the map
                .stream()
                .filter(e -> e.getValue().getType().equals(Target.Type.INDEPENDENTS))
                .count();

        return new GraphInformation(amountOfTargets, levies, middles, roots, independents);
    }

    @Override
    public Information specificTargetInformation(String name) throws Exception {
        Target target = targetMap.get(name);

        if (target == null)                                               // the  target not found in the map
            throw new TargetIsExists(name);
        else {
            Target.Type type = target.getType();
            Set<String> dependsOn = target.getSetDependsOn();
            Set<String> requiredFor = target.getSetRequiredFor();
            String data = target.getUserData();
            return new TargetInformation(name, type, dependsOn, requiredFor, data);
        }
    }

    @Override
    public PathBetweenTwoTargetsInfo findAPathBetweenTwoTargets(String t1, String t2, Dependence d) throws Exception {
        Target target1 = targetMap.get(t1);
        Target target2 = targetMap.get(t2);

        List<Targets> list = new ArrayList<>();
        list.add(new Targets());

       // check if the two targets are in the map
        if (null == target1)
            throw new TargetIsExists(t1);
        if (null == target2)
            throw new TargetIsExists(t2);
        if (d == Dependence.REQUIRED_FOR && target1.getSetRequiredFor().size() != 0)
            findRequiredForPathBetweenTwoTargetsHelper(target1,target2,list,0);
        else if (d == Dependence.DEPENDS_ON && target1.getSetDependsOn().size() != 0)
            findDependsOnPathBetweenTwoTargetsHelper(target1,target2,list,0);

        return  new PathBetweenTwoTargetsInfo(t1 , t2 , d.name() , list);
    }
    public void findRequiredForPathBetweenTwoTargetsHelper(Target t1, Target t2, List<Targets> listSt , int index) throws Exception {
        if (t1.getSetRequiredFor().size() == 0)
            listSt.get(index).getTargetsList().remove(listSt.get(index).getTargetsList().size()-1);
        if (listSt.get(index).getTargetsList().contains(t1.getName())) // handle cycle
            return;
        else {
            if (t1.getSetRequiredFor().contains(t2.getName())) {
                Targets t = new Targets();
                t.getTargetsList().addAll(listSt.get(index).getTargetsList());
                listSt.add(new Targets());
                listSt.get(index).setFind(true);
                index++;
                return;
            }

            for (String st : t1.getSetRequiredFor()) {
                listSt.get(index).getTargetsList().add(st);
                findRequiredForPathBetweenTwoTargetsHelper(targetMap.get(st), t2, listSt, index);
            }
        }
    }
    public void findDependsOnPathBetweenTwoTargetsHelper(Target t1, Target t2, List<Targets> listSt , int index) throws Exception {
        if (t1.getSetDependsOn().size() == 0)
            listSt.get(index).getTargetsList().remove(listSt.get(index).getTargetsList().size()-1);
        if (listSt.get(index).getTargetsList().contains(t1.getName()))  // handle cycle
            return;
        else {
            if (t1.getSetDependsOn().contains(t2.getName())) {
                Targets t = new Targets();
                t.getTargetsList().addAll(listSt.get(index).getTargetsList());
                listSt.add(new Targets());
                listSt.get(index).setFind(true);
                index++;
                return;
            }

            for (String st : t1.getSetDependsOn()) {
                listSt.get(index).getTargetsList().add(st);
                findDependsOnPathBetweenTwoTargetsHelper(targetMap.get(st), t2, listSt, index);
            }
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
    @Override
    public CircuitDetectionInfo circuitDetection(String name)throws Exception {
        PathBetweenTwoTargetsInfo info = findAPathBetweenTwoTargets(name,name,Dependence.DEPENDS_ON);
        return new CircuitDetectionInfo(name , info.getPaths());
    }

}
