package FXML.task;
import FXML.error.errorMain;
import FXML.main.mainAppController;
import FXML.resourceutilization.resourceUtilizationMain;
import FXML.task.processing.finishSumUp;
import FXML.task.processing.targetInfoMain;
import engine.engine;
import information.infoThread;
import javafx.application.Platform;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import target.Target;
import target.targetTable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class taskController {
    private final SimpleBooleanProperty runTask;
    private mainAppController mainController;
    private final SimpleBooleanProperty isRunSelected;
    private final SimpleBooleanProperty isPauseSelected;
    private final SimpleBooleanProperty isCompiler;
    private final ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private final IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private boolean showFinish;

    public taskController(){
        isRunSelected = new SimpleBooleanProperty(false);
        isPauseSelected = new SimpleBooleanProperty(false);
        isCompiler = new SimpleBooleanProperty(true);
        runTask = new SimpleBooleanProperty(false);
    }
    @FXML public void initialize() {
        setRunAndPauseButton();
        setComboBox();
        clickOnRow();
        runButton.setDisable(true);
        numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> displayRunButton());

        simulationBox.disableProperty().bind(isCompiler);
        compilerBox.disableProperty().bind(isCompiler.not());
        isCompiler.addListener((obs, oldSelectedCount, newSelectedCount) -> {
            if (isCompiler.getValue()) {
                compilerToggle.setStyle(mainController.getToggleColor());
                compilerToggle.setOpacity(1);
                simulationToggle.setOpacity(0.3);
            }
            else {
                simulationToggle.setStyle(mainController.getToggleColor());
                simulationToggle.setOpacity(1);
                compilerToggle.setOpacity(0.3);
            }
        });
        selectAll.selectedProperty().addListener((obs, oldSelectedCount, newSelectedCount)->{
            if (newSelectedCount){
                ObservableList<targetTable> data = tableView.getItems();
                for (targetTable p : data)
                    p.getCheckBoxTask().setSelected(true);
            }
        });
        pauseButton.disableProperty().bind(runTask.not());
        resumeButton.disableProperty().bind(runTask.not());
    }
