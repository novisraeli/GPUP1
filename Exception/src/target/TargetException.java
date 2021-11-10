package target;

public class TargetException extends Exception {
    protected String targetOne;
    protected String targetTwo;

    public TargetException(String targetOneName){
        targetOne = targetOneName;
    }

    public TargetException(String targetOneName , String targetTwoName){
        targetOne = targetOneName;
        targetTwo = targetTwoName;
    }

    public String toString(){
        return  "Target Exception: ";
    }
}
