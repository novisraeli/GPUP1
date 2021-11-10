package target;

public class TargetException {
    protected String targetOne;
    protected String targetTwo;

    public TargetException(String targetOneName){
        targetOne = targetOneName;
    }

    public TargetException(String targetOneName , String targetTwoName){
        targetOne = targetOneName;
        targetTwo = targetTwoName;
    }

    public void printTargetException(){
        System.out.println("Target Exception:");
    }
}
