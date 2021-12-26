package FXML.file;

import FXML.error.errorController;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import target.Target;
import target.targetTable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;


public class fileController {
    private mainAppController mainController;
    @FXML
    private Button loadFileButton;

    @FXML
    void loadFileButton(ActionEvent event) throws IOException {
        try{
            File file = new FileChooser().showOpenDialog(new Stage());
            mainController.getEngine().loadFile(file.getPath());
            mainController.isFileSelected().set(true);
            addDataToTable();
            mainController.showTable();
        }
        catch (Exception e) {
              new errorMain(e);
        }
    }

    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }

    public void addDataToTable() {
        try {
            mainController.observableList().removeAll();
            Map<String, Target> map = mainController.getEngine().getMap();
            for (String keys : map.keySet())
            {
                mainController.observableList().add(new targetTable(map.get(keys)));
            }
        }
        catch (Exception e){
        }
    }
}
