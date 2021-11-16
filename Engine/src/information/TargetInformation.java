package information;
import target.Target;
import java.util.Set;

/// contain all the information about target:
/// - target name
/// - type (root , middle ...)
/// - set of dependsOn (contain name of targets)
/// - set of requiredFor (contain name of targets)
/// - data target

public class TargetInformation implements Information{ // 3

    private String name;
    private Target.Type type;
    private Set<String> dependsOn;
    private Set<String> requiredFor;
    private String data;

    public TargetInformation( String name , Target.Type type , Set<String> dependsOn , Set<String> requiredFor , String data)
    {
        this.name = name;
        this.type = type;
        this.dependsOn = dependsOn;
        this.requiredFor = requiredFor;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Target name: " + name +"\n\r" +
                "location target: " + type +"\n\r" +
                "dependsOn targets: " + dependsOn +"\n\r" +
                "requiredFor targets: " + requiredFor + "\n\r" +
                "Data target: " + data + "\n\r" ;
    }
}

