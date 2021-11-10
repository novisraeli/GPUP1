package target;

import java.util.ArrayList;
import java.util.List;

public class Targets{

    private List<Target> gpupTarget;

    public List<Target> getGPUPTarget() {
        if (gpupTarget == null) {
            gpupTarget = new ArrayList<Target>();
        }
        return this.gpupTarget;
    }

}
