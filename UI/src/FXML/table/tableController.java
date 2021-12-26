package FXML.table;

import FXML.main.mainAppController;
import engine.engine;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import target.Target;
import target.targetTable;

public class tableController {
    private mainAppController mainController;

    public tableController(){
        ///
    }

    @FXML
    public void initialize() {

        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        directRequiredForTableCol.setCellValueFactory(new PropertyValueFactory<>("directRequiredForTableCol"));
        directDepemdsOnTableCol.setCellValueFactory(new PropertyValueFactory<>("directDepemdsOnTableCol"));
        totalRequiredForTableCol.setCellValueFactory(new PropertyValueFactory<>("totalRequiredForTableCol"));
       // TotalDepemdsOnTableCol.setCellValueFactory(new PropertyValueFactory<>("TotalDepemdsOnTableCol"));
        dataTableCol.setCellValueFactory(new PropertyValueFactory<>("userData"));
//        tableView.setItems(mainController.observableList());
    }

    @FXML
    private TableView<targetTable> tableView;
    @FXML
    private TableColumn<targetTable, String> nameTableCol;

    @FXML
    private TableColumn<targetTable, Target.Type> typeTableCol;

    @FXML
    private TableColumn<targetTable, Integer> directRequiredForTableCol;

    @FXML
    private TableColumn<targetTable, Integer> totalRequiredForTableCol;

    @FXML
    private TableColumn<targetTable, Integer> directDepemdsOnTableCol;

    @FXML
    private TableColumn<targetTable, Integer> TotalDepemdsOnTableCol;

    @FXML
    private TableColumn<targetTable, String> dataTableCol;

    @FXML
    private Text targetNumberText;

    @FXML
    private Text leavesNumberText;

    @FXML
    private Text middleNumberText;

    @FXML
    private Text rootsNumberText;

    @FXML
    private Text indepNumberText;

    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }
    public void show() {
        tableView.setItems(mainController.items);
        for(int i =0 ; i < mainController.items.size() ; i++)
            mainController.items.get(i).getRemark();






    }
}
