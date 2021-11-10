package target;

import java.util.ArrayList;
import java.util.List;

public class Targets{

    private List<Target> Targets;

    public List<Target> getGPUPTarget() {
        if (Targets == null) {
            Targets = new ArrayList<Target>();
        }
        return this.Targets;
    }
}
