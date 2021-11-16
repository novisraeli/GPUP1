package target;
/// send error message if target no exists in the structure
public class TargetIsExists extends TargetException{

    public TargetIsExists(String nameOne){
        super(nameOne);
    }

    @Override
    public String toString(){
        return super.toString() + super.targetOne + " - The target is not exists \n\r";
    }
}