/// set all the details to run this task pane
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
        setToggles();
        setSpinner();
    }
    public void show() {
        tableView.setItems(mainController.getObservableListTask());
        for (int i = 0 ; i< mainController.getObservableListTask().size();++i)
            configureCheckBoxTask(mainController.getObservableListTask().get(i).getCheckBoxTask());
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
        runButton.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
        pauseButton.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
        resumeButton.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
    }
    public void setToggles(){
        compilerToggle.setSelected(true);
        compilerToggle.setStyle(mainController.getToggleColor());
        simulationToggle.setStyle("-fx-background-color: linear-gradient(#61a2b1, #2A5058)");
    }
    public void setSpinner(){
        ProcessingTimeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 1000));
        successSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 100));
        successWithWarningSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        spinnerManuallyTyping(ProcessingTimeSpinner);
        spinnerManuallyTyping(successSpinner);
        spinnerManuallyTyping(successWithWarningSpinner);
    }
    public void spinnerManuallyTyping (Spinner <Integer> spinner){
        spinner.setEditable(true);
        TextFormatter formatter3 = new TextFormatter(spinner.getValueFactory().getConverter(), spinner.getValueFactory().getValue());
        spinner.getEditor().setTextFormatter(formatter3);
        spinner.getValueFactory().valueProperty().bindBidirectional(formatter3.valueProperty());
    }
    public void setTreadsSpinner() {
        numOfTreadsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, mainController.getEngine().getMaxThreads(), 1));
    }

    /**
     * if "root" target - select all the target with the dependence
     * @param d
     */
    public void getAllTargetWith(engine.Dependence d ){
        List<String> newList = new ArrayList();
        try{
            for (int i = 0 ; i < mainController.getObservableListTask().size();++i) {
                if (mainController.getObservableListTask().get(i).getCheckBoxTask().isSelected()) {
                    mainController.getEngine().whatIf(mainController.getObservableListTask().get(i).getName(), newList, d);
                    for (int k = 0; k < mainController.getObservableListTask().size(); ++k)
                        if (newList.contains(mainController.getObservableListTask().get(k).getName())) {
                            mainController.getObservableListTask().get(k).getCheckBoxTask().setSelected(true);
                        }
                }
            }
        }

        catch (Exception e){
            new errorMain(e);
        }
    }
    /**
     * double-click on row - show all the information about the target
     */
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

                    new targetInfoMain(name, type, serialSets, process,t.getSetDependsOn() ,t.getSetRequiredFor() );
                }
            });
            return row ;
        });
    }
    /**
     * clear all the selected checkBox
     * @param event
     */
    @FXML void clearAction(ActionEvent event) {
        selectAll.setSelected(false);
        ObservableList<targetTable> data = tableView.getItems();
        for (targetTable p : data)
        {
            if(p.getCheckBoxTask().isSelected())
                p.getCheckBoxTask().setSelected(false);
        }
    }
    /**
     * manage when the run button show -
     * if the compiler selected - only when the target and source folder choose and target selected
     * if the simulation selected - only if target selected
     */
    public void displayRunButton(){
        if (compilerToggle.isSelected()) {
            if  (sourceFolderText.getText().equals("") || targetFolderText.getText().equals("") || selectedCheckBoxes.size() == 0)
                runButton.setDisable(true);
            else
                runButton.setDisable(false);
        }
        else runButton.setDisable(selectedCheckBoxes.size() == 0);
    }

    /**
     * manage the simulation and compiler Toggles
     * @param event
     */
    @FXML void compilerToggleSelected(ActionEvent event) {
        isCompiler.set(true);
        compilerToggle.setSelected(true);
        simulationToggle.setSelected(false);
        displayRunButton();
    }
    @FXML void simulationToggleSelected(ActionEvent event) {
        isCompiler.set(false);
        simulationToggle.setSelected(true);
        compilerToggle.setSelected(false);
        displayRunButton();
    }
    /**
     * choose source folder from directory chooser
     * @param event
     */
    @FXML void sourceFolderChooserTask(ActionEvent event) {
        File selectedDirectory = new DirectoryChooser().showDialog(new Stage());
        sourceFolderText.setText(selectedDirectory.getPath());
        displayRunButton();
    }
    /**
     * choose target folder from directory chooser
     * @param event
     */
    @FXML void targetFolderChooserTask(ActionEvent event) {
        File selectedDirectory = new DirectoryChooser().showDialog(new Stage());
        targetFolderText.setText(selectedDirectory.getPath());
        displayRunButton();
    }
/// run task
    @FXML void runTask(ActionEvent event) {
    try{
        showFinish =false;
        boolean fromScratch = scratchOrIncremental.getValue().equals("scratch");;
        List<Target> targetsToRun=new ArrayList<>();
        for(targetTable t:tableView.getItems()){
            if(t.getCheckBoxTask().isSelected()){
                targetsToRun.add(mainController.getEngine().getMap().get(t.getName()));
            }
        }
        Thread thread;
        if(simulationToggle.isSelected()) {
            thread = new Thread("runSimulation"){
                public void run(){
                    try{
                        mainController.getEngine().taskSetUp(ProcessingTimeSpinner.getValue(),
                                randomCheckBox.isSelected(), (float) successSpinner.getValue()/100, (float)successWithWarningSpinner.getValue()/100, fromScratch, "simulation", numOfTreadsSpinner.getValue(), targetsToRun);
                    }
                    catch (Exception e){new errorMain(e);}
                }
            };
            thread.start();
            threadForUpdateInformation();
        }
        else if (compilerToggle.isSelected() && !sourceFolderText.getText().equals("") && !targetFolderText.getText().equals("")) {
            thread = new Thread("runCompiler"){
                public void run(){
                    try{
                        mainController.getEngine().compile(fromScratch,"compilation",numOfTreadsSpinner.getValue(),targetsToRun,targetFolderText.getText(),sourceFolderText.getText());
                    }
                    catch (Exception e){new errorMain(e);}
                }
            };
            thread.start();
            threadForUpdateInformation();
        }

    }
    catch (Exception e){new errorMain(e);}
}
    @FXML void pauseTask(ActionEvent event) {
        isPauseSelected.setValue(true);
        isRunSelected.setValue(false);
        mainController.getEngine().stopThreads();
        messageText.setText("Pause Task");
    }
    @FXML void resumeTask(ActionEvent event) {
        isPauseSelected.setValue(false);
        isRunSelected.setValue(true);
        mainController.getEngine().activateThreads();
        threadForUpdateInformation();
    }
