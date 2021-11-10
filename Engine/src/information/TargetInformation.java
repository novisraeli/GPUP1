package information;

import target.Target;

import java.lang.ref.SoftReference;

public class TargetInformation extends InformationToUI{

    private Target target;

    public TargetInformation(Target target)
    {
        this.target = target;
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
