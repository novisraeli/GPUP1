package FXML.path;

import FXML.main.mainAppController;
import engine.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import target.targetTable;


public class pathController {
    private mainAppController mainController;
    private SimpleBooleanProperty isSourceSelected;
    private SimpleBooleanProperty isDestinationSelected;

    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

    private final int maxNumSelected =  2;

    public pathController(){
        isSourceSelected = new SimpleBooleanProperty(false);
        isDestinationSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private TableView<targetTable> tableView;
    @FXML
    private TableColumn<targetTable,Boolean> remarkTableCol;
    @FXML
    private TableColumn<targetTable, String> nameTableCol;
    @FXML
    private TableColumn<targetTable, String> typeTableCol;
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
    private ListView<?> pathListView;

    @FXML
    private ComboBox<String> depenceComboBox;
    @FXML
    private Text sourceText;

    @FXML
    private Text destinationText;

    @FXML
    private Button switchButton;

    @FXML
    private Button clearButton;

    @FXML
    private ToggleButton toggleButtonPath;

    @FXML
    private ToggleButton toggleButtonCycle;

    @FXML
    private Button runButton;

    @FXML
    private Button setButton;


    @FXML
    public void initialize() {
        /*
        runButton.disableProperty().bind(
                Bindings.and(
                        isDestinationSelected,isSourceSelected));
        depenceComboBox.getItems().add(engine.Dependence.REQUIRED_FOR.toString());
        depenceComboBox.getItems().add(engine.Dependence.DEPENDS_ON.toString());

         */
        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (newSelectedCount.intValue() >= maxNumSelected) {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
                clearButton.setDisable(false);
            } else {
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
                clearButton.setDisable(true);
            }
        });

        nameTableCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        directRequiredForTableCol.setCellValueFactory(new PropertyValueFactory<>("directRequiredForTableCol"));
        directDepemdsOnTableCol.setCellValueFactory(new PropertyValueFactory<>("directDepemdsOnTableCol"));
        totalRequiredForTableCol.setCellValueFactory(new PropertyValueFactory<>("totalRequiredForTableCol"));
        TotalDepemdsOnTableCol.setCellValueFactory(new PropertyValueFactory<>("TotalDepemdsOnTableCol"));

        dataTableCol.setCellValueFactory(new PropertyValueFactory<>("userData"));
        remarkTableCol.setCellValueFactory(new PropertyValueFactory<>("remark"));
        tableView.getColumns().addAll(nameTableCol, typeTableCol, directRequiredForTableCol, directDepemdsOnTableCol,
                totalRequiredForTableCol,dataTableCol,remarkTableCol);
       // tableView.itemsProperty().bind(mainController.isFileSelected());

    }


    @FXML
    void setForRun(ActionEvent event) {
        int x = 0;
        ObservableList<targetTable> data = tableView.getItems();
        for (targetTable p : data)
        {
            if(p.getRemark().isSelected()) {
                if (x == 0) {
                    sourceText.setText(p.getName());
                    isSourceSelected.set(true);
                    x++;
                }
                if (x==1) {
                    destinationText.setText(p.getName());
                    isDestinationSelected.set(true);
                }
            }

        }
            Text temp = sourceText;
            sourceText = destinationText;
            destinationText = temp;
    }

    @FXML
    void clearAction(ActionEvent event) {
        ObservableList<targetTable> data = tableView.getItems();
        for (targetTable p : data)
        {
            if(p.getRemark().isSelected())
                p.getRemark().setSelected(false);
        }
    }

    @FXML
    void switchAction(ActionEvent event) {
        if (isDestinationSelected.get() && isSourceSelected.get()) {
            Text temp = sourceText;
            sourceText = destinationText;
            destinationText = temp;
        }
    }

    @FXML
    void runButtonAction(ActionEvent event) {
        if (isDestinationSelected.get() && isSourceSelected.get()) {
            if (toggleButtonCycle.isSelected())
                sourceText.setText("barrrr");
            if (toggleButtonPath.isSelected())
                sourceText.setText("dannnnn");
        }
    }

    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }

    public void show() {
        tableView.setItems(mainController.items);
        for (int i = 0 ; i< mainController.items.size();++i)
            configureCheckBox(mainController.items.get(i).getRemark());
    }

    private void configureCheckBox(CheckBox checkBox) {

        if (checkBox.isSelected()) {
            selectedCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            } else {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });

    }
}

