package information;
import java.util.List;

/// contain all the information about the path between two targets:
/// - source target
/// - destination target
/// - attitude (dependsOn / requiredFor)
/// - the path between the targets

public class PathBetweenTwoTargetsInfo implements Information{

    private String source;
    private String destination;
    private String attitude;
    private List<String> path;

    public PathBetweenTwoTargetsInfo(String source , String destination , String attitude ,List<String> path){
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
