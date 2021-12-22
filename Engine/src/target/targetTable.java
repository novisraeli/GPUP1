package target;

import javafx.scene.control.CheckBox;

import java.util.Set;

public class targetTable extends Target{
    private CheckBox remark;


    public  targetTable(String name , String userData , Set<String> setDependsOn , Set<String> setRequiredFor) {
        super(name,userData,setDependsOn,setRequiredFor);
        this.remark = new CheckBox();
    }

    public  targetTable(Target t) {
        super(t.getName(),t.getUserData(),t.getSetDependsOn(),t.getSetRequiredFor());
        this.remark = new CheckBox();
        this.SetStatus( t.getStatus());
        this.SetType(t.getType());
        this.SetUserData("bar");
    }

    public CheckBox getRemark() {
        return remark;
    }

    public void setRemark(CheckBox remark) {
        this.remark = remark;
    }
}
