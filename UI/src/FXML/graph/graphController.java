package FXML.graph;
import FXML.main.mainAppController;
import graphViz.GraphViz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.Button;

public class graphController {
    private mainAppController mainController;
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }

    @FXML private Button loadFileButton;
    @FXML private ImageView imageView;
    @FXML private TextField nameOfFile;
    @FXML private HBox hbox;

    /**
     * choose directory from DirectoryChooser for the image and the viz doc and run graphviz
     * @param event
     */
    @FXML void loadFileButton(ActionEvent event) {
        File selectedDirectory = new DirectoryChooser().showDialog(new Stage());
        changeBackgroundGraph(selectedDirectory.getAbsolutePath());
    }

    /**
     *
     * @param pathTemp
     * @return viz file that contain
     */
    public File createDotGraph(String pathTemp) {
        GraphViz gv = new GraphViz(pathTemp + "/", nameOfFile.getText());
        String dotFormat = gv.makeDotFormat(mainController.getEngine().getMap() , mainController.getEngine().getSerialSets());
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        gv.decreaseDpi();
        gv.decreaseDpi();
        String pathImage = pathTemp + "/" + nameOfFile.getText() + ".png";
        File out = new File(pathImage);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), ".png"), out);
        return out;
    }
    public void changeBackgroundGraph(String pathTemp){
        File f = createDotGraph(pathTemp);
        Image image = new Image(f.toURI().toString(), 1300 , 600 ,false,false);
        imageView.setImage(image);
    }
    public void changeBackgroundColor(String newColorString){
        hbox.setStyle(newColorString);
    }

}

