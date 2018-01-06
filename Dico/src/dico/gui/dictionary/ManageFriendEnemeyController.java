/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.dictionary;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.ObjectFactory;
import static dico.ObjectFactory.Objects;
import dico.exceptions.ObjectNotFoundException;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
    private Button btnCreateDictionary;
    @FXML
    private Button btnAddToDictionary;
    @FXML
    private Button btnSaveDictionary;
    @FXML
    private Button btnAddFriend;
    @FXML
    private Button btnAddEnemy;
    @FXML
    private ComboBox ComboBoxDic;
    @FXML
    private ComboBox ComboBoxIt;
    @FXML
    private ListView myFriends;
    @FXML
    private ListView myEnemies;
    @FXML
    private ComboBox ComboBoxF;
    @FXML
    private ComboBox ComboBoxE;

    private static int counter = 0;

    public ArrayList<String> Person = new ArrayList<>();
    public ArrayList<String> Animal = new ArrayList<>();

    @FXML
    private void addFriend() {
        Object obj = ComboBoxF.getSelectionModel().getSelectedItem();
        ObservableList<Object> nameE = myEnemies.getItems();
        if (!nameE.contains(obj)) {
            myFriends.getItems().add(obj.toString());
            ComboBoxF.getItems().remove(obj);
        }
    }

    @FXML
    private void addEnemy() {
        Object obj = ComboBoxE.getSelectionModel().getSelectedItem();
        ObservableList<Object> nameF = myFriends.getItems();
        if (!nameF.contains(obj)) {
            myEnemies.getItems().add(obj.toString());
            ComboBoxE.getItems().remove(obj);
        }
    }

    @FXML
    private void SaveDictionary() {
        //Save all addding 
    }

    @FXML
    private void RemoveItem() {

    }

    @FXML
    private void fillItems() {
        ComboBoxIt.getItems().clear();
        String selectedDictionary = (ComboBoxDic.getSelectionModel().getSelectedItem()).toString();
        Dictionary dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        ComboBoxIt.getItems().clear();
        ComboBoxIt.getItems().addAll(dic.getKeySet());
    }

    public void editLists() {

        Object usr = null;

        String selectedDictionary = ComboBoxDic.getSelectionModel().getSelectedItem().toString();
        Dictionary dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);

//            Object o = ComboBoxIt.getSelectionModel().getSelectedItem().toString();
//            String s=(String) o;
//       ObjectModel o2 = ObjectFactory.Instance.GetObject(s);
//            ArrayList<Object> OurKeys = dic.getKeySet();
//            ObservableList<String> nameF = (ObservableList<String>) new ArrayList();
//            ObservableList<String> nameE = (ObservableList<String>) new ArrayList();
//            for(Object obj:OurKeys){
//                String ss=obj.toString();
//                if(ss.equals(s))
//                    usr=obj;
//                
//            }
//            for (Object elem : dic.findFriends(usr)) {
//                nameF.add(elem.toString());
//            }
//            for (Object elem : dic.findEnemies(usr)) {
//                nameE.add(elem.toString());
//            }
        ComboBoxF.getItems().clear();
        ComboBoxE.getItems().clear();
        for (Object obj : dic.getKeySet()) {
            if (obj != ComboBoxIt.getSelectionModel().getSelectedItem()) {
                ComboBoxF.getItems().add(obj);
            }

        }
        for (Object obj : dic.getKeySet()) {
            if (obj != ComboBoxIt.getSelectionModel().getSelectedItem()) {
                ComboBoxE.getItems().add(obj);
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (String d : DictionaryFactory.Instance.getDictionaryNames()) {
            ComboBoxDic.getItems().add(d);
        }

    }

}
