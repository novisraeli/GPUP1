package FXML.resourceutilization;
import FXML.error.errorMain;
import information.GraphInformation;
import information.infoThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class resourceUtilizationController {

    public void show(ObservableList<infoThread> list) {
            table.setItems(list);
    }
    @FXML public void initialize() {
        inCol.setCellValueFactory(new PropertyValueFactory<>("inOut"));
        abTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeAbsolutString"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeFromStart"));
        threadCol.setCellValueFactory(new PropertyValueFactory<>("freeThread"));
        targetInPool.setCellValueFactory(new PropertyValueFactory<>("targetInPool"));
    }

    @FXML private TableView<infoThread> table;
    @FXML private TableColumn<infoThread, String > inCol;
    @FXML private TableColumn<infoThread, String> abTimeCol;
    @FXML private TableColumn<infoThread, String> timeCol;
    @FXML private TableColumn<infoThread, String> threadCol;
    @FXML private TableColumn<?, ?> targetInPool;
}
