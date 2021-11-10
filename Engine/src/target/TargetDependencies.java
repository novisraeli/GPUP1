package target;

import java.util.ArrayList;
import java.util.List;

public class TargetDependencies {
    private List<Dependency> Dependency;

    public List<Dependency> getListOfDependency() {
        if (Dependency == null) {
            Dependency = new ArrayList<>();
        }
        return this.Dependency;
    }

}
