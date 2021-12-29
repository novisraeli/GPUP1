package FXML.main;
import FXML.file.fileController;
import FXML.path.pathController;
import FXML.setting.settingController;
import FXML.table.tableController;
import FXML.task.taskController;
import engine.*;
import graphViz.GraphViz;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import target.Target;
import target.targetTable;

import java.util.ArrayList;
import java.util.List;


public class mainAppController {
    private List<Rectangle> recList = new ArrayList<>();
    private int time = 350;
    private String toggleColor = "-fx-background-color: linear-gradient(#2A5058, #61a2b1)";
    private final engine engine = new engineImpl();
    private final SimpleBooleanProperty isFileSelected;
    public ObservableList<targetTable> items = FXCollections.observableArrayList();

    public mainAppController() {////
        isFileSelected = new SimpleBooleanProperty(false);
        isFileSelected.addListener((a,b,isSelected)->{
            if (isSelected)
            {
                GraphViz graph = new GraphViz();
                graph.createDotGraph(graph.makeDotFormat(engine.getMap()),"gpup12");
            }
        });
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
    public void setTreadsSpinner() {
        taskComponentController.setTreadsSpinner();
    }
    public void setToggleColor(String toggleColor){this.toggleColor = toggleColor;}
    public String getToggleColor(){return toggleColor;}
    public void setTime(int time){this.time = time;}

    @FXML public void fileTabSelected(){
        TranslateTransition openNav;
        openNav = new TranslateTransition(new Duration(time), fileComponent);
        openNav.setToX(0);
        fileComponent.setTranslateX(-(fileComponent.getWidth()));
        openNav.play();
    }
    @FXML public void tableTabSelected(){
        TranslateTransition openNav;
        openNav = new TranslateTransition(new Duration(time), tableComponent);
        openNav.setToX(0);
        tableComponent.setTranslateX(-(tableComponent.getWidth()));
        openNav.play();
    }
    @FXML public void graphTabSelected(){
        TranslateTransition openNav;
        openNav = new TranslateTransition(new Duration(time), settingAnchorPane);
        openNav.setToX(0);
        settingAnchorPane.setTranslateX(-(settingAnchorPane.getWidth()));
        openNav.play();
    }
    @FXML public void pathTabSelected(){
        TranslateTransition openNav;
        openNav = new TranslateTransition(new Duration(time), pathComponent);
        openNav.setToX(0);
        pathComponent.setTranslateX(-(pathComponent.getWidth()));
        openNav.play();
    }
    @FXML public void taskTabSelected(){
        TranslateTransition openNav;
        openNav = new TranslateTransition(new Duration(time), taskComponent);
        openNav.setToX(0);
        taskComponent.setTranslateX(-(taskComponent.getWidth()));
        openNav.play();
    }
    @FXML public void settingTabSelected(){
        TranslateTransition openNav;
        openNav = new TranslateTransition(new Duration(time), settingComponent);
        openNav.setToX(0);
        settingComponent.setTranslateX(-(settingComponent.getWidth()));
        openNav.play();
    }
    public void fileAnimation() {
        if(settingComponentController.animation())
        {
            recList.add(oneFileAnimation(195*1.5));
            recList.add(oneFileAnimation(195*2.5));
            recList.add(oneFileAnimation(195*3.5));
            recList.add(oneFileAnimation(195*4.5));
            fileComponent.getChildren().addAll(recList);
        }
    }
    public Rectangle oneFileAnimation(double x) {
        final Rectangle rectPath = new Rectangle (0, 0, 40, 40);
        rectPath.setArcHeight(10);
        rectPath.setArcWidth(10);
        rectPath.setFill(new ImagePattern(new Image("/FXML/utility/file icon.png")));
        Path path = new Path();
        path.getElements().add(new MoveTo(fileComponentController.getFileButton().getLayoutX(),fileComponentController.getFileButton().getLayoutY()));
        path.getElements().add(new CubicCurveTo(fileComponentController.getFileButton().getLayoutX(), fileComponentController.getFileButton().getLayoutY(), x, 0, x, 0));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(path);
        pathTransition.setNode(rectPath);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
        pathTransition.statusProperty().addListener((a,b,c)->{
            if (c == Animation.Status.STOPPED) {
                fileComponent.getChildren().remove(recList.get(0));
                fileComponent.getChildren().remove(recList.get(1));
                fileComponent.getChildren().remove(recList.get(2));
                fileComponent.getChildren().remove(recList.get(3));
                recList.clear();
            }
        });
        return rectPath;
    }

    /// tab fxml
    @FXML private AnchorPane settingAnchorPane;
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

