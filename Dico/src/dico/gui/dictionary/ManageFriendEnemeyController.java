/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.dictionary;

import dico.DictionaryFactory;
import dico.models.Dictionary;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */
public class ManageFriendEnemeyController implements Initializable {

    @FXML
    private Button btnAddToDictionary;
    @FXML
    private Button btnSaveDictionary;
    @FXML
    private ComboBox ComboBoxDic;
    @FXML
    private ComboBox ComboBoxIt;
    @FXML
    private ListView myFriends;
    @FXML
    private ListView myEnemies;

    private static int counter = 0;

    public ArrayList<String> Person = new ArrayList<>();
    public ArrayList<String> Animal = new ArrayList<>();

    @FXML
    private void SaveDictionary() {
        //Save all addding 
    }

    @FXML
    private void RemoveItem() {

    }

    @FXML
    private void fillItems() {
        String selectedDictionary = ComboBoxDic.getSelectionModel().getSelectedItem().toString();
        Dictionary dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        ComboBoxIt.getItems().clear();
        ComboBoxIt.getItems().addAll(dic.getKeySet());
    }

    public void editLists() {
        String selectedDictionary = ComboBoxDic.getSelectionModel().getSelectedItem().toString();
        Dictionary dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        Object o = ComboBoxIt.getItems().addAll(dic.getKeySet());
        // List friends=dic.findFriends(o);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (String d : DictionaryFactory.Instance.getDictionaryNames()) {
            ComboBoxDic.getItems().add(d);
        }

    }

}
