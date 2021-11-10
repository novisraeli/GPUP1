package target;

public class TargetIsExists extends TargetException{

    public TargetIsExists(String nameOne){
        super(nameOne);
    }

    @Override
    public void printTargetException(){
        super.printTargetException();
        System.out.println(super.targetOne);
        System.out.println(" - The target is not exists \n\r");
    }
}
