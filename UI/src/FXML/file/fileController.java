package FXML.file;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import engine.engine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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

    /**
     * add the data from the xml file (engine) to all the tabel in the app
     */
    public void addDataToTable() {
        try {
            mainController.getObservableList().clear();
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

                mainController.getObservableList().add(t);
            }

        }
        catch (Exception e){new errorMain(e);}
    }
    /**
     * change the button color in file pane
     */
    public void changeButtonColor(String newColorString){
        loadFileButton.setStyle(newColorString);
    }

    /**
     * load xml file from FileChooser and call to function that update the information in the app
     * @param event
     */
    @FXML void loadFileButton(ActionEvent event) {
        try{
            File file = new FileChooser().showOpenDialog(new Stage());
            mainController.getEngine().loadFile(file.getPath());
            addDataToTable();
            mainController.showTable();
            mainController.setTreadsSpinner();
            mainController.fileAnimation();
            mainController.isFileSelected().set(true);
            mainController.change().set(true);
        }
        catch (Exception e) {
            new errorMain(e);
        }
    }
    public Button getFileButton(){return loadFileButton;}

    ////// fxml member
    @FXML private BorderPane image;
    @FXML private Button loadFileButton;
}
