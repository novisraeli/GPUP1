package FXML.error;

import FXML.main.mainAppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class errorController {

    @FXML
    private Text errorText;

    public void error(Exception e) throws IOException {
        errorText.setText(e.toString());
    }
}
