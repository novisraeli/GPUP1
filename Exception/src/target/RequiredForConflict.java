package target;

public class RequiredForConflict extends TargetException {

    public RequiredForConflict(String nameOne , String nameTwo){
        super(nameOne , nameTwo);
    }

    @Override
    public void printTargetException(){
        super.printTargetException();
        System.out.println(super.targetOne + "requiredFor  " + super.targetTwo +
                " then " + super.targetTwo + "can’t be requiredFor  " + super.targetOne +"\n\r");
    }
}
