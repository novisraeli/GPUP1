package target;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Target
{
        private String userData;
        private String name;
        private Type type;
        private Set setDependsOn;
        private Set setRequiredFor;


        public  Target(String name , String userData , Set setDependsOn , Set setRequiredFor)
        {
            this.name = name;
            this.userData = userData;
            this.setDependsOn = setDependsOn;
            this.setRequiredFor = setRequiredFor;
        }

        public String getName() {
        return name;
    }
        public String getUserData() {
            return userData;
        }
        public Set<String> getSetDependsOn() {
        return setDependsOn;
    }
        public Set<String> getSetRequiredFor() {
        return setRequiredFor;
    }

    public void addToSetDependsOn(String st) {setDependsOn.add(st);}

    public void addToSetRequiredFor(String st) {setRequiredFor.add(st);}

    public Type getType() {
        return type;
    }
    public void SetType(Type t) {
        this.type = t;
    }








    @Override
    public String toString() {
        return "Target: "+ name + "\n\r"
                + "Target DependsOn:" + setDependsOn + "\n\r"
                + "Target RequiredFor:" + setRequiredFor + "\n\r"
                + "Target type:" + type + "\n\r"
                + "Target Data: "+ userData + "\n\r" ;
    }
}


