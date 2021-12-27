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
import javafx.scene.text.Text;
import target.Target;
import target.targetTable;

public class tableController {
    private mainAppController mainController;

    @FXML public void initialize() {
    }

    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
        mainController.setGeneralTableCol(nameTableCol, typeTableCol, dataTableCol, serialSetTableCol,
                                            directRequiredForTableCol, directDependsOnTableCol,
                                            totalRequiredForTableCol, totalDependsOnTableCol);
    }
    public void show() {
        try {
            tableView.setItems(mainController.items);
            GraphInformation info =  mainController.getEngine().targetsInFormation();
            targetNumberText.setText(info.getAmountOfTargets());
            middleNumberText.setText(info.getMiddle());
            rootsNumberText.setText(info.getRoot());
            indepNumberText.setText(info.getIndependents());
            leavesNumberText.setText(info.getLevies());

        }
        catch (Exception e){new errorMain(e);}
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
    @FXML private Text targetNumberText;
    @FXML private Text leavesNumberText;
    @FXML private Text middleNumberText;
    @FXML private Text rootsNumberText;
    @FXML private Text indepNumberText;
}


