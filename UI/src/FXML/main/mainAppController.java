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
import javafx.scene.paint.Color;
import target.Target;
import target.targetTable;


public class mainAppController {
    public String color = "-fx-background-color: linear-gradient(#2A5058, #61a2b1)";
    public String toggleColor = "-fx-background-color: linear-gradient(#2A5058, #61a2b1)";
    private final engine engine = new engineImpl();
    private final SimpleBooleanProperty isFileSelected;
    public ObservableList<targetTable> items = FXCollections.observableArrayList();
    public mainAppController() {
        isFileSelected = new SimpleBooleanProperty(false);
    }
    @FXML public void initialize() {
        setMainForComponentController();
        setBinding();
    }
    public SimpleBooleanProperty isFileSelected(){
        return isFileSelected;
    }
    public ObservableList observableList(){
        return items;
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
               taskComponentController != null && settingComponentController != null){
            settingComponentController.setMainController(this);
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
    public void changeTabColor(Color newColor){
        String newColorString = "-fx-background-color: rgb(" + newColor.getRed()*255 + "," +  newColor.getGreen()*255 +"," +  newColor.getBlue()*255 +")";
       // -fx-background-color: rgba(170, 170, 170, 204);
        tableTab.setStyle(newColorString);
        graphTab.setStyle(newColorString);
        pathTab.setStyle(newColorString);
        taskTab.setStyle(newColorString);
        fileTab.setStyle(newColorString);
        settingTab.setStyle(newColorString);
    }
    public void changeButtonColor(Color newColor){
        String newColorString = "-fx-background-color: rgb(" + newColor.getRed()*255 + "," +  newColor.getGreen()*255 +"," +  newColor.getBlue()*255 +")";
        fileComponentController.changeButtonColor(newColorString);
        pathComponentController.changeButtonColor(newColorString);
        taskComponentController.changeButtonColor(newColorString);
    }
    public void changeToggleColor(){
        pathComponentController.changeToggleColor();
        taskComponentController.changeToggleColor();
    }
    public void changeComboBoxColor(Color newColor){
        String newColorString = "-fx-background-color: rgb(" + newColor.getRed()*255 + "," +  newColor.getGreen()*255 +"," +  newColor.getBlue()*255 +")";
        pathComponentController.changeComboBoxColor(newColorString);
        taskComponentController.changeComboBoxColor(newColorString);
    }
    public void changeTableColor(Color newColor) {
        String newColorString = "-fx-background-color: rgb(" + newColor.getRed()*255 + "," +  newColor.getGreen()*255 +"," +  newColor.getBlue()*255 +")";
        pathComponentController.changeTableColor(newColorString);
        tableComponentController.changeTableColor(newColorString);
        taskComponentController.changeTableColor(newColorString);
    }
    public void changeBackgroundColor(Color newColor) {
        String newColorString = "-fx-background-color: rgb(" + newColor.getRed()*255 + "," +  newColor.getGreen()*255 +"," +  newColor.getBlue()*255 +")";
        pathComponentController.changeBackgroundColor(newColorString);
        tableComponentController.changeBackgroundColor(newColorString);
        taskComponentController.changeBackgroundColor(newColorString);
    }



    /// tab fxml
    @FXML private Tab tableTab;
    @FXML private Tab graphTab;
    @FXML private Tab pathTab;
    @FXML private Tab taskTab;
    @FXML private Tab fileTab;
    @FXML private Tab settingTab;
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

}

