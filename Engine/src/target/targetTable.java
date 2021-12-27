package target;

import engine.engineImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.CheckBox;

import java.util.Set;

public class targetTable extends Target{
    private CheckBox checkBoxPath;
    private CheckBox checkBoxTask;
    private Integer directRequiredForTableCol;
    private Integer totalRequiredForTableCol;
    private Integer directDependsOnTableCol;
    private Integer totalDependsOnTableCol;
    private Integer serialSetTableCol;

    public  targetTable(Target t) {
        super(t.getName(),t.getUserData(),t.getSetDependsOn(),t.getSetRequiredFor());
        this.checkBoxPath = new CheckBox();
        this.checkBoxTask = new CheckBox();
        this.SetStatus( t.getStatus());
        this.SetType(t.getType());
        this.SetUserData(t.getUserData());
        this.directRequiredForTableCol = t.getSetRequiredFor().size();
        this.directDependsOnTableCol =  t.getSetDependsOn().size();
    }

    public CheckBox getCheckBoxPath() {return checkBoxPath;}
    public void setCheckBoxPath(CheckBox remark) {
        this.checkBoxPath = remark;
    }
    public CheckBox getCheckBoxTask() {return checkBoxTask;}
    public void setCheckBoxTask(CheckBox remark) {
        this.checkBoxTask = remark;
    }

    public Integer getSerialSetTableCol() {return serialSetTableCol;}
    public void setSerialSetTableCol(Integer serialSetTableCol) {
        this.serialSetTableCol = serialSetTableCol;
    }

    public Integer getDirectRequiredForTableCol() {return directRequiredForTableCol;}
    public void setDirectRequiredForTableCol(Integer directRequiredForTableCol) {this.directRequiredForTableCol = directRequiredForTableCol;}

    public Integer getTotalRequiredForTableCol() {return totalRequiredForTableCol;}
    public void setTotalRequiredForTableCol(Integer totalRequiredForTableCol) {this.totalRequiredForTableCol = totalRequiredForTableCol;}

    public Integer getDirectDependsOnTableCol() {return directDependsOnTableCol;}
    public void setDirectDependsOnTableCol(Integer directDependsOnTableCol) {this.directDependsOnTableCol = directDependsOnTableCol;}

    public Integer getTotalDependsOnTableCol() {return totalDependsOnTableCol;}
    public void setTotalDependsOnTableCol(Integer totalDependsOnTableCol) {this.totalDependsOnTableCol = totalDependsOnTableCol;}
}
