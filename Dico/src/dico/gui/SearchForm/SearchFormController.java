/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.SearchForm;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.ObjectFactory;
import dico.models.Dictionary;
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
    private ListView listFriends;
    @FXML
    private ListView listEnemies;
    @FXML
    private ComboBox comboItems;
    @FXML
    private ComboBox comboDictionary;

    Dictionary dictionary;

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
    private void getObjects() {
        comboItems.getItems().clear();
        String selected = comboDictionary.getSelectionModel().getSelectedItem().toString();
        Dictionary dic = DictionaryFactory.Instance.getDictionary(selected);
        comboItems.getItems().setAll(dic.getKeySet());
    }

    @FXML
    private void SelectItem() {
        Object selectedItem = comboItems.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem.toString());
        System.out.println(dictionary.findFriends(selectedItem));
        listFriends.getItems().setAll(dictionary.findFriends(selectedItem.toString()));
        listEnemies.getItems().setAll(dictionary.findEnemies(selectedItem));
    }

    @FXML
    private void SelectDictionary() {
        String selectedDictionary = comboDictionary.getSelectionModel().getSelectedItem().toString();
        dictionary = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        comboItems.getItems().setAll(DictionaryFactory.Instance.getObjectsByType(dictionary));
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addToComboBox(comboDictionary, DictionaryFactory.Instance.getDictionaryNames());
    }

    public static void main(String[] args) {

        launch(args);
    }

}
