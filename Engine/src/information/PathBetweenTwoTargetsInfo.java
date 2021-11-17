package information;
import target.Target;
import target.Targets;

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
    private List<Targets> paths;

    public PathBetweenTwoTargetsInfo(String source , String destination , String attitude ,List<Targets> paths){
        this.source = source;
        this.destination = destination;
        this.attitude = attitude;
        this.paths = paths;
    }

    @Override
    public String toString() {
        String st;
        int count = 1;
        if (paths.size() == 1)
            return "There isn't path between " + source + "and " + destination + "with attitude of "+ attitude + "\n\r";

        st = "Source: " + source + "\n\r" +
                "Destination: " + destination + "\n\r" +
                "Attitude: " + attitude + "\n\rPath:\n\r";

        for(int i = 0 ; i<paths.size(); ++i)
            if (paths.get(i).getFind() == true)
                if (paths.get(i).getTargetsList().size() == 0) {
                    st += count + ". " + "Direct dependency" + "\n\r";
                    ++count;
                }
                else {
                    st += count + ". " + paths.get(i).getTargetsList() + "\n\r";
                    ++count;
                }
        return st;
    }

    public List<Targets> getPaths(){return paths;}
}
