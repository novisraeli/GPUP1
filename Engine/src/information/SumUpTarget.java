package information;
import java.text.SimpleDateFormat;

/// Sum up target:
/// - target name
/// - the target result
/// - date

public class SumUpTarget implements Information{

    private String name;
    private String result;
    private String time;

    public SumUpTarget(String name , String result ,String time  ){
        this.name = name;
        this.result = result;
        this.time=time;

    }

    @Override
    public String toString() {
        return
                "Target name: " + name + "\n\r" +
                "Target result: " + result + "\n\r" +
                "Target time in ms: " + time + "\n\r";
    }
}