//// update information during the running of task
    public void updateInformation(Double x){
        refreshTable();
        progressBarTask.setProgress(x);
        if (x == 1 && !showFinish)
            finish();
    }
    public void updateInformationTable(ObservableList<targetTable> newItems){
        for(targetTable t : newItems){
            Target old = mainController.getEngine().getMap().get(t.getName().toUpperCase());
            t.SetStatus(old.getStatus());
            t.setFailReason(old.getFailReason());
            t.setSimTimeString(old.getSimTimeString());
            t.setIsInQueue(old.getIsInQueue());
            t.setIsRunning(old.getIsRunning());
            t.setWaitingTime(old.getWaitingTime());
            t.setFailReason(old.getFailReason());
        }
    }
    private void updateStartTask(){
        progressTaskBox.setDisable(false);
        isRunSelected.setValue(true);
        isPauseSelected.setValue(false);
        runTask.set(true);
        messageText.setText("in Progress");
        progressBarTask.setProgress(0);
        typeOfRunningText.setText(scratchOrIncremental.getValue());
    }
    public void updateInformationThreadSumUp(){
        long time = 0;
        ObservableList<infoThread> infoThreadListIN = FXCollections.observableArrayList();
        ObservableList<infoThread> infoThreadListOUT = FXCollections.observableArrayList();
        for(infoThread t:mainController.getEngine().getInfoThreadList()) {
            infoThreadListIN.add(t);
            time = t.getTimeAbsolut();
        }
        ///infoThreadList.add(new infoThread(infoThread.InOrOut.IN, startTime ,getTimeFromStart() ,threadsNum-workingThreads));
        for(String st : mainController.getEngine().getMap().keySet()) {
            infoThread f = mainController.getEngine().getMap().get(st).getInfoThread();

            infoThread newInfo = new infoThread(infoThread.InOrOut.OUT,time,makeTimeString(time,f.getCurrTime()),f.getFreeThread());
            infoThreadListOUT.add(newInfo);
        }

        infoThreadListIN.addAll(infoThreadListOUT);
        new resourceUtilizationMain(infoThreadListIN);
    }
    public void threadForUpdateInformation(){
        Thread update = new Thread("update") {
            public void run() {
                sleepForSomeTime();
                Platform.runLater(() -> updateStartTask());
                while (mainController.getEngine().getPrecentageDone() != 1 && mainController.getEngine().getIsTaskRunning()) {
                    sleepForSomeTime();
                    Platform.runLater(() -> updateInformation(mainController.getEngine().getPrecentageDone()));
                }
                Platform.runLater(() -> updateInformation(mainController.getEngine().getPrecentageDone()));
            }
        };
        update.start();
    }
    public void finish(){
        showFinish = true;
        updateInformationThreadSumUp();
        runTask.set(false);
        messageText.setText("Done");
        ///////////
        int skipped = 0;
        int finishedSuccessfully = 0;
        int finishedWarning = 0;
        int finishedWithFailure = 0;
        for(String st : mainController.getEngine().getMap().keySet()){
            Target t = mainController.getEngine().getMap().get(st);
            if (t.getStatus() == Target.Status.Success)
                finishedSuccessfully ++;
            else if (t.getStatus() == Target.Status.Failure)
                finishedWithFailure ++;
            else if (t.getStatus() == Target.Status.Warning)
                finishedWarning ++;
            else if (t.getStatus() == Target.Status.Skipped)
                skipped ++;
        }

        new finishSumUp(skipped, finishedSuccessfully, finishedWarning, finishedWithFailure);
    }
    private void refreshTable(){
        Map<String,Target> b = mainController.getEngine().getMap();

        ObservableList<targetTable> newItems = FXCollections.observableArrayList();
        newItems.addAll(tableView.getItems());
        updateInformationTable(newItems);
        tableView.getItems().clear();
        tableView.setItems(newItems);
        mainController.setObservableListTask(newItems);
    }
    public String makeTimeString(long timeFromStart,long curr ){
        long temp= curr-timeFromStart;
        long millis = temp % 1000;
        long second = (temp / 1000) % 60;
        long minute = (temp / (1000 * 60)) % 60;
        long hour = (temp / (1000 * 60 * 60)) % 24;
        String Time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
        return Time;
    }
    private void sleepForSomeTime() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
    }
