package target;

import java.util.ArrayList;
import java.util.List;

public class Targets {
    private final List<String> targetsList;
    private boolean find;

    public Targets(){
        targetsList = new ArrayList<>();
        find = false;
    }
    public List<String> getTargetsList(){return targetsList;}
    public boolean getFind(){return find;}
    public void setFind(boolean f){find = f;}
}
