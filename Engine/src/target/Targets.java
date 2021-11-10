package target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Targets{

    private Map<String,Target> Targets;

    public Targets(List<Target> listTarget) {
        this.Targets = new HashMap<>();
        for(Target pt : listTarget)
            Targets.put(pt.getName(),pt);
    }
}
