package FXML.setting;

import FXML.main.mainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;

public class settingController {
        private mainAppController mainController;

        public void setMainController(mainAppController mainController) {
            this.mainController = mainController;

            tabColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeTabColor(tabColor.getValue());
            });

            buttonColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.changeButtonColor(buttonColor.getValue());
            });

            toggleColor.valueProperty().addListener((obs, oldString, newString) -> {
                mainController.toggleColor = "-fx-background-color: rgb(" + toggleColor.getValue().getRed()*255 + "," +  toggleColor.getValue().getGreen()*255 +"," +  toggleColor.getValue().getBlue()*255 +")";
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
            });
        }
        @FXML private ColorPicker backgroundColor;
        @FXML private ColorPicker tableColor;
        @FXML private ColorPicker buttonColor;
        @FXML private ColorPicker toggleColor;
        @FXML private ColorPicker combBoxColor;
        @FXML private ColorPicker tabColor;
        @FXML private BorderPane borderPaneSetting;
    }
