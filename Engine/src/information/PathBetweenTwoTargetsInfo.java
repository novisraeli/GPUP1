package information;

import target.Target;

import java.util.List;

public class PathBetweenTwoTargetsInfo implements Information{

    private String source;
    private String destination;
    private String attitude;
    private List<Target> path;

    public PathBetweenTwoTargetsInfo(String source , String destination , String attitude ,List<Target> path){
        this.source = source;
        this.destination = destination;
        this.attitude = attitude;
        this.path = path;
    }

    @Override
    public String toString() {
        return
        "Source: " + source + "\n\r" +
        "Destination: " + destination + "\n\r" +
        "Attitude: " + attitude + "\n\r"+
        "Path: " + path + "\n\r";
    }
}
