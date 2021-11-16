package information;
import java.text.SimpleDateFormat;

/// Sum up target:
/// - target name
/// - the target result
/// - date

public class SumUpTarget implements Information{

    private  String name;
    private String result;
    SimpleDateFormat simpleDateFormat;

    public SumUpTarget(String name , String result ,  SimpleDateFormat simpleDateFormat ){
        this.name = name;
        this.result = result;
        this.simpleDateFormat = simpleDateFormat;

    }

    @Override
    public String toString() {
        return
                "Target name: " + name + "\n\r" +
                "Target result: " + result + "\n\r" +
                "Target time: " + simpleDateFormat + "\n\r";
    }
}
