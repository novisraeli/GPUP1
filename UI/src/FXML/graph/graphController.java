package FXML.graph;
import FXML.main.mainAppController;
import graphViz.GraphViz;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class graphController {
    private mainAppController mainController;
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }

    @FXML private Button loadFileButton;
    @FXML private Pane paneImage;
    @FXML private TextField nameOfFile;
    @FXML private HBox hbox;

    @FXML void loadFileButton(ActionEvent event) {
           File selectedDirectory = new DirectoryChooser().showDialog(new Stage());
           changeBackgroundGraph(selectedDirectory.getAbsolutePath());
    }
    public File createDotGraph(String pathTemp) {
        GraphViz gv = new GraphViz(pathTemp + "/", nameOfFile.getText());
        String dotFormat = gv.makeDotFormat(mainController.getEngine().getMap() , mainController.getEngine().getSerialSets());
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        String pathImage = pathTemp + "/" + nameOfFile.getText() + ".png";
        File out = new File(pathImage);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), ".png" ,nameOfFile.getText() ), out);
        return out;
    }
    public void changeBackgroundGraph(String pathTemp){
        //
        File f = createDotGraph(pathTemp);
        Image image = new Image(f.toURI().toString());
        // image = new Image("/graphViz/temp/image.png", graphAnchorPane.getWidth(), graphAnchorPane.getHeight(), false, false);
        BackgroundImage bImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1300, 700, true, true, true, true));
        Background bGround = new Background(bImg);
        paneImage.setBackground(bGround);
        //changeGraph.set(false);
    }
    public void changeBackgroundColor(String newColorString){
        hbox.setStyle(newColorString);
    }

}



/*
public class graphController {
    private mainAppController mainController;
    //private SimpleBooleanProperty changeGraph;


    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }
    @FXML public void initialize() {

    }
    @FXML void buttonGraph(ActionEvent event) {
     //   DirectoryChooser directoryChooser = new DirectoryChooser();
     //   directoryChooser.setInitialDirectory(new File("src"));
     //   File selectedDirectory = directoryChooser.showDialog(new Stage());
   //     createDotGraph(selectedDirectory.getAbsolutePath());
    }

    public File createDotGraph(String pathTemp)
    {
        GraphViz gv = new GraphViz(pathTemp, nameOfFile.getText());
        String dotFormat = gv.makeDotFormat(mainController.getEngine().getMap() , mainController.getEngine().getSerialSets());
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        String type = "png";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        String pathImage = pathTemp +"image." + type;
        File out = new File(pathImage);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
        return out;
    }
    public void changeBackgroundGraph(String pathTemp){
        File f = createDotGraph(pathTemp);
        Image image = new Image(f.toURI().toString());
        // image = new Image("/graphViz/temp/image.png", graphAnchorPane.getWidth(), graphAnchorPane.getHeight(), false, false);
        BackgroundImage bImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1300, 750, true, true, true, true));
        Background bGround = new Background(bImg);
        pane.setBackground(bGround);
        //changeGraph.set(false);
    }
    public void changeBackgroundColor(String newColorString){
        hbox.setStyle(newColorString);
    }



    @FXML void buttonGraph(ActionEvent event) {}
    @FXML private HBox hbox;
    @FXML private Pane pane;
    @FXML private TextField nameOfFile;

}
*/