package controller;

import engine.engineImpl;
import engine.engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import target.Target;
import target.Targets;
import target.targetTable;

import java.io.File;
import java.util.*;

public class pathController {
    engineImpl e = new engineImpl();
    @FXML
    private ComboBox<engine.Dependence> depenceComboBox;
    @FXML
    private Text sourceText;
    @FXML
    private Text destinationText;
    @FXML
    private Button switchButton;
    @FXML
    private Button clearButton;
    @FXML
    private TableColumn<targetTable, String> nameTableCol;
    @FXML
    private TableColumn<targetTable, String> typeTableCol;
    @FXML
    private TableColumn<targetTable, Integer> directRequiredForTableCol;
    @FXML
    private TableColumn<targetTable, Integer> totalRequiredForTableCol;
    @FXML
    private TableColumn<targetTable, Integer> directDepemdsOnTableCol;
    @FXML
    private TableColumn<targetTable, Integer> TotalDepemdsOnTableCol;
    @FXML
    private TableColumn<targetTable, String> dataTableCol;
    @FXML
    private TableColumn<targetTable,Boolean> remarkTableCol;
    @FXML
    private ListView<targetTable> pathListView;
    @FXML
    private TableView<targetTable> tableView;

    @FXML
    private Button button;

    @FXML
    void set(ActionEvent event) {
        updateTable();
    }
    @FXML
    public void initialize() {
        depenceComboBox.getItems().add(engine.Dependence.DEPENDS_ON);
        depenceComboBox.getItems().add(engine.Dependence.REQUIRED_FOR);
    }

    @FXML
    void clearAction(ActionEvent event) {
        ObservableList<targetTable> data2 = tableView.getItems();
        for (targetTable p : data2)
        {
            if(p.getRemark().isSelected())
                p.getRemark().setSelected(false);
        }
    }

    @FXML
    void switchAction(ActionEvent event) {
        Text temp = sourceText;
        sourceText = destinationText;
        destinationText = temp;
    }

    void updateTable() {
        try {
            ObservableList<targetTable> data =  FXCollections.observableArrayList();
            String path =new FileChooser().showOpenDialog(new Stage()).getPath();
            e.loadFile(path);
            Map<String, Target> map= e.getMap();
            for (String keys : map.keySet())
            {
                data.add(new targetTable(map.get(keys)));
            }

            nameTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("name")
            );
            typeTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("type")
            );

            dataTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("userData")
            );

            remarkTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("remark")
            );

            //////
            directRequiredForTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("age")
            );

            totalRequiredForTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("remark")
            );
            directDepemdsOnTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("remark")
            );

            TotalDepemdsOnTableCol.setCellValueFactory(
                    new PropertyValueFactory<>("remark")
            );
            System.out.println(dataTableCol);
            tableView.setItems(data);
        }
        catch (Exception e){}
    }
}
