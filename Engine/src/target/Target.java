package target;

import engine.engineImpl;
import information.Information;
import information.SumUpTarget;
import information.infoThread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Target implements Serializable,Runnable
{
    public enum Type {INDEPENDENTS, LEAF, MIDDLE, ROOT}
    public enum Status {Waiting,Success,Warning ,Skipped ,Failure,Frozen}
    private String userData;
    private final String name;
    private Type type;
    private Status status = Status.Frozen;
    private final Set<String> setDependsOn;
    private final Set<String> setRequiredFor;
    private int runTime=0;
    private float successChance=0;
    private float warningChance=0;
    private Map<String, Target> targetMap;
    private String simTimeString;
    private String path;
    private boolean isInQueue;
    private boolean isRunning;
    private long startWaitingTime;
    private String waitingTime;
    private String failReason;
    private boolean notSelected;
    private boolean compile=false;
    private String compileDest;
    private String source;
    private infoThread infor;
    /** ctor */
    public  Target(String name , String userData , Set<String> setDependsOn , Set<String>setRequiredFor) {
        this.name = name;
        this.userData = userData;
        this.setDependsOn = setDependsOn;
        this.setRequiredFor = setRequiredFor;
        this.notSelected = false;
    }

    /** Get name
     * @return name of target
     */
    public String getName() {
        return name;
    }

    /** Get user data
     * @return data of target
     */
    public String getUserData() {
            return userData;
    }

    /** Get all depends-on
     * @return set of depends-on string
     */
    public Set<String> getSetDependsOn() {
        return setDependsOn;
    }

    /** Get required-for
     * @return set of required-for string
     */
    public Set<String> getSetRequiredFor() {
        return setRequiredFor;
    }

    /** Get type
     * @return type of target
     */
    public Type getType() {
        return type;
    }

    /** Get status
     * @return status of target
     */
    public Status getStatus() {
        return status;
    }

    /** Add to set depends-on
     * @param st - target name to add
     */
    public void addToSetDependsOn(String st) {setDependsOn.add(st);}

    /** Add to set required-for
     * @param st - target name to add
     */
    public void addToSetRequiredFor(String st) {setRequiredFor.add(st);}

    /** Set type
     * @param t- new target type
     */
    public void SetType(Type t) {
        this.type = t;
    }

    public void SetUserData(String s){this.userData = s;}

    public String getCompileDest() {
        return compileDest;
    }

    public void setCompileDest(String compileDest) {
        this.compileDest = compileDest;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.compileDest = source;
    }
    public void setMap(Map<String, Target> t){
        targetMap=t;
    }
    public void setSuccessChance(float f){
        successChance=f;
    }
    public void setWarningChance(float f){
        warningChance=f;
    }
    public void setRunTime(int i){
        runTime=i;
    }
    public synchronized boolean getNotSelected(){
        return notSelected;
    }
    public synchronized void setNotSelected(boolean b){
        notSelected =b;
    }

    public String getSimTimeString(){
        return simTimeString;
    }
    public synchronized void setSimTimeString(String s){
        simTimeString=s;
    }
    public void setPath(String s){
        path=s + "\\" + this.name + ".log";
    }
    public String getPath(){
        return path;
    }
    public boolean getIsInQueue() {
        return isInQueue;
    }
    public void setIsInQueue(boolean b){
        isInQueue=b;
    }
    public synchronized boolean getIsRunning(){
        return isRunning;
    }
    public synchronized void setIsRunning(boolean b){
        isRunning=b;
    }
    public void setCompile(boolean b){
        compile=b;
    }
    public boolean getCompile(){
        return compile;
    }
    public synchronized String getWaitingTime(){
        long temp= System.currentTimeMillis()-startWaitingTime;
        long millis = temp % 1000;
        long second = (temp / 1000) % 60;
        long minute = (temp / (1000 * 60)) % 60;
        long hour = (temp / (1000 * 60 * 60)) % 24;
        waitingTime=String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        return waitingTime;
    }
    public synchronized void setWaitingTime(String s){
        waitingTime=s;
    }
    public void setStartWaitingTime(long t){
        startWaitingTime=t;
    }
    /** Set status
     * @param s- new target status
     */
    public void SetStatus(Status s) {
        this.status = s;
    }
    public synchronized String getFailReason(){
        return failReason;
    }
    public synchronized void setFailReason(String s){
        failReason=s;
    }
    public void run(int time, boolean random, float success, float warning, List<Information>res,Map<String,Target>targetMap,String path) throws Exception {
        if(!this.setDependsOn.isEmpty()){
            for(String s:setDependsOn){
                if(targetMap.get(s).getStatus()==Status.Waiting){//dont run if depends on is still waiting
                    return;
                }
            }
        }
        if(status==Status.Success||status==Status.Warning){//if run on already go to inners
            if(!this.setRequiredFor.isEmpty()){
                for(String s:setRequiredFor){
                    targetMap.get(s).moveForward(time,random,success,warning, res,targetMap,path,true);
                }
            }
            return;
        }
        boolean runCheck=true;
        String fName = path + "\\" + this.name + ".log";
        Random r=new Random();
        float successRand;
        float warningRand;
        //here we sim the task
        if(random){//set random time
            time=r.nextInt(time)+1;
        }
        long startTime = System.currentTimeMillis();//sim target and keep time of sim
        Thread.sleep((long)time);
        long simTime=System.currentTimeMillis()-startTime;
        //turn simTime to string
        long millis = simTime % 1000;
        long second = (simTime / 1000) % 60;
        long minute = (simTime / (1000 * 60)) % 60;
        long hour = (simTime / (1000 * 60 * 60)) % 24;
        String simTimeString = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        //sim

        successRand=r.nextFloat();
        warningRand=r.nextFloat();
        if(success>=successRand){
            if(warning>=warningRand){
                this.status=Status.Warning;
            }
            else{
                this.status=Status.Success;
            }
        }
        else{
            runCheck=false;
            this.status=Status.Failure;
        }
        //update
        res.add(new SumUpTarget(this.name,this.status.name(),simTimeString));
        File f = new File(fName);
        f.createNewFile();
        FileWriter w = new FileWriter(fName);
        w.write("Target name: " + this.name + "\n\r" +
                "Target result: " + this.status.name() + "\n\r" +
                "Target time : "+simTimeString +"\n\r");
        w.close();
        //move to inners
        if(!this.setRequiredFor.isEmpty()){
            for(String s:setRequiredFor){
                targetMap.get(s).moveForward(time,random,success,warning, res,targetMap,path,runCheck);
            }
        }
    }
    private void moveForward(int time, boolean random, float success, float warning, List<Information>res,Map<String,Target>targetMap,String path,boolean runCheck) throws Exception {
        String fName=path+"\\"+this.name+".log";
        if (status == Status.Skipped)
            return;
        if(!runCheck ){//print skipped
                File f = new File(fName);
                f.createNewFile();
                FileWriter w = new FileWriter(fName);
                w.write("Target name: " + this.name + "\n\r" +
                        "Target result: " + Status.Skipped.name() + "\n\r" +
                        "Target time : 00:00:00:00 \n\r");
                w.close();
                this.SetStatus(Status.Skipped);
                res.add(new SumUpTarget(this.name, this.status.name(), "00:00:00:00"));
                if (!this.setRequiredFor.isEmpty()) {
                    for (String s : setRequiredFor) {
                        targetMap.get(s).moveForward(time, random, success, warning, res, targetMap, path, runCheck);
                    }
                }

        }
        else{//run on this
            run(time,random,success,warning, res,targetMap,path);
        }
    }
    @Override
    public String toString() {
        return "Target: "+ name + "\n\r"
                + "Target DependsOn:" + setDependsOn + "\n\r"
                + "Target RequiredFor:" + setRequiredFor + "\n\r"
                + "Target type:" + type + "\n\r"
                + "Target Data: "+ userData + "\n\r" ;
    }
    public void run(){
        engineImpl.incrementWorkingThreads();
        isRunning=true;
        try {
            if (compile) {//compile task
                if (!this.setDependsOn.isEmpty()) {
                    for (String s : setDependsOn) {
                        if ((targetMap.get(s).getStatus() == Status.Waiting || targetMap.get(s).getStatus() == Status.Frozen) && !targetMap.get(s).getNotSelected()) {//dont run if depends on is still waiting
                            isRunning = false;
                            isInQueue = false;
                            engineImpl.decrementWorkingThreads();
                            return;
                        } else if (targetMap.get(s).getStatus() == Status.Failure || targetMap.get(s).getStatus() == Status.Skipped) {
                            if (targetMap.get(s).getStatus() == Status.Failure) {
                                failReason = s;
                            } else {
                                failReason = targetMap.get(s).failReason;
                            }
                            isInQueue = false;
                            isRunning = false;
                            this.status = Status.Skipped;
                            simTimeString = "00:00:00:00";
                            engineImpl.decrementWorkingThreads();
                            File f = new File(path);
                            f.createNewFile();
                            FileWriter w = new FileWriter(path);
                            w.write("Target name: " + this.name + "\n\r" +
                                    "Target result: " + this.status.name() + "\n\r" +
                                    "Target time : 00:00:00:00 \n\r");
                            w.close();
                            this.SetStatus(Status.Skipped);
                            return;
                        }
                    }
                }
                long startTime = System.currentTimeMillis();//sim target and keep time of sim
                Runtime rt = Runtime.getRuntime();
                String[] strings = {"javac", "-d", compileDest, "-cp", compileDest, source+"/"+this.name};
                Process p = rt.exec(strings);
                p.waitFor();
                System.out.println("done");
                int res=p.exitValue();
                long simTime = System.currentTimeMillis() - startTime;
                //turn simTime to string
                long millis = simTime % 1000;
                long second = (simTime / 1000) % 60;
                long minute = (simTime / (1000 * 60)) % 60;
                long hour = (simTime / (1000 * 60 * 60)) % 24;
                simTimeString = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
                if(res!=0){//compilation failed
                    this.status=Status.Failure;
                }
                else{//success
                       this.status=Status.Success;
                }
                if (!this.setRequiredFor.isEmpty()) {
                    for (String s : setRequiredFor) {
                        if (targetMap.get(s).status == Status.Frozen)
                            targetMap.get(s).status = Status.Waiting;
                    }
                }
            }

            else {//simulation task
                if (!this.setDependsOn.isEmpty()) {
                    for (String s : setDependsOn) {
                        if ((targetMap.get(s).getStatus() == Status.Waiting || targetMap.get(s).getStatus() == Status.Frozen) && !targetMap.get(s).getNotSelected()) {//dont run if depends on is still waiting
                            isRunning = false;
                            isInQueue = false;
                            engineImpl.decrementWorkingThreads();
                            return;
                        } else if (targetMap.get(s).getStatus() == Status.Failure || targetMap.get(s).getStatus() == Status.Skipped) {
                            if (targetMap.get(s).getStatus() == Status.Failure) {
                                failReason = s;
                            } else {
                                failReason = targetMap.get(s).failReason;
                            }
                            isInQueue = false;
                            isRunning = false;
                            this.status = Status.Skipped;
                            simTimeString = "00:00:00:00";
                            engineImpl.decrementWorkingThreads();
                            File f = new File(path);
                            f.createNewFile();
                            FileWriter w = new FileWriter(path);
                            w.write("Target name: " + this.name + "\n\r" +
                                    "Target result: " + this.status.name() + "\n\r" +
                                    "Target time : 00:00:00:00 \n\r");
                            w.close();
                            this.SetStatus(Status.Skipped);
                            return;
                        }
                    }
                }
                long startTime = System.currentTimeMillis();//sim target and keep time of sim
                Thread.sleep((long) runTime);
                long simTime = System.currentTimeMillis() - startTime;
                //turn simTime to string
                long millis = simTime % 1000;
                long second = (simTime / 1000) % 60;
                long minute = (simTime / (1000 * 60)) % 60;
                long hour = (simTime / (1000 * 60 * 60)) % 24;
                simTimeString = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
                Random r = new Random();
                float successRand = r.nextFloat();
                float warningRand = r.nextFloat();
                if (successChance >= successRand) {
                    if (warningChance >= warningRand) {
                        this.status = Status.Warning;
                    } else {
                        this.status = Status.Success;
                    }
                } else {
                    this.status = Status.Failure;
                }
                if (!this.setRequiredFor.isEmpty()) {
                    for (String s : setRequiredFor) {
                        if (targetMap.get(s).status == Status.Frozen)
                            targetMap.get(s).status = Status.Waiting;
                    }
                }
            }
            File f = new File(path);
            f.createNewFile();
            FileWriter w = new FileWriter(path);
            w.write("Target name: " + this.name + "\n\r" +
                    "Target result: " + this.status.name() + "\n\r" +
                    "Target time :  \n\r");
            w.close();
            isRunning = false;
            isInQueue = false;
            engineImpl.decrementWorkingThreads();

            infor = new infoThread(infoThread.InOrOut.OUT, System.currentTimeMillis() , engineImpl.getWorkingThreads() );

        }
        catch (Exception e){

        }
    }
    public infoThread getInfoThread(){return infor;}
}


