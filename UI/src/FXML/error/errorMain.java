package FXML.error;

import FXML.file.fileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class errorMain {

    public final static String ERROR_FXML = "/FXML/error/erroe.fxml";

    public errorMain(Exception e) {
        try {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Exception");
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(ERROR_FXML);
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, 400, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
            errorController controller = fxmlLoader.getController();
            controller.error(e);
        }
        catch (Exception ex){}
    }

}
