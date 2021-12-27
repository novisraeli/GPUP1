package FXML.main;
import FXML.file.fileController;
import FXML.path.pathController;
import FXML.setting.settingController;
import FXML.table.tableController;
import FXML.task.taskController;
import engine.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import target.Target;
import target.targetTable;


public class mainAppController {
    private final engine engine = new engineImpl();
    private final SimpleBooleanProperty isFileSelected;
    public ObservableList<targetTable> items = FXCollections.observableArrayList();
/// tab fxml
    @FXML private Tab tableTab;
    @FXML private Tab graphTab;
    @FXML private Tab pathTab;
    @FXML private Tab taskTab;

    @FXML private BorderPane mainBorderPane;
    @FXML private BorderPane fileComponent;
    @FXML private fileController fileComponentController;

    @FXML private BorderPane pathComponent;
    @FXML private pathController pathComponentController;

    @FXML private BorderPane settingComponent;
    @FXML private settingController settingComponentController;

    @FXML private BorderPane tableComponent;
    @FXML private tableController tableComponentController;

    @FXML private BorderPane taskComponent;
    @FXML private taskController taskComponentController;

    public mainAppController() {
        isFileSelected = new SimpleBooleanProperty(false);
    }

    public SimpleBooleanProperty isFileSelected(){return isFileSelected;}
    public ObservableList observableList(){return items;}

    @FXML public void initialize() {
        setMainForComponentController();
        setBinding();
    }
    public static void setGeneralTableCol(TableColumn<targetTable,String> nameTableCol,
                                          TableColumn<targetTable, Target.Type> typeTableCol,
                                          TableColumn<targetTable, String> dataTableCol,
                                          TableColumn<targetTable, Integer> serialSetTableCol,
                                          TableColumn<targetTable, Integer> directRequiredForTableCol,
                                          TableColumn<targetTable, Integer> directDependsOnTableCol,
                                          TableColumn<targetTable, Integer> totalRequiredForTableCol,
                                          TableColumn<targetTable, Integer> totalDependsOnTableCol) {
        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        directRequiredForTableCol.setCellValueFactory(new PropertyValueFactory<>("directRequiredForTableCol"));
        directDependsOnTableCol.setCellValueFactory(new PropertyValueFactory<>("directDependsOnTableCol"));
        totalRequiredForTableCol.setCellValueFactory(new PropertyValueFactory<>("totalRequiredForTableCol"));
        totalDependsOnTableCol.setCellValueFactory(new PropertyValueFactory<>("totalDependsOnTableCol"));
        serialSetTableCol.setCellValueFactory(new PropertyValueFactory<>("serialSetTableCol"));
        dataTableCol.setCellValueFactory(new PropertyValueFactory<>("userData"));

    }
    public void setMainForComponentController(){
        if (fileComponentController != null && pathComponentController != null &&
                settingComponentController != null && tableComponentController != null &&
               taskComponentController != null){

            fileComponentController.setMainController(this);
            pathComponentController.setMainController(this);
            settingComponentController.setMainController(this);
            tableComponentController.setMainController(this);
            taskComponentController.setMainController(this);
        }
    }
    public engine getEngine(){return engine;}
    public void showTable() {
        pathComponentController.show();
        tableComponentController.show();
        taskComponentController.show();
    }
    public void setBinding(){
        tableTab.disableProperty().bind(isFileSelected.not());
        graphTab.disableProperty().bind(isFileSelected.not());
        pathTab.disableProperty().bind(isFileSelected.not());
        taskTab.disableProperty().bind(isFileSelected.not());
    }
}

