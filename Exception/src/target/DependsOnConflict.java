package target;

public class DependsOnConflict extends TargetException {

    public DependsOnConflict(String nameOne , String nameTwo){
        super(nameOne , nameTwo);
    }

    @Override
    public void printTargetException(){
        super.printTargetException();
        System.out.println(super.targetOne + "dependsOn " + super.targetTwo +
        " then " + super.targetTwo + "can’t be dependsOn " + super.targetOne +"\n\r");
    }
}
