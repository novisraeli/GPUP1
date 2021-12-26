package target;

import javafx.scene.control.CheckBox;

import java.util.Set;

public class targetTable extends Target{
    private CheckBox remark;
    private int directRequiredForTableCol;
    private int directDepemdsOnTableCol;

    public  targetTable(Target t) {
        super(t.getName(),t.getUserData(),t.getSetDependsOn(),t.getSetRequiredFor());
        this.remark = new CheckBox();
        this.SetStatus( t.getStatus());
        this.SetType(t.getType());
        this.SetUserData(t.getUserData());
        this.directRequiredForTableCol = t.getSetRequiredFor().size();
        directDepemdsOnTableCol =  t.getSetDependsOn().size();
    }

    public CheckBox getRemark() {
        return remark;
    }

    public void setRemark(CheckBox remark) {
        this.remark = remark;
    }
}
