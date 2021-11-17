package information;

import target.Targets;

import java.util.List;

public class CircuitDetectionInfo implements Information{
    private final String name;
    private final List<Targets> paths;

    public CircuitDetectionInfo(String name ,List<Targets> paths){
        this.name = name;
        this.paths = paths;
    }

    @Override
    public String toString() {
        String st;
        int count = 1;
        if (paths.size() == 1)
            return name +" does not belong to a circle\n\r";

        st = "Target name: " + name + "\n\r" +
                "Cycle path:\n\r";

        for (Targets path : paths) {
            if (path.getFind()) {
                st += count + ". " + path.getTargetsList() + "\n\r";
                ++count;
            }
        }
        return st;
    }
}
