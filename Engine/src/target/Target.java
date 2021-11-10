package target;

import java.util.ArrayList;
import java.util.List;

public class Target
{
        private String UserData;
        private TargetDependencies TargetDependencies;
        private String name;


        public String getGPUPUserData() {
            return UserData;
        }

        public void setGPUPUserData(String value) {
            this.UserData = value;
        }

        public TargetDependencies getGPUPTargetDependencies() {
            return TargetDependencies;
        }

        public void setGPUPTargetDependencies(TargetDependencies value) {
            this.TargetDependencies = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }
    }


