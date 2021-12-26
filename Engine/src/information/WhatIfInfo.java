package information;

import java.util.Set;

public class WhatIfInfo implements Information {
    Set<String>res;
    boolean dir;
    public WhatIfInfo(Set<String>res,boolean dir){
        this.res=res;
        this.dir=dir;
    }

    @Override
    public String toString() {
        String s;
        if(dir){
            s="Targets depends on source target are:\n";
        }
        else{
            s="Targets required for source target are:\n";
        }
        for(String st:res){
            s+=(st+",");
        }
        return s;
    }
}
