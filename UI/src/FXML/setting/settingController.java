package FXML.setting;

import FXML.main.mainAppController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class settingController {
    private mainAppController mainController;
    @FXML public void initialize() {
            durationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1500, 350));
            ofOffButton.setSelected(true);
        }

    public void setMainController(mainAppController mainController) {
            this.mainController = mainController;

            tabColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeTabColor(tabColor.getValue());
            });

            buttonColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeButtonColor(buttonColor.getValue());
            });

            toggleColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.setToggleColor("-fx-background-color: rgb(" + toggleColor.getValue().getRed()*255 + "," +  toggleColor.getValue().getGreen()*255 +"," +  toggleColor.getValue().getBlue()*255 +")");
                mainController.changeToggleColor();
            });

            combBoxColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeComboBoxColor(combBoxColor.getValue());
            });

            tableColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeTableColor(tableColor.getValue());
            });

            backgroundColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeBackgroundColor(backgroundColor.getValue());
                String newColorString = "-fx-background-color: rgb(" + backgroundColor.getValue().getRed()*255 + "," +  backgroundColor.getValue().getGreen()*255 +"," +  backgroundColor.getValue().getBlue()*255 +")";
                borderPaneSetting.setStyle(newColorString);
                vbox.setStyle(newColorString);
                hbox.setStyle(newColorString);
            });

           /* durationSpinner.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.time = newString;
            });

            */
            durationSpinner.disableProperty().bind(ofOffButton.selectedProperty().not());
            ofOffButton.selectedProperty().addListener((obs, oldString, isSelected) -> {
                if (!ofOffButton.isSelected()) {
                    mainController.setTime(1);
                }
                else {
                    mainController.setTime(durationSpinner.getValue());
                }
            });
        }
    public boolean animation(){return ofOffButton.isSelected();}
    @FXML private HBox hbox;
    @FXML private VBox vbox;
    @FXML private ColorPicker backgroundColor;
    @FXML private ColorPicker tableColor;
    @FXML private ColorPicker buttonColor;
    @FXML private ColorPicker toggleColor;
    @FXML private ColorPicker combBoxColor;
    @FXML private ColorPicker tabColor;
    @FXML private BorderPane borderPaneSetting;
    @FXML private Spinner<Integer> durationSpinner;
    @FXML private RadioButton ofOffButton;
}
