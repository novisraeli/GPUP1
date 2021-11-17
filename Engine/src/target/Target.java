package target;

import information.Information;
import information.SumUpTarget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Target
{
    public enum Type {INDEPENDENTS, LEAF, MIDDLE, ROOT}
    public enum Status {Waiting,Success,Warning ,Skipped ,Failure}
    private String userData;
    private String name;
    private Type type;
    private Status status=Status.Waiting;
    private Set<String> setDependsOn;
    private Set<String> setRequiredFor;

    // Ctor
    public  Target(String name , String userData , Set setDependsOn , Set setRequiredFor) {
        this.name = name;
        this.userData = userData;
        this.setDependsOn = setDependsOn;
        this.setRequiredFor = setRequiredFor;
    }
    // Get function
    public String getName() {
        return name;
    }
    public String getUserData() {
            return userData;
        }
    public Set<String> getSetDependsOn() {
        return setDependsOn;
    }
    public Set<String> getSetRequiredFor() {
        return setRequiredFor;
    }
    public Type getType() {
        return type;
    }
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
    public void run(int time, boolean random, float success, float warning, List<Information>res,Map<String,Target>targetMap) throws Exception {
        Random r=new Random();
        float successRand;
        float warningRand;

        if(!this.status.equals(Status.Waiting)){
            return;
        }
        for(String s:setDependsOn){
            if(targetMap.get(s).getStatus().equals(Status.Failure)||targetMap.get(s).getStatus().equals(Status.Skipped)){
                targetMap.get(s).SetStatus(Status.Skipped);
                //need to create file in this condition too
                res.add(new SumUpTarget(this.name,this.status.name(),"00:00:00:00"));
                return;
            }
            else if(targetMap.get(s).getStatus().equals(Status.Waiting)){
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
        //wright to file as well

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


