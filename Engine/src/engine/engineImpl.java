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
import java.util.concurrent.*;

public class engineImpl implements engine {
    private boolean loadFile = false;
    private Map<String, Target> targetMap;
    private String workingDirectory;
    private List<Information> res;
    private int maxThreads;
    private Map<String,Set<String>> serialSets = new HashMap<>();
    private boolean stopThreads=false;
    private boolean activateThreads=false;
    private static int workingThreads=0;
    private int size;
    private boolean taskRunning = false;
    private ArrayList<infoThread> infoThreadList  = new ArrayList<>();
    private long startTime;

    /**
     * @return list of all the information on target that add to the thread pool
     */
    @Override public ArrayList<infoThread> getInfoThreadList(){return infoThreadList;}

    /**
     * @return working directory path
     */
    @Override public String getWorkingDirectory(){return workingDirectory;}
    /** Load file
     *  Open XML file
     *  if the XML is corrupt stay with the last detail you have
     *  @throws  target.TargetIsExists on if target isn't exists
     *  @throws target.UniqueTarget on if target isn't unique
     *  @throws target.DependsOnConflict on there is conflict between targets
     *  @throws target.RequiredForConflict on there is conflict between targets
     *  @throws xml.XmlIsExists on the XML not exists
     */
    @Override public void loadFile(String path) throws Exception {/// option 2 in the menu
        Map<String, Target> targetMapTemp;
        //// member
        Xmlimpl file = new Xmlimpl(path);                       // load XML file
        targetMapTemp = file.makeAMap();// check if the XML file is proper and crate map (key - target name, val - target) from file
        workingDirectory= file.getWorkingDirectoryXml();
        maxThreads=file.getMaxParallelism();
        serialSets = file.getSerialSets();
        file.checkSerialSets(targetMapTemp,serialSets);
        loadFile = true;
        targetMap = targetMapTemp;
        stopThreads=false;
    }
    /** Targets in formation
     *  @return all the information about the graph:
     *  amount of roots
     *  amount of leaves
     *  amount of middles
     *  amount of independents
     */
    @Override public GraphInformation targetsInFormation() throws Exception{ /// option 2 in the menu
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
    @Override public Information specificTargetInformation(String name) throws Exception {
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
    @Override public PathBetweenTwoTargetsInfo findAPathBetweenTwoTargets(String t1, String t2, Dependence dependence) throws Exception {
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
    private String openDir(String taskType) throws IOException {//doesnt have path yet,this func create directory for simulation task
        Path path=Paths.get(workingDirectory);
        Files.createDirectories(path);
        //File dir = new File(workingDirectory);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.mm.yyyy HH.MM.SS");
        LocalDateTime now = LocalDateTime.now();
        String s =taskType+" "+dtf.format(now);
        s= workingDirectory+"\\"+s;
        Path innerPath=Paths.get(s);
        Files.createDirectories(innerPath);
        //File dir2 = new File(s);
        return s;
    }
    /** Exit
     */
    @Override public void exit() {
        System.exit(0);
    }
    /** Circuit detection info
     *  @return all the information between the targets:
     *  target names
     *  all the cycle paths
     * @throws TargetIsExists on if target isn't exists
     */
    @Override public CircuitDetectionInfo circuitDetection(String name)throws Exception {
        if (!loadFile)
            throw new XmlNotLoad();
        PathBetweenTwoTargetsInfo info = findAPathBetweenTwoTargets(name,name,Dependence.DEPENDS_ON);
        return new CircuitDetectionInfo(name , info.getPaths());
    }
    @Override public Map<String, Target> getMap(){return targetMap;}
    @Override public void whatIf(String target, List<String>  newList, Dependence dependence) {
        Set<String> tOneSet;
        if (!newList.contains(target)) {
            newList.add(target);
            if (Dependence.DEPENDS_ON == dependence)
                tOneSet = targetMap.get(target).getSetDependsOn();
            else
                tOneSet = targetMap.get(target).getSetRequiredFor();

            if (tOneSet.size() != 0) {
                for (String st2 : tOneSet) {
                    whatIf(targetMap.get(st2).getName(), newList, dependence);
                }
            }
        }
    }
    private boolean checkSerialSets(String t){
        for (Set<String> set:serialSets.values()){
            if(set.contains(t)){
                for(String s : set){
                    if(targetMap.get(s).getIsInQueue()&&!s.equals(t)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @Override public Map<String,Set<String>> getAllSerialSetsWithYou(String t){
        Map<String,Set<String>> newSet = new HashMap<>();
        for (String key : serialSets.keySet())
            if(serialSets.get(key).contains(t)) {
                newSet.put(key,serialSets.get(key));
            }
        return newSet;
    }
    @Override public Map<String,Set<String>> getSerialSets(){
        return serialSets;
    }

/// file - write and read
    @Override public void writeTargetsAndInformationToTextFile(String path)throws Exception{
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
    @Override public void readTargetsAndInformationToTextFile(String path) throws Exception {
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

/// task
    public synchronized void setStopThreads(boolean b){
        stopThreads=b;
    }
    @Override public double getPrecentageDone(){
        int count=0;
        int doneCount=0;
        for(Map.Entry<String, Target> e : targetMap.entrySet()){
            if(!e.getValue().getNotSelected()){
                if(e.getValue().getStatus() != Target.Status.Waiting && e.getValue().getStatus() != Target.Status.Frozen){
                    doneCount++;
                }
            }
        }
        return ((double)doneCount)/size;
    }
    public static synchronized int getWorkingThreads(){
        return workingThreads;
    }
    public static synchronized void incrementWorkingThreads(){
        workingThreads++;
    }
    public static synchronized void decrementWorkingThreads(){
        workingThreads--;
    }
    private synchronized void setToWaiting() {
        boolean check=true;
        for (Map.Entry<String, Target> e : targetMap.entrySet()) {
            if(!e.getValue().getNotSelected()) {
                for (String s : e.getValue().getSetDependsOn()) {
                    if (!targetMap.get(s).getNotSelected() && (targetMap.get(s).getStatus() == Target.Status.Waiting || targetMap.get(s).getStatus() == Target.Status.Frozen)) {
                        check = false;
                    }
                }
            }
            else{
                check=false;
            }
            if(check){
                e.getValue().setStartWaitingTime(System.currentTimeMillis());
                e.getValue().SetStatus(Target.Status.Waiting);
            }
            check=true;
        }
    }
    @Override public synchronized void compile(boolean keepLastRun,String taskType,
                                               int threadsNum,List<Target> targets
                                                ,String dest,String src) throws Exception {
        infoThreadList.clear();
        startTime = System.currentTimeMillis();
        taskRunning = true;
        size = targets.size();
        activateThreads = false;
        stopThreads = false;
        if (!loadFile)
            throw new XmlNotLoad();
        String path=openDir(taskType);
        if(!keepLastRun){
            for(Map.Entry<String, Target> e : targetMap.entrySet()){//set all targets to waiting or frozen
                if(targets.contains(e.getValue())) {
                    if (e.getValue().getType() == Target.Type.LEAF || e.getValue().getType() == Target.Type.INDEPENDENTS) {
                        e.getValue().SetStatus(Target.Status.Waiting);
                        e.getValue().setStartWaitingTime(System.currentTimeMillis());
                    }
                    else {
                        e.getValue().SetStatus(Target.Status.Frozen);
                    }
                    e.getValue().setNotSelected(false);
                }
                else{
                    e.getValue().SetStatus(Target.Status.Frozen);
                    e.getValue().setNotSelected(true);
                }
            }
        }
        else{
            for(Map.Entry<String, Target> e : targetMap.entrySet()){//set all failed or skipped targets to waiting
                if(targets.contains(e.getValue())) {
                    if (e.getValue().getStatus() == Target.Status.Failure) {
                        e.getValue().SetStatus(Target.Status.Waiting);
                        e.getValue().setStartWaitingTime(System.currentTimeMillis());
                    } else if (e.getValue().getStatus() == Target.Status.Skipped) {
                        e.getValue().SetStatus(Target.Status.Frozen);
                    }
                    e.getValue().setNotSelected(false);
                }
                else{
                    e.getValue().setNotSelected(true);
                }
            }
        }
        //now set variables for run
        for(Map.Entry<String, Target> e : targetMap.entrySet()){
            e.getValue().setPath(path);
            e.getValue().setMap(targetMap);
            e.getValue().setCompile(true);
            e.getValue().setCompileDest(dest);
            e.getValue().setSource(src);
        }
        setToWaiting();
        run(threadsNum);

    }
    @Override public synchronized void taskSetUp(int time, boolean random, float success,
                                                 float warning,boolean keepLastRun,String taskType,
                                                 int threadsNum,List<Target> targets) throws Exception {
        infoThreadList.clear();
        startTime = System.currentTimeMillis();
        taskRunning = true;
        size = targets.size();
        activateThreads = false;
        stopThreads = false;

        int randomTime=0;
        if (!loadFile)
            throw new XmlNotLoad();
        String path=openDir(taskType);
        if(!keepLastRun){
            for(Map.Entry<String, Target> e : targetMap.entrySet()){//set all targets to waiting or frozen
                if(targets.contains(e.getValue())) {
                    if (e.getValue().getType() == Target.Type.LEAF || e.getValue().getType() == Target.Type.INDEPENDENTS) {
                        e.getValue().SetStatus(Target.Status.Waiting);
                        e.getValue().setStartWaitingTime(System.currentTimeMillis());
                    }
                    else {
                        e.getValue().SetStatus(Target.Status.Frozen);
                    }
                    e.getValue().setNotSelected(false);
                }
                else{
                    e.getValue().SetStatus(Target.Status.Frozen);
                    e.getValue().setNotSelected(true);
                }
            }
        }
        else{
            for(Map.Entry<String, Target> e : targetMap.entrySet()){//set all failed or skipped targets to waiting
                if(targets.contains(e.getValue())) {
                    if (e.getValue().getStatus() == Target.Status.Failure) {
                        e.getValue().SetStatus(Target.Status.Waiting);
                        e.getValue().setStartWaitingTime(System.currentTimeMillis());
                    } else if (e.getValue().getStatus() == Target.Status.Skipped) {
                        e.getValue().SetStatus(Target.Status.Frozen);
                    }
                    e.getValue().setNotSelected(false);
                }
                else{
                    e.getValue().setNotSelected(true);
                }
            }
        }
        Random r = new Random();
        //now set variables for run
        for(Map.Entry<String, Target> e : targetMap.entrySet()){
            if(random)
                randomTime=r.nextInt(time)+1;
            else
                randomTime = time;

            e.getValue().setRunTime(randomTime);
            e.getValue().setSuccessChance(success);
            e.getValue().setWarningChance(warning);
            e.getValue().setPath(path);
            e.getValue().setMap(targetMap);
        }
        setToWaiting();
        run(threadsNum);

    }
    private synchronized void run(int threadsNum)throws Exception{
        //set up thread pool
        List<Runnable>t=new ArrayList<>();
        ExecutorService threads = Executors.newFixedThreadPool(threadsNum);
        while(!taskDoneCheck()) {
            //might not work didnt check yet
            if(stopThreads&&!activateThreads){
                System.out.println("before wait");
                threads.shutdown();
                threads.awaitTermination(1, TimeUnit.NANOSECONDS);
                t=threads.shutdownNow();

                System.out.println("after wait");
                stopThreads=false;
                taskRunning = false;
                System.out.println(threads.isShutdown());
            }
            else if(activateThreads&&!stopThreads){
                System.out.println("here");
                threads=Executors.newFixedThreadPool(threadsNum);
                for(Runnable tar:t){

                    threads.execute((Target)tar);
                }
                activateThreads=false;
                taskRunning = true;
            }

            for(Map.Entry<String, Target> e : targetMap.entrySet()){
                if(e.getValue().getStatus()==Target.Status.Waiting&&!e.getValue().getIsInQueue()) {
                    if (checkSerialSets(e.getValue().getName())) {
                        e.getValue().setIsInQueue(true);
                        threads.execute(e.getValue());
                        infoThreadList.add(new infoThread(infoThread.InOrOut.IN, startTime ,getTimeFromStart() ,threadsNum-workingThreads));
                    }
                }
            }
        }
        //if we got here the there is not more waiting targets
        taskRunning = false;
        threads.shutdown();
    }
    public String getTimeFromStart(){
        long temp= System.currentTimeMillis()-startTime;
        long millis = temp % 1000;
        long second = (temp / 1000) % 60;
        long minute = (temp / (1000 * 60)) % 60;
        long hour = (temp / (1000 * 60 * 60)) % 24;
        String waitingTime = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        return waitingTime;
    }
    @Override public boolean getIsTaskRunning(){return taskRunning;}

    @Override public void stopThreads() {
        stopThreads=true;
        activateThreads =false;
    }
    @Override public void activateThreads(){
        stopThreads=false;
        activateThreads =true;
    }
    @Override public boolean ifRunTask(){
        return res==null;
    }
    @Override public int getMaxThreads(){
        return maxThreads;
    }
    private boolean taskDoneCheck(){
        for(Map.Entry<String, Target> e : targetMap.entrySet()){
            if((e.getValue().getStatus()== Target.Status.Waiting||e.getValue().getStatus()== Target.Status.Frozen)&&!e.getValue().getNotSelected()) {
                return false;
            }
        }
        return true;
    }
}
