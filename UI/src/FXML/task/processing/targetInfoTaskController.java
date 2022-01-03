package FXML.task.processing;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.List;

public class targetInfoTaskController {
    @FXML private Text nameTarget;
    @FXML private Text statusTarget;
    @FXML private Text serialSetInfo;
    @FXML private Text processStatus;
    @FXML private Label nameLabel;
    @FXML private Label statusLabel;
    @FXML private Label serialSetLabel;
    @FXML private Label processStatusLabel;

    public targetInfoTaskController(){}
    public void setInfo(String name , String type , String serialSets ,  String process){
        nameTarget.setText(name);
        statusTarget.setText(type);
        serialSetInfo.setText(serialSets);
        processStatus.setText(process);
    }
    public void setSumUp(String skipped , String finishedSuccessfully , String finishedWarning ,  String finishedWithFailure){
        nameTarget.setText(skipped);
        statusTarget.setText(finishedSuccessfully);
        serialSetInfo.setText(finishedWarning);
        processStatus.setText(finishedWithFailure);
        nameLabel.setText("Skipped - ");
        statusLabel.setText("finished Successfully - ");
        serialSetLabel.setText("finished Warning - ");
        processStatusLabel.setText("finished With Failure - ");
    }

}
