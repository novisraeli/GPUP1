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

    private final String name;
    private final Target.Type type;
    private final Set<String> dependsOn;
    private final Set<String> requiredFor;
    private final String data;

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
        String depends;
        String required;
        String dataS;

        if (dependsOn.size() == 0)
            depends = "There isn't depends-on target";
        else
            depends = dependsOn.toString();

        if (requiredFor.size() == 0)
            required = "There isn't required-for target";
        else
            required = requiredFor.toString();

        if (this.data == null)
            dataS = "There isn't data for this target";
        else
            dataS = data;

        return "Target name: " + name +"\n\r" +
                "Target type: " + type +"\n\r" +
                "DependsOn targets: " + depends +"\n\r" +
                "RequiredFor targets: " + required + "\n\r" +
                "Data target: " + dataS + "\n\r" ;
    }
}

