import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;

public class main extends Application {
    public final static String APP_FXML_INCLUDE_RESOURCE = "/FXML/main/mainApp.fxml";
   /* public static void main(String[]args){
        UIimpl u = new UIimpl();
        u.mainMenu();
    }*/

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("G.P.U.P");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(APP_FXML_INCLUDE_RESOURCE);
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());

        Scene scene = new Scene(root, 605, 440);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
