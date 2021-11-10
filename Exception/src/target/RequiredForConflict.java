package target;

public class RequiredForConflict extends TargetException {

    public RequiredForConflict(String nameOne , String nameTwo){
        super(nameOne , nameTwo);
    }

    @Override
    public String toString(){
        System.out.println (super.toString());
        System.out.println(super.targetOne + "requiredFor  " + super.targetTwo +
                " then " + super.targetTwo + "can’t be requiredFor  " + super.targetOne +"\n\r");
        return null;
    }
}
