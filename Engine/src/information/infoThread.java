package information;

import target.Target;

public class infoThread {
    public enum InOrOut {IN, OUT}
    private InOrOut inOut;
    private long timeAbsolut;
    private String timeAbsolutString;
    private String timeFromStart;
    private int freeThread;
    private long currTime;
    private int targetInPool;


    public infoThread(InOrOut inOut,long timeAbsolut,String timeFromStart,int freeThread , int targetInPool){
        this.inOut = inOut;
        this.timeAbsolut = timeAbsolut;
        timeAbsolutString = makeTimeString(timeAbsolut);
        this.timeFromStart = timeFromStart;
        this.freeThread = freeThread;
        this.targetInPool = targetInPool;
    }
    public infoThread(InOrOut inOut,long currTime ,int freeThread ,int targetInPool){
        this.inOut = inOut;
        this.freeThread = freeThread;
        this.currTime = currTime;
        this.targetInPool = targetInPool;
    }
    public String makeTimeString(long time){
        long millis = time % 1000;
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;
        String Time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        return Time;
    }
    public int getTargetInPool(){return targetInPool;}
    public String getTimeAbsolutString(){return timeAbsolutString;}
    public InOrOut getInOut(){return inOut;}
    public long getTimeAbsolut(){return timeAbsolut;}
    public String getTimeFromStart(){return timeFromStart;}
    public int getFreeThread(){return freeThread;}
    public long getCurrTime(){return currTime;}
    public void setInOut(InOrOut inOut){this.inOut = inOut;}
    public void setTimeAbsolut(long timeAbsolut){this.timeAbsolut = timeAbsolut;}
    public void setTimeFromStart(String timeFromStart){this.timeFromStart = timeFromStart;}
    public void setFreeThread(int freeThread){this.freeThread = freeThread;}
    public void setCurrTime(long time){this.currTime = time;}
    public void setTimeAbsolutString(String time){this.timeAbsolutString = time;}
    public void getTargetInPool(int targetInPool){this.targetInPool = targetInPool;}

}