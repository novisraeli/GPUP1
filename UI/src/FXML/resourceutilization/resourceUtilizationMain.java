package FXML.resourceutilization;

import FXML.error.errorController;
import FXML.error.errorMain;
import information.infoThread;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class resourceUtilizationMain {
    public final static String FXML = "/FXML/resourceutilization/resource utilization.fxml";
        public resourceUtilizationMain(ObservableList<infoThread> list){
            try {
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Resource Utilization");
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(FXML);
                fxmlLoader.setLocation(url);
                Parent root = fxmlLoader.load(url.openStream());
                Scene scene = new Scene(root, 600, 400);
                primaryStage.setScene(scene);
              //  primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.show();
                resourceUtilizationController controller = fxmlLoader.getController();
                controller.show(list);
            }
            catch (Exception ex){
                new errorMain(ex);
            }
        }
}