/// manage color
public void changeButtonColor(String newColorString){
    runButton.setStyle(newColorString);
    clearButton.setStyle(newColorString);
    pauseButton.setStyle(newColorString);
    sourceFolderChooser.setStyle(newColorString);
    targetFolderChooser.setStyle(newColorString);
    resumeButton.setStyle(newColorString);
}
    public void changeToggleColor(){
        compilerToggle.setStyle(mainController.getToggleColor());
        simulationToggle.setStyle(mainController.getToggleColor());
    }
    public void changeComboBoxColor(String newColorString){
        scratchOrIncremental.setStyle(newColorString);
    }
    public void changeTableColor(String newColorString){
        tableView.setStyle(newColorString);
        remarkTableCol.setStyle(newColorString);
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
        statusTableCol.setStyle(newColorString);
    }
    public void changeBackgroundColor(String newColorString){
        simulationBox.setStyle(newColorString);
        taskMangerTitle.setStyle(newColorString);
        hboxTask.setStyle(newColorString);
        gridPaneTAsk.setStyle(newColorString);
        gridPaneTask2.setStyle(newColorString);
        hbox2.setStyle(newColorString);
        hbox3.setStyle(newColorString);
        vbox1.setStyle(newColorString);
        vbox2.setStyle(newColorString);
        hbox5.setStyle(newColorString);
        vbox3.setStyle(newColorString);
        vbox4.setStyle(newColorString);
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
    @FXML private TableColumn<targetTable, Integer> dependsOnTableCol;
    @FXML private TableColumn<targetTable, String> requiredForTableCol;
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
    @FXML private Label taskMangerTitle;
    @FXML private HBox hboxTask;
    @FXML private GridPane gridPaneTAsk;
    @FXML private GridPane gridPaneTask2;
    @FXML private HBox hbox2;
    @FXML private VBox hbox3;
    @FXML private VBox vbox1;
    @FXML private VBox vbox2;
    @FXML private HBox hbox5;
    @FXML private VBox vbox3;
    @FXML private VBox vbox4;
    @FXML private VBox progressTaskBox;
    ////
    @FXML private CheckBox selectAll;
    @FXML private Button sourceFolderChooser;
    @FXML private Button targetFolderChooser;
    @FXML private Button resumeButton;
    @FXML private Text sourceFolderText;
    @FXML private Text targetFolderText;
    @FXML private VBox compilerBox;
    @FXML private Label messageText;
    @FXML private Label typeOfRunningText;
    @FXML private CheckBox randomCheckBox;
}