package FXML.task.processing;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class finishSumUp {
    public final static String TARGETINFO_FXML = "/FXML/task/processing/targetInformationTask.fxml";
    public finishSumUp(int skipped , int finishedSuccessfully , int finishedWarning ,  int finishedWithFailure) {
        try {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Task Sum Up");
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource(TARGETINFO_FXML);
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load(url.openStream());
            Scene scene = new Scene(root, 400, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
            targetInfoTaskController controller = fxmlLoader.getController();
            controller.setSumUp(""+skipped,""+finishedSuccessfully,""+finishedWarning,""+finishedWithFailure);
        }
        catch (Exception ex){}
    }
}
