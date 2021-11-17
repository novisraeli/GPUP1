package target;

import java.util.ArrayList;
import java.util.List;

public class Targets {
    private final List<String> targetsList;
    private boolean find;

    // Ctor
    public Targets(){
        targetsList = new ArrayList<>();
        find = false;
    }
    // Get
    public List<String> getTargetsList(){return targetsList;}
    public boolean getFind(){return find;}
    // Set
    public void setFind(boolean f){find = f;}
}
