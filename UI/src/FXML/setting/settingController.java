package FXML.setting;

import FXML.main.mainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

public class settingController {
    private mainAppController mainController;
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }

    @FXML private void initialize(){

    }


    public void setColorBackground(){
        buttonColorComboBox.getItems().add("default");
        buttonColorComboBox.getItems().add("bar");
    }


    //////////////////////////////////////////////////////////////
    @FXML private ComboBox<?> backGroundColorComboBox;
    @FXML private ComboBox<?> tableColorComboBox;
    @FXML private Spinner<?> tableFontSizeSpinner;
    @FXML private ComboBox<?> tableFontColorComboBox;
    @FXML private ComboBox<String> buttonColorComboBox;
    @FXML private Spinner<?> buttonFontSizeSpinner;
    @FXML private ComboBox<?> buttonFontColorComboBox;
}
