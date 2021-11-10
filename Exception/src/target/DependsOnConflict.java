package target;

public class DependsOnConflict extends TargetException {

    public DependsOnConflict(String nameOne , String nameTwo){
        super(nameOne , nameTwo);
    }

    @Override
    public String toString(){
        System.out.println (super.toString());
        System.out.println(super.targetOne + "dependsOn " + super.targetTwo +
        " then " + super.targetTwo + "can’t be dependsOn " + super.targetOne +"\n\r");
        return null;
    }
}
