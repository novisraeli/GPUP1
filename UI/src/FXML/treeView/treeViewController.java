package FXML.treeView;

import FXML.main.mainAppController;
import engine.engine;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import target.Target;

import java.util.*;

public class treeViewController {
    private mainAppController mainController;
    @FXML public void initialize() {
    }
    public void setMainController(mainAppController mainController) {
        this.mainController = mainController;
    }
    public void setTreeView(){
        TreeItem roots_to_leaves = new TreeItem("ROOTS TO LEAVES");
        TreeItem leaves_to_roots = new TreeItem("LEAVES TO ROOTS");
        Map<String, Target> targetMap = mainController.getEngine().getMap();

        for (String st : targetMap.keySet())
        {
            if(targetMap.get(st).getType() == Target.Type.ROOT) {
                TreeItem root = new TreeItem(targetMap.get(st).getName());
                roots_to_leaves.getChildren().add(root);
                tree(targetMap.get(st), root, engine.Dependence.DEPENDS_ON);
            }
            else if (targetMap.get(st).getType() == Target.Type.INDEPENDENTS){
                roots_to_leaves.getChildren().add(new TreeItem(targetMap.get(st).getName()));
                leaves_to_roots.getChildren().add(new TreeItem(targetMap.get(st).getName()));
            }
            else if(targetMap.get(st).getType() == Target.Type.LEAF) {
                TreeItem root = new TreeItem(targetMap.get(st).getName());
                leaves_to_roots.getChildren().add(root);
                tree(targetMap.get(st), root, engine.Dependence.REQUIRED_FOR);
            }
        }
        treeViewLeft.setRoot(roots_to_leaves);
        treeViewRight.setRoot(leaves_to_roots);
    }
    public void tree(Target target , TreeItem javaItem , engine.Dependence dependence){
        Set<String> tOneSet;
        if (dependence == engine.Dependence.DEPENDS_ON)
            tOneSet = target.getSetDependsOn();
        else
            tOneSet = target.getSetRequiredFor();

        if (tOneSet.size() == 0)
            return;

        else {
            for (String st : tOneSet) {  /// search in all DEPENDS_ON or REQUIRED_FOR for each target
                TreeItem Item = new TreeItem(mainController.getEngine().getMap().get(st).getName());
                javaItem.getChildren().add(Item);
                tree(mainController.getEngine().getMap().get(st) , Item, dependence);
            }

        }
    }
    public void changeBackgroundColor(String newColorString){
        treeViewRight.setStyle(newColorString);
        treeViewLeft.setStyle(newColorString);
    }
    @FXML private BorderPane borderPaneTreeView;
    @FXML private TreeView<String> treeViewLeft;
    @FXML private TreeView<String> treeViewRight;



}
