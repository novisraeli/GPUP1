package target;

import information.Information;
import information.SumUpTarget;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Target
{
    public enum Type {INDEPENDENTS, LEAF, MIDDLE, ROOT}
    public enum Status {Waiting,Success,Warning ,Skipped ,Failure}
    private final String userData;
    private final String name;
    private Type type;
    private Status status=Status.Waiting;
    private final Set<String> setDependsOn;
    private final Set<String> setRequiredFor;

    // Ctor
    public  Target(String name , String userData , Set<String> setDependsOn , Set<String> setRequiredFor) {
        this.name = name;
        this.userData = userData;
        this.setDependsOn = setDependsOn;
        this.setRequiredFor = setRequiredFor;
    }
    // Get function

    /** Get name
     * @return name of target
     */
    public String getName() {
        return name;
    }

    /** Get name
     * @return name of target
     */
    public String getUserData() {
            return userData;
    }

    /** Get name
     * @return name of target
     */
    public Set<String> getSetDependsOn() {
        return setDependsOn;
    }

    /** Get name
     * @return name of target
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
    // Add to set functio
    public void addToSetDependsOn(String st) {setDependsOn.add(st);}
    public void addToSetRequiredFor(String st) {setRequiredFor.add(st);}
    // Set function
    public void SetType(Type t) {
        this.type = t;
    }
    public void SetStatus(Status s) {
        this.status = s;
    }
    public void run(int time, boolean random, float success, float warning, List<Information>res,Map<String,Target>targetMap,String path) throws Exception {
        String fName=path+"\\"+this.name+".log";
        Random r=new Random();
        float successRand;
        float warningRand;

        if(!this.status.name().equals(Status.Waiting.name())){
            return;
        }
        for(String s:setDependsOn){
            if(targetMap.get(s).getStatus().name().equals(Status.Failure.name())||targetMap.get(s).getStatus().name().equals(Status.Skipped.name())){
                targetMap.get(s).SetStatus(Status.Skipped);
                File f=new File(fName);
                f.createNewFile();
                FileWriter w = new FileWriter(fName);
                w.write("Target name: " + this.name + "\n\r" +
                        "Target result: " + Status.Skipped.name() + "\n\r" +
                        "Target time : 00:00:00:00 \n\r");
                w.close();
                res.add(new SumUpTarget(this.name,this.status.name(),"00:00:00:00"));
                return;
            }
            else if(targetMap.get(s).getStatus().name().equals(Status.Waiting.name())){
                return;
            }
        }
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
        //
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
            this.status=Status.Failure;
        }
        res.add(new SumUpTarget(this.name,this.status.name(),simTimeString));
        File f=new File(fName);
        f.createNewFile();
        FileWriter w = new FileWriter(fName);
        w.write("Target name: " + this.name + "\n\r" +
                "Target result: " + this.status.name() + "\n\r" +
                "Target time : "+simTimeString +"\n\r");
        w.close();

    }

    @Override
    public String toString() {
        return "Target: "+ name + "\n\r"
                + "Target DependsOn:" + setDependsOn + "\n\r"
                + "Target RequiredFor:" + setRequiredFor + "\n\r"
                + "Target type:" + type + "\n\r"
                + "Target Data: "+ userData + "\n\r" ;
    }
}


