package FXML.path;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import FXML.task.taskController;
import FXML.utility.buttonColor;
import engine.*;
import information.Information;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import target.Target;
import target.targetTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class pathController {
    private mainAppController mainController;
    private SimpleBooleanProperty isSourceSelected;
    private SimpleBooleanProperty isDestinationSelected;
    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();
    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private final int maxNumSelected =  2;
    private buttonColor color;
    public pathController(){
        isSourceSelected = new SimpleBooleanProperty(false);
        isDestinationSelected = new SimpleBooleanProperty(false);
        color = new buttonColor();

    }

    @FXML
    public void initialize() {
        setDefault();
        groupToggle();
        setBinding();
    }
    public void groupToggle(){
        toggleButtonWhatIf.selectedProperty().addListener( (observable, oldValue, newValue) -> {
            if (newValue == true) {
                toggleButtonWhatIf.setStyle(color.isSelected);
                toggleButtonPath.setSelected(false);
                toggleButtonCycle.setSelected(false);
            }
            else {
                toggleButtonWhatIf.setStyle(color.notSelected);
            }
        }) ;


        toggleButtonCycle.selectedProperty().addListener( (observable, oldValue, newValue) -> {
            if (newValue == true) {
                toggleButtonCycle.setStyle(color.isSelected);
                toggleButtonPath.setSelected(false);
                toggleButtonWhatIf.setSelected(false);
            }
            else {
                toggleButtonCycle.setStyle(color.notSelected);
            }
        }) ;


        toggleButtonPath.selectedProperty().addListener( (observable, oldValue, newValue) -> {
            if (newValue == true) {
                toggleButtonPath.setStyle(color.isSelected);
                toggleButtonWhatIf.setSelected(false);
                toggleButtonCycle.setSelected(false);
            }
            else {
                toggleButtonPath.setStyle(color.notSelected);
            }
        }) ;
    }
    public void setTableCol(){
        mainController.setGeneralTableCol(nameTableCol, typeTableCol, dataTableCol, serialSetTableCol,
                directRequiredForTableCol, directDependsOnTableCol,
                totalRequiredForTableCol, totalDependsOnTableCol);
        remarkTableCol.setCellValueFactory(new PropertyValueFactory<>("checkBoxPath"));
    }
    public void setDefault(){
        toggleButtonPath.setSelected(true);
        toggleButtonWhatIf.setSelected(false);
        toggleButtonCycle.setSelected(false);
        depenceComboBox.getItems().add(engine.Dependence.REQUIRED_FOR);
        depenceComboBox.getItems().add(engine.Dependence.DEPENDS_ON);
        depenceComboBox.setValue(engine.Dependence.DEPENDS_ON);
    }
    public void setBinding(){
        ////// show destinationText only if toggleButtonPath is selected
        destinationText.opacityProperty().bind(
                Bindings.when(
                                toggleButtonPath.selectedProperty())
                        .then(1)
                        .otherwise(0.1)
        );
        ////// enable run button only if the source selected
        runButton.disableProperty().bind(isSourceSelected.not());

        ///// select only 2 checkBox from the table
        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (newSelectedCount.intValue() >= maxNumSelected)
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
            else
                unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
        });
    }
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
        setTableCol();
    }
    public void show() {
        tableView.setItems(mainController.items);
        for (int i = 0 ; i< mainController.items.size();++i)
            configureCheckBox(mainController.items.get(i).getCheckBoxPath());
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

////// set on action button
    @FXML void setForRun(ActionEvent event) {
        int x = 0;
        ObservableList<targetTable> data = tableView.getItems();
        for (targetTable p : data)
        {
            if(p.getCheckBoxPath().isSelected()) {
                if (x == 0) {
                    sourceText.setText(p.getName());
                    isSourceSelected.set(true);
                    x++;
                }
                else if (x==1) {
                    destinationText.setText(p.getName());
                    isDestinationSelected.set(true);
                    x++;
                }
            }
        }

        if (x==1) {
            destinationText.setText(sourceText.getText());
            isDestinationSelected.set(true);
        }


    }
    @FXML void clearAction(ActionEvent event) {
        ObservableList<targetTable> data = tableView.getItems();
        for (targetTable p : data)
        {
            if(p.getCheckBoxPath().isSelected())
                p.getCheckBoxPath().setSelected(false);
        }
    }
    @FXML void switchAction(ActionEvent event) {
        if (isDestinationSelected.get() && isSourceSelected.get()) {
            String temp = (sourceText.getText());
            sourceText.setText(destinationText.getText());
            destinationText.setText(temp);
        }
    }
    @FXML void runButtonAction(ActionEvent event) {
        Information info = null;
        try {

            if (isDestinationSelected.get() && isSourceSelected.get() && toggleButtonPath.isSelected())
                info = mainController.getEngine().findAPathBetweenTwoTargets(sourceText.getText(), destinationText.getText(), depenceComboBox.getValue());

            else if (isSourceSelected.get()){

                if (toggleButtonCycle.isSelected())
                    info = mainController.getEngine().circuitDetection(sourceText.getText());

                if (toggleButtonWhatIf.isSelected()){
                    List<String> whatIfList = new ArrayList<>();
                    mainController.getEngine().whatIf(sourceText.getText(),whatIfList , depenceComboBox.getValue());
                    pathListView.getItems().clear();
                    pathListView.getItems().add(whatIfList.toString());
                }

            }
            if (info != null) {
                pathListView.getItems().clear();
                pathListView.getItems().add(info.toString());
            }

        }
        catch (Exception e){new errorMain(e);}
    }

////// fxml member

    @FXML private TableView<targetTable> tableView;
    @FXML private TableColumn<targetTable,Boolean> remarkTableCol;
    @FXML private TableColumn<targetTable, String> nameTableCol;
    @FXML private TableColumn<targetTable, Target.Type> typeTableCol;
    @FXML private TableColumn<targetTable, Integer> directRequiredForTableCol;
    @FXML private TableColumn<targetTable, Integer> totalRequiredForTableCol;
    @FXML private TableColumn<targetTable, Integer> directDependsOnTableCol;
    @FXML private TableColumn<targetTable, Integer> totalDependsOnTableCol;
    @FXML private TableColumn<targetTable, String> dataTableCol;
    @FXML private TableColumn<targetTable, Integer> serialSetTableCol;
    @FXML private ListView<String> pathListView;
    @FXML private ComboBox<engine.Dependence> depenceComboBox;
    @FXML private Text sourceText;
    @FXML private Text destinationText;
    @FXML private Button switchButton;
    @FXML private Button clearButton;
    @FXML private ToggleButton toggleButtonPath;
    @FXML private ToggleButton toggleButtonCycle;
    @FXML private ToggleButton toggleButtonWhatIf;
    @FXML private Button runButton;
    @FXML private Button setButton;

}

