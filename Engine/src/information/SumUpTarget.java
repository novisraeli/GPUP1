package information;

import target.Target;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.SimpleTimeZone;

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
