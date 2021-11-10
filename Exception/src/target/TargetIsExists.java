package target;

public class TargetIsExists extends TargetException{

    public TargetIsExists(String nameOne){
        super(nameOne);
    }

    @Override
    public String toString(){
        System.out.println (super.toString());
        System.out.println(super.targetOne);
        System.out.println(" - The target is not exists \n\r");
        return null;
    }
}
