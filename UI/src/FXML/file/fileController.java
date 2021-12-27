package FXML.file;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import engine.engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import target.Target;
import target.targetTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class fileController {
    private mainAppController mainController;
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }
    public void addDataToTable() {
        try {
            mainController.observableList().clear();
            Map<String, Target> map = mainController.getEngine().getMap();
            for (String keys : map.keySet()) {
                targetTable t = new targetTable(map.get(keys));
                //t.setSerialSetTableCol();
                List<String > list= new ArrayList<>();
                mainController.getEngine().whatIf(t.getName(),list, engine.Dependence.DEPENDS_ON);
                t.setTotalDependsOnTableCol(list.size()-1);
                list.clear();
                mainController.getEngine().whatIf(t.getName(),list, engine.Dependence.REQUIRED_FOR);
                t.setTotalRequiredForTableCol(list.size()-1);

                t.setSerialSetTableCol(mainController.getEngine().getAllSerialSetsWithYou(t.getName()).size());

                mainController.observableList().add(t);
            }
        }
        catch (Exception e){new errorMain(e);}
    }
    public void changeButtonColor(String newColorString){
        loadFileButton.setStyle(newColorString);
    }
    ////// set on action button
    @FXML void loadFileButton(ActionEvent event) {
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

    ////// fxml member
    @FXML private BorderPane image;
    @FXML private Button loadFileButton;
}
