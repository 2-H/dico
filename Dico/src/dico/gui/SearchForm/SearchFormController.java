/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.SearchForm;

import dico.ClassFactory;
import dico.ObjectFactory;
import dico.models.ObjectModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class SearchFormController implements Initializable {

    @FXML
    private ListView listFriend;
    @FXML
    private ListView listEnemies;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox comboObjects;
    @FXML
    private ComboBox comboClasses;

    @FXML
    public void Search() {

    }

    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        combo.getItems().clear();
        for (String str : myArrStr) {
            combo.getItems().add(str);
        }
    }
    @FXML
    public void getObjects() {
        String selectedClass = comboClasses.getSelectionModel().getSelectedItem().toString();
        ArrayList<ObjectModel> objList = ObjectFactory.Instance.Objects;
        ArrayList<String> list = new ArrayList<>();
        
        for (ObjectModel o : objList) {
            if (o.getClassModel().getName().equals(selectedClass)) {
                list.add(o.getVariableName());
            }
        }
        addToComboBox(comboObjects, list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addToComboBox(comboClasses, ClassFactory.Instance.GetClassNames());
    }

    public static void main(String[] args) {
        
        launch(args);
    }

}
