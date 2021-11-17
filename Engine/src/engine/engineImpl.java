package engine;

import information.*;
import target.Target;
import target.TargetIsExists;
import target.Targets;
import xml.Xmlimpl;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class engineImpl implements engine {
    private boolean loadFile;
    private Map<String, Target> targetMap;
    private String workingDirectory;


    /// methods
    @Override
    public boolean ifLoadFile(){return loadFile;}

    @Override
    public void loadFile(String path) throws Exception {/// option 2 in the menu
        Map<String, Target> targetMapTemp;
        //// member
        Xmlimpl file = new Xmlimpl(path);                       // load XML file
        targetMapTemp = file.makeAMap();// check if the XML file is proper and crate map (key - target name, val - target) from file
        workingDirectory = file.getWorkingDirectoryXml();
        loadFile = true;
        targetMap = targetMapTemp;
    }

    public void printXml() {
        System.out.println(targetMap);
    } // print all the Xml file

    @Override
    public Information targetsInformation() { /// option 2 in the menu
        int amountOfTargets = targetMap.entrySet().size();          // count all the targets in the map

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
    public PathBetweenTwoTargetsInfo findAPathBetweenTwoTargets(String t1, String t2, Dependence dependence) throws Exception {
        Target target1 = targetMap.get(t1);
        Target target2 = targetMap.get(t2);
        IntX i = new IntX();
        i.x = 0;

        List<Targets> list = new ArrayList<>();
        list.add(new Targets());

       // check if the two targets are in the map
        if (null == target1)
            throw new TargetIsExists(t1);
        if (null == target2)
            throw new TargetIsExists(t2);

        if (dependence == Dependence.REQUIRED_FOR && target1.getSetRequiredFor().size() != 0)
            findPathBetweenTwoTargetsHelper(target1,target2,list,i, dependence);
        else if (dependence == Dependence.DEPENDS_ON && target1.getSetDependsOn().size() != 0)
            findPathBetweenTwoTargetsHelper(target1,target2,list,i , dependence);

        return  new PathBetweenTwoTargetsInfo(t1 , t2 , dependence.name() , list);
    }
    public void findPathBetweenTwoTargetsHelper(Target t1, Target t2, List<Targets> listSt , IntX index , Dependence dependence) {

        Set<String> tOneSet;

        if (Dependence.DEPENDS_ON == dependence)
            tOneSet = t1.getSetDependsOn();
        else
            tOneSet = t1.getSetRequiredFor();


        if (tOneSet.size() == 0) {
            listSt.get(index.x).getTargetsList().remove(listSt.get(index.x).getTargetsList().size() - 1);
        }

        else {
            for (String st : tOneSet) {  /// search in all DEPENDS_ON or REQUIRED_FOR for each target
                if (st.equals(t2.getName())) {
                    listSt.add(new Targets()); /// the next list
                    listSt.get(index.x).setFind(true); // find
                    listSt.get(listSt.size()-1).getTargetsList().addAll(listSt.get(index.x).getTargetsList());
                    ++index.x;
                }

                else if (! listSt.get(index.x).getTargetsList().contains(st)) { /// skip on direct dependency and handle cycle
                    listSt.get(index.x).getTargetsList().add(st);
                    findPathBetweenTwoTargetsHelper(targetMap.get(st), t2, listSt, index , dependence);
                }
            }

            if ( listSt.get(index.x).getTargetsList().size() != 0 )
                listSt.get(index.x).getTargetsList().remove(listSt.get(index.x).getTargetsList().size()-1);
        }
    }

    @Override
    public List<Information> runTask(int time, boolean random, float success, float warning,boolean keepLastRun) throws Exception {
        boolean done=false;
        List<Information>res=new ArrayList<Information>();
        openDir();
        if(!keepLastRun){
            for(Map.Entry<String, Target> e : targetMap.entrySet()){
                e.getValue().SetStatus(Target.Status.Waiting);
            }

        }
        while(!done) {
            for (Map.Entry<String, Target> e : targetMap.entrySet()) {
                e.getValue().run(time,random,success,warning,res);
            }
            done=taskDoneCheck();
        }
        return res;
    }
    private void openDir() throws IOException {//doesnt have path yet,this func create directory for simulation task
        Path path=Paths.get(workingDirectory);
        Files.createDirectories(path);
        File dir = new File(workingDirectory);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.mm.yyyy HH.MM.SS");
        LocalDateTime now = LocalDateTime.now();
        String s ="Simulation "+dtf.format(now);
       // dir.renameTo(s);
    }
    private boolean taskDoneCheck(){
        for(Map.Entry<String, Target> e : targetMap.entrySet()){
            if(e.getValue().getStatus().equals(Target.Status.Waiting));
            return false;
        }
        return true;
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
