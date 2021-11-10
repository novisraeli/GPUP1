package target;

import java.util.ArrayList;
import java.util.List;

public class Target
{
    private String name;
    private List requiredFor = new ArrayList();
    private List dependsOn = new ArrayList();

    public String GetName() {
        return  name;
    }

}





