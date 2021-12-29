package FXML.file;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import engine.engine;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import target.Target;
import target.targetTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class fileController {
    private mainAppController mainController;
    private String fileName;
    @FXML public void initialize() {
        /**
         *
         */
        image.widthProperty().addListener((a,c,d)->{
            Image img = new Image("/FXML/file/css/programming.jpg");
            BackgroundImage bImg = new BackgroundImage(img,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(image.getWidth(),image.getHeight(),true, true, true, true));
            Background bGround = new Background(bImg);
            image.setBackground(bGround);
        });
    }
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
            addDataToTable();
            mainController.showTable();
            mainController.setTreadsSpinner();
            mainController.fileAnimation();
            //int i = fileName.lastIndexOf('.');
            //fileName = fileName.substring(i+1);
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
