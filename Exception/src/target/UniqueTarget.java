package target;

public class UniqueTarget extends TargetException{

    public UniqueTarget(String nameOne) {
        super(nameOne);
    }

    @Override
    public void printTargetException(){
        super.printTargetException();
        System.out.println(super.targetOne);
        System.out.println(" - The name is not unique \n\r");
    }
}
