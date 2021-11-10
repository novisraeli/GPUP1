package information;

public class LoadFileInfo implements Information{
    private boolean status;
    public LoadFileInfo(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if (true == status)
            return "The file open successfully \n\r";
        else
            return "The open file failed \n\r";
    }
}
