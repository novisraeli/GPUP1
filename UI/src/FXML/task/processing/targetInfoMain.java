package FXML.task.processing;

import FXML.error.errorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Set;

public class targetInfoMain {
    public final static String TARGETINFO_FXML = "/FXML/task/processing/targetInformationTask.fxml";
    public targetInfoMain(String name , String type , String serialSets , String process, Set<String> setDependsOn, Set<String> setRequiredFor) {
        try {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Target Information - Task");
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(TARGETINFO_FXML);
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
            targetInfoTaskController controller = fxmlLoader.getController();
            controller.setInfo(name,type,serialSets,process, setDependsOn, setRequiredFor);
        }
        catch (Exception ex){}
    }
}
