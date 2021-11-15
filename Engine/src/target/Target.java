package target;

import java.util.ArrayList;
import java.util.List;

public class Target
{
        private String userData;
        private String name;
        private Type type;
        private List<String> listDependsOn;
        private List<String> listRequiredFor;


        public  Target(String name , String userData , List<String> listDependsOn , List<String> listRequiredFor , Type type)
        {
            this.name = name;
            this.userData = userData;
            this.listDependsOn = listDependsOn;
            this.listRequiredFor = listRequiredFor;
            this.type = type;
        }

        public String getName() {
        return name;
    }
        public String getUserData() {
            return userData;
        }
        public List<String> getListDependsOn() {
        return listDependsOn;
    }
        public List<String> getListRequiredFor() {
        return listRequiredFor;
    }
        public Type getType() {
        return type;
    }








    @Override
    public String toString() {
        return "Target: "+ name + "\n\r"
                + "Target DependsOn:" + listDependsOn + "\n\r"
                + "Target RequiredFor:" + listRequiredFor + "\n\r"
                + "Target type:" + type + "\n\r"
                + "Target Data: "+ userData + "\n\r" ;
    }
}


