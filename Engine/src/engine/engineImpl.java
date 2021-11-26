package engine;

import information.*;
import target.Target;
import target.TargetIsExists;
import target.Targets;
import xml.XmlNotLoad;
import xml.Xmlimpl;

import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class engineImpl implements engine {
    private boolean loadFile = false;
    private Map<String, Target> targetMap;
    private String workingDirectory;
    private List<Information> res;


    /** Load file
     *  Open XML file
     *  if the XML is corrupt stay with the last detail you have
     *  @throws  target.TargetIsExists on if target isn't exists
     *  @throws target.UniqueTarget on if target isn't unique
     *  @throws target.DependsOnConflict on there is conflict between targets
     *  @throws target.RequiredForConflict on there is conflict between targets
     *  @throws xml.XmlIsExists on the XML not exists
     */
    @Override
    public void loadFile(String path) throws Exception {/// option 2 in the menu
        Map<String, Target> targetMapTemp;
        //// member
        Xmlimpl file = new Xmlimpl(path);                       // load XML file
        targetMapTemp = file.makeAMap();// check if the XML file is proper and crate map (key - target name, val - target) from file
        workingDirectory= file.getWorkingDirectoryXml();
        loadFile = true;
        targetMap = targetMapTemp;
    }

    /** Targets in formation
     *  @return all the information about the graph:
     *  amount of roots
     *  amount of leaves
     *  amount of middles
     *  amount of independents
     */
    @Override
    public Information targetsInFormation() throws Exception{ /// option 2 in the menu
        if (!loadFile)
            throw new XmlNotLoad();
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

    /** Specific target information
     *  @return all the information about the targets:
     *  target name
     *  type (root, middle , leaf , independent)
     *  all the target that depends-on
     *  all the target required-for
     *  user data
     * @throws TargetIsExists on if target isn't exists
     */
    @Override
    public Information specificTargetInformation(String name) throws Exception {
        if (!loadFile)
            throw new XmlNotLoad();
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

    /** Specific target information
     *  @return all the information between the targets:
     *  target names
     *  all the path between the targets with a specific dependence
     * @throws TargetIsExists on if target isn't exists
     */
    @Override
    public PathBetweenTwoTargetsInfo findAPathBetweenTwoTargets(String t1, String t2, Dependence dependence) throws Exception {
        if (!loadFile)
            throw new XmlNotLoad();
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
    /** Specific target information - helper (recursion)
     */
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
        if (!loadFile)
            throw new XmlNotLoad();
        boolean done = false;
       res = new ArrayList<Information>();
        String path=openDir();
        if(!keepLastRun){
            for(Map.Entry<String, Target> e : targetMap.entrySet()){
                e.getValue().SetStatus(Target.Status.Waiting);
            }
        }
        else{
            for(Map.Entry<String, Target> e : targetMap.entrySet()){
                if(e.getValue().getStatus()== Target.Status.Skipped||e.getValue().getStatus()== Target.Status.Failure){
                    e.getValue().SetStatus(Target.Status.Waiting);
                }
            }
        }
        Set<Target>indi=new HashSet<>();
        Set<Target>leavies=new HashSet<>();


        for(Map.Entry<String,Target>e:targetMap.entrySet()){
            if(e.getValue().getType()== Target.Type.INDEPENDENTS){
                indi.add(e.getValue());
            }
            else if(e.getValue().getType()== Target.Type.LEAF){
                leavies.add(e.getValue());
            }
        }
        for(Target t:indi){
            t.run(time,random,success,warning,res,targetMap,path);
        }
        for(Target t:leavies){
            t.run(time,random,success,warning,res,targetMap,path);
        }
        /*
        while(!done) {
            for (Map.Entry<String, Target> e : targetMap.entrySet()) {
                e.getValue().run(time,random,success,warning,res,targetMap,path);
            }
            done=taskDoneCheck();
        }
        */
        return res;
    }
    private String openDir() throws IOException {//doesnt have path yet,this func create directory for simulation task
        Path path=Paths.get(workingDirectory);
        Files.createDirectories(path);
        //File dir = new File(workingDirectory);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.mm.yyyy HH.MM.SS");
        LocalDateTime now = LocalDateTime.now();
        String s ="Simulation "+dtf.format(now);
        s= workingDirectory+"\\"+s;
        Path innerPath=Paths.get(s);
        Files.createDirectories(innerPath);
        //File dir2 = new File(s);
        return s;
    }
    private boolean taskDoneCheck(){
        for(Map.Entry<String, Target> e : targetMap.entrySet()){
            if(e.getValue().getStatus().name().equals(Target.Status.Waiting.name())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void writeTargetsAndInformationToTextFile(String path)throws Exception{
        try{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(targetMap);
            out.writeObject(workingDirectory);
            out.writeObject(res);
            out.flush();
        }
        catch (FileNotFoundException e){throw e;}
        catch (Exception e){throw e;}
    }
    @Override
    public void readTargetsAndInformationToTextFile(String path) throws Exception {
        // Read the array list  from the file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            // we know that we read array list of Persons
            loadFile = true;
            targetMap = (Map<String, Target>) in.readObject();
            workingDirectory = (String)  in.readObject();
            res = (ArrayList<Information>) in.readObject();
        }
        catch (FileNotFoundException e){throw e;}
        catch (Exception e){throw e;}
    }

    /** Exit
     */
    @Override
    public void exit() {
        System.exit(0);
    }

    /** Circuit detection info
     *  @return all the information between the targets:
     *  target names
     *  all the cycle paths
     * @throws TargetIsExists on if target isn't exists
     */
    @Override
    public CircuitDetectionInfo circuitDetection(String name)throws Exception {
        if (!loadFile)
            throw new XmlNotLoad();
        PathBetweenTwoTargetsInfo info = findAPathBetweenTwoTargets(name,name,Dependence.DEPENDS_ON);
        return new CircuitDetectionInfo(name , info.getPaths());
    }
    public boolean ifRunTask(){
        return res==null;
    }
}
