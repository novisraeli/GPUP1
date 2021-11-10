package target;

public class UniqueTarget extends TargetException{

    public UniqueTarget(String nameOne) {
        super(nameOne);
    }

    @Override
    public String toString(){
        System.out.println (super.toString());
        System.out.println(super.targetOne);
        System.out.println(" - The name is not unique \n\r");
        return null;
    }
}
