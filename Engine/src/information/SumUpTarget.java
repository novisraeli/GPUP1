package information;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/// Sum up target:
/// - target name
/// - the target result
/// - date

public class SumUpTarget implements Information , Serializable {

    private final String name;
    private final String result;
    private final String time;

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
