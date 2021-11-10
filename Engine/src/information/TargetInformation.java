package information;

import target.Target;

import java.util.List;

public class TargetInformation implements Information{ // 3

    private String name;
    private String loc;
    private List<Target> dependsOn;
    private List<Target> requiredFor;
    private String data;

    public TargetInformation( String name , String loc , List<Target> dependsOn , List<Target> requiredFor , String data)
    {
        this.name = name;
        this.loc = loc;
        this.dependsOn = dependsOn;
        this.requiredFor = requiredFor;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Target name: " + name +"\n\r" +
                "location target: " + loc +"\n\r" +
                "dependsOn targets: " + dependsOn +"\n\r" +
                "requiredFor targets: " + requiredFor + "\n\r" +
                "Data target: " + data + "\n\r" ;
    }
}

