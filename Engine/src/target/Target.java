package target;

import java.util.ArrayList;
import java.util.List;

public class Target
{
        private String userData;
        private TargetDependencies targetDependencies;
        private String name;


        public String getGPUPUserData() {
            return userData;
        }

        public void setGPUPUserData(String value) {
            this.userData = value;
        }

        public TargetDependencies getGPUPTargetDependencies() {
            return targetDependencies;
        }

        public void setGPUPTargetDependencies(TargetDependencies value) {
            this.targetDependencies = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

    @Override
    public String toString() {
        return "Target: "+ name + "\n\r"
                + "Target Dependencies:" + targetDependencies + "\n\r"
                + "Target Data: "+ userData + "\n\r" ;
    }
}


