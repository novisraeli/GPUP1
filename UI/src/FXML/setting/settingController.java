package FXML.setting;

import FXML.main.mainAppController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

public class settingController {
    private mainAppController mainController;

    @FXML
    private ComboBox<?> backGroundColorComboBox;

    @FXML
    private ComboBox<?> tableColorComboBox;

    @FXML
    private Spinner<?> tableFontSizeSpinner;

    @FXML
    private ComboBox<?> tableFontColorComboBox;

    @FXML
    private ComboBox<?> buttonColorComboBox;

    @FXML
    private Spinner<?> buttonFontSizeSpinner;

    @FXML
    private ComboBox<?> buttonFontColorComboBox;

    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }
}
