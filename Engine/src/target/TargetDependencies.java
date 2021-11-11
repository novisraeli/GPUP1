package target;

import java.util.ArrayList;
import java.util.List;

public class TargetDependencies {

    private List<Dependency> listDependency;

    public TargetDependencies(List<Dependency> listDependency){
        this.listDependency = listDependency;
    }
    public List<Dependency> getListOfDependency() {
        if (listDependency == null) {
            listDependency = new ArrayList<>();
        }
        return this.listDependency;
    }

    @Override
    public String toString() {
        return listDependency.toString();
    }
}
