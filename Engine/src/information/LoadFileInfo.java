package information;
/// contain all the information about load File status:
/// - The file open successfully or not

public class LoadFileInfo implements Information{
    private final boolean status;

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
