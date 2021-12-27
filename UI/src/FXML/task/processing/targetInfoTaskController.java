package FXML.task.processing;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

public class targetInfoTaskController {
    @FXML private Text nameTarget;
    @FXML private Text statusTarget;
    @FXML private Text serialSetInfo;
    @FXML private Text processStatus;
    public targetInfoTaskController(){}
    public void setInfo(String name , String type , String serialSets ,  String process){
        nameTarget.setText(name);
        statusTarget.setText(type);
        serialSetInfo.setText(serialSets);
        processStatus.setText(process);
    }
}
