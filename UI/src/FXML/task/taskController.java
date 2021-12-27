package FXML.task;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import FXML.task.processing.targetInfoMain;
import engine.engine;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import target.Target;
import target.UniqueTarget;
import target.targetTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class taskController {

    private mainAppController mainController;
    private SimpleBooleanProperty isRunSelected;
    private SimpleBooleanProperty isPauseSelected;
    private SimpleBooleanProperty isCompiler;
    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();

    public taskController(){
        isRunSelected = new SimpleBooleanProperty(false);
        isPauseSelected = new SimpleBooleanProperty(false);
        isCompiler = new SimpleBooleanProperty(true);
    }
    @FXML public void initialize() {
        setSpinner();
        setToggles();
        setRunAndPauseButton();
        setComboBox();
        clickOnRow();
        simulationBox.disableProperty().bind(isCompiler);
        isCompiler.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (isCompiler.getValue()) {
                compilerToggle.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
                simulationToggle.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
            }
            else {
                simulationToggle.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
                compilerToggle.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
            }
        });
    }
    private void configureCheckBoxTask(CheckBox checkBox) {
        if (checkBox.isSelected())
            selectedCheckBoxes.add(checkBox);

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                selectedCheckBoxes.add(checkBox);
                if (withDepend.isSelected())
                    getAllTargetWith(engine.Dependence.DEPENDS_ON);
                if (withRequired.isSelected())
                    getAllTargetWith(engine.Dependence.REQUIRED_FOR);
            }
             else
                selectedCheckBoxes.remove(checkBox);
        });
    }
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
        setTableCol();
    }
    public void show() {
        tableView.setItems(mainController.items);
        for (int i = 0 ; i< mainController.items.size();++i)
            configureCheckBoxTask(mainController.items.get(i).getCheckBoxTask());
    }
    public void getAllTargetWith(engine.Dependence d ){
        List<String> newList = new ArrayList();
            try{
                for (int i = 0 ; i < mainController.items.size();++i) {
                    if (mainController.items.get(i).getCheckBoxTask().isSelected()) {
                        mainController.getEngine().whatIf(mainController.items.get(i).getName(), newList, d);
                        for (int k = 0; k < mainController.items.size(); ++k)
                            if (newList.contains(mainController.items.get(k).getName())) {
                                mainController.items.get(k).getCheckBoxTask().setSelected(true);
                            }
                    }
                }
            }

            catch (Exception e){}
    }
    public void setTableCol(){
        mainController.setGeneralTableCol(nameTableCol, typeTableCol, dataTableCol, serialSetTableCol,
                directRequiredForTableCol, directDependsOnTableCol,
                totalRequiredForTableCol, totalDependsOnTableCol);
        statusTableCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        remarkTableCol.setCellValueFactory(new PropertyValueFactory<>("checkBoxTask"));
    }
    public void setComboBox(){
        scratchOrIncremental.getItems().add("Scratch");
        scratchOrIncremental.getItems().add("Incremental");
        scratchOrIncremental.setValue("Scratch");
    }
    public void setRunAndPauseButton(){
        runButton.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
        pauseButton.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
        isRunSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (isRunSelected.getValue())
                runButton.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
            else
                runButton.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
        });
        isPauseSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (isPauseSelected.getValue())
                pauseButton.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
            else
                pauseButton.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
        });
    }
    public void setToggles(){
        compilerToggle.setSelected(true);
        compilerToggle.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1)");
        simulationToggle.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
    }
    public void setSpinner(){
        ProcessingTimeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
        successSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        successWithWarningSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        numOfTreadsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }
    public void clickOnRow() {
        tableView.setRowFactory( tv -> {
            TableRow<targetTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    List<String> list = new ArrayList<>();
                    Target t = mainController.getEngine().getMap().get(tableView.getSelectionModel().getSelectedItem().getName());
                    String process = "";
                    String name = t.getName();
                    String type = t.getType().toString();

                    String serialSets;
                    Map<String, Set<String>> serialSetsMap = mainController.getEngine().getAllSerialSetsWithYou(t.getName());
                    if (serialSetsMap.size() == 0)
                        serialSets = "doesn't belong to any serial set";
                    else
                        serialSets = serialSetsMap + "\n";

                    if (t.getIsRunning())
                        process = t.getWaitingTime();
                    else{
                        switch (t.getStatus())
                        {
                            // skipped waiting skipped in-process finished
                            case Waiting:
                                process = "is waiting: " + t.getWaitingTime() + " ms\n";
                                mainController.getEngine().whatIf(name,list , engine.Dependence.DEPENDS_ON);
                                process += "is waiting for " + list + " targets";
                                break;

                            case Skipped:
                                mainController.getEngine().whatIf(name,list , engine.Dependence.DEPENDS_ON);
                                process += list.stream()
                                        .filter(e -> mainController.getEngine().getMap().get(e).getStatus().equals(Target.Status.Failure))
                                        .toString();
                                break;

                            case Failure:
                                process = Target.Status.Failure.toString();
                                break;

                            case Success:
                                process = Target.Status.Success.toString();
                                break;
                        }
                    }

                    new targetInfoMain(name, type, serialSets, process);
                }
            });
            return row ;
        });
    }

        ///
    @FXML void clearAction(ActionEvent event) {
        ObservableList<targetTable> data = tableView.getItems();
        for (targetTable p : data)
        {
            if(p.getCheckBoxTask().isSelected())
                p.getCheckBoxTask().setSelected(false);
        }
    }
    @FXML void pauseTask(ActionEvent event) {
        isPauseSelected.setValue(true);
        isRunSelected.setValue(false);
    }
    @FXML void runTask(ActionEvent event) {
        try{
            boolean fromScratch;
            isRunSelected.setValue(true);
            isPauseSelected.setValue(false);
            if (scratchOrIncremental.getValue().equals("scratch"))
                fromScratch = true;
            else
                fromScratch = false;
            mainController.getEngine().runTask(ProcessingTimeSpinner.getValue(),true,successSpinner.getValue(),successWithWarningSpinner.getValue(),fromScratch);
            // uiAdapter, () -> toggleTaskButtons(false));
        }
        catch (Exception e){new errorMain(e);}


    }
    @FXML void compilerToggleSelected(ActionEvent event) {
        isCompiler.set(true);
    }
    @FXML void simulationToggleSelected(ActionEvent event) {
        isCompiler.set(false);
    }

    ///fxml member
    @FXML private TableView<targetTable> tableView;
    @FXML private TableColumn<targetTable,Boolean> remarkTableCol;
    @FXML private TableColumn<targetTable, String> nameTableCol;
    @FXML private TableColumn<targetTable, Target.Type> typeTableCol;
    @FXML private TableColumn<targetTable, Integer> directRequiredForTableCol;
    @FXML private TableColumn<targetTable, Integer> totalRequiredForTableCol;
    @FXML private TableColumn<targetTable, Integer> directDependsOnTableCol;
    @FXML private TableColumn<targetTable, Integer> totalDependsOnTableCol;
    @FXML private TableColumn<targetTable, String> dataTableCol;
    @FXML private TableColumn<targetTable, String> statusTableCol;
    @FXML private TableColumn<targetTable, Integer> serialSetTableCol;
    @FXML private Button clearButton;
    @FXML private ComboBox<String> scratchOrIncremental;
    @FXML private ToggleButton compilerToggle;
    @FXML private ToggleButton simulationToggle;
    @FXML private ProgressBar progressBarTask;
    @FXML private Button pauseButton;
    @FXML private Button runButton;
    @FXML private HBox simulationBox;
    @FXML private Spinner<Integer> ProcessingTimeSpinner;
    @FXML private Spinner<Integer> successSpinner;
    @FXML private Spinner<Integer> successWithWarningSpinner;
    @FXML private Spinner<Integer> numOfTreadsSpinner;
    @FXML private CheckBox withRequired;
    @FXML private CheckBox withDepend;
}
