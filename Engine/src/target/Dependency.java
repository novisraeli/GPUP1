package target;

public class Dependency {

    private String value;
    private String type;

    public Dependency(String value ,String type){
        this.value = value;
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getType() {
        return type;
    }
    public void setType(String value) {
        this.type = value;
    }

    @Override
    public String toString() {
        return type + " " + value;
    }
}
