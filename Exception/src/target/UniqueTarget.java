package target;

public class UniqueTarget extends TargetException{

    public UniqueTarget(String nameOne) {
        super(nameOne);
    }

    @Override
    public String toString(){
        return super.toString() + super.targetOne + " - The name is not unique \n\r";

    }
}
