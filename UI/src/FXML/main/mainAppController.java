package FXML.main;

import FXML.error.errorController;
import FXML.file.fileController;
import FXML.path.pathController;
import FXML.setting.settingController;
import FXML.table.tableController;
import FXML.whatIf.whatIfController;
import engine.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import target.Target;
import target.targetTable;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class mainAppController {
    public final static String ERROR_FXML = "/FXML/error/erroe.fxml";
    private final engine engine = new engineImpl();
    private SimpleBooleanProperty isFileSelected;
    public ObservableList<targetTable> items = FXCollections.observableArrayList();

    @FXML private Tab tableTab;
    @FXML private Tab graphTab;
    @FXML private Tab pathTab;
    @FXML private Tab taskTab;
    @FXML private Tab whatIfTab;

    @FXML private BorderPane fileComponent;
    @FXML private fileController fileComponentController;

    @FXML private BorderPane pathComponent;
    @FXML private pathController pathComponentController;

    @FXML private BorderPane settingComponent;
    @FXML private settingController settingComponentController;

    @FXML private BorderPane tableComponent;
    @FXML private tableController tableComponentController;

    @FXML private BorderPane whatIfComponent;
    @FXML private whatIfController whatIfComponentController;

    public mainAppController() {
        isFileSelected = new SimpleBooleanProperty(false);
    }

    public SimpleBooleanProperty isFileSelected(){return isFileSelected;}
    public ObservableList observableList(){return items;}


    @FXML
    public void initialize() {
        if (fileComponentController != null && pathComponentController != null &&
            settingComponentController != null && tableComponentController != null && whatIfComponentController != null){

            fileComponentController.setMainController(this);
            pathComponentController.setMainController(this);
            settingComponentController.setMainController(this);
            tableComponentController.setMainController(this);
            whatIfComponentController.setMainController(this);
        }
        tableTab.disableProperty().bind(isFileSelected.not());
        graphTab.disableProperty().bind(isFileSelected.not());
        pathTab.disableProperty().bind(isFileSelected.not());
        taskTab.disableProperty().bind(isFileSelected.not());
        whatIfTab.disableProperty().bind(isFileSelected.not());
    }

    public engine getEngine(){return engine;}


    public void showTable() {
        pathComponentController.show();
        tableComponentController.show();
    }
}

