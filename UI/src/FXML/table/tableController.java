package FXML.table;

import FXML.error.errorMain;
import FXML.main.mainAppController;
import engine.engine;
import information.GraphInformation;
import information.Information;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import target.Target;
import target.targetTable;

public class tableController {
    private mainAppController mainController;

    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
        mainController.setGeneralTableCol(nameTableCol, typeTableCol, dataTableCol, serialSetTableCol,
                                            directRequiredForTableCol, directDependsOnTableCol,
                                            totalRequiredForTableCol, totalDependsOnTableCol);
    }
    public void show() {
        try {
            tableView.setItems(mainController.observableList());
            GraphInformation info =  mainController.getEngine().targetsInFormation();
            targetNumberText.setText(info.getAmountOfTargets());
            middleNumberText.setText(info.getMiddle());
            rootsNumberText.setText(info.getRoot());
            indepNumberText.setText(info.getIndependents());
            leavesNumberText.setText(info.getLevies());
            String serialSetString = "";
            for(String st : mainController.getEngine().getSerialSets().keySet()) {
                serialSetString += "name:  "+ st + " - " + mainController.getEngine().getSerialSets().get(st) + "\n\n";
            }
            if (serialSetString.equals(""))
                serialSetText.setText("There isn't serial set");
            else
                serialSetText.setText(serialSetString);
        }
        catch (Exception e){new errorMain(e);}
    }
    public void changeTableColor(String newColorString){
        tableView.setStyle(newColorString);
        nameTableCol.setStyle(newColorString);
        typeTableCol.setStyle(newColorString);
        directRequiredForTableCol.setStyle(newColorString);
        totalRequiredForTableCol.setStyle(newColorString);
        directDependsOnTableCol.setStyle(newColorString);
        totalDependsOnTableCol.setStyle(newColorString);
        dataTableCol.setStyle(newColorString);
        serialSetTableCol.setStyle(newColorString);
        dependsOnTableCol.setStyle(newColorString);
        requiredForTableCol.setStyle(newColorString);
    }
    public void changeBackgroundColor(String newColorString){
        vboxTable.setStyle(newColorString);
        hboxTable.setStyle(newColorString);
        borderPaneTable.setStyle(newColorString);
        gridPaneTable.setStyle(newColorString);
    }
    ////// fxml member
    @FXML private TableView<targetTable> tableView;
    @FXML private TableColumn<targetTable, String> nameTableCol;
    @FXML private TableColumn<targetTable, Target.Type> typeTableCol;
    @FXML private TableColumn<targetTable, Integer> directRequiredForTableCol;
    @FXML private TableColumn<targetTable, Integer> totalRequiredForTableCol;
    @FXML private TableColumn<targetTable, Integer> directDependsOnTableCol;
    @FXML private TableColumn<targetTable, Integer> totalDependsOnTableCol;
    @FXML private TableColumn<targetTable, Integer> serialSetTableCol;
    @FXML private TableColumn<targetTable, String> dataTableCol;
    @FXML private TableColumn<targetTable, Integer> dependsOnTableCol;
    @FXML private TableColumn<targetTable, String> requiredForTableCol;
    @FXML private Text targetNumberText;
    @FXML private Text leavesNumberText;
    @FXML private Text middleNumberText;
    @FXML private Text rootsNumberText;
    @FXML private Text indepNumberText;
    @FXML private Text serialSetText;
    @FXML private VBox vboxTable;
    @FXML private HBox hboxTable;
    @FXML private BorderPane borderPaneTable;
    @FXML private GridPane gridPaneTable;
}


