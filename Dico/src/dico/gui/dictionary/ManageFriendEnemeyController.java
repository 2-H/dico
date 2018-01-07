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
import dico.exceptions.FriendIsEnemyOrEnemyIsFriendException;
import dico.exceptions.FriendOrEnemyAlreadyExistsException;
import dico.exceptions.ObjectNotFoundException;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import dico.models.Pair;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    public Dictionary dic;
    
    public void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void addFriend() {
        if (ComboBoxF.getValue() == null) {
            return;
        }
        Object obj = ComboBoxF.getSelectionModel().getSelectedItem();
        if (!myEnemies.getItems().contains(obj)) {
            myFriends.getItems().add(obj.toString());
            ComboBoxF.getItems().remove(obj);
            ComboBoxE.getItems().remove(obj);
        }
    }

    @FXML
    private void addEnemy() {
        if (ComboBoxE.getValue() == null) {
            return;
        }
        Object obj = ComboBoxE.getSelectionModel().getSelectedItem();
        if (!myFriends.getItems().contains(obj)) {
            myEnemies.getItems().add(obj.toString());
            ComboBoxE.getItems().remove(obj);
            ComboBoxF.getItems().remove(obj);
        }
    }

    @FXML
    private void SaveDictionary() {
        Object[] friends = myFriends.getItems().toArray();
        Object[] enemies = myEnemies.getItems().toArray();
        try {
            if (friends != null) {
                for (Object o : friends) {
                    dic.addFriend(ComboBoxIt.getSelectionModel().getSelectedItem(), o);
                }
            }
            if (enemies != null) {
                for (Object o : enemies) {
                    dic.addEnemy(ComboBoxIt.getSelectionModel().getSelectedItem(), o);
                }
            }
        } catch (FriendOrEnemyAlreadyExistsException | FriendIsEnemyOrEnemyIsFriendException | NullPointerException ex) {
            showAlert("Fatal Error",ex.getMessage(),AlertType.ERROR);
        }
    }

    @FXML
    private void RemoveFriend() {

        Object o = myFriends.getSelectionModel().getSelectedItem();

        myFriends.getItems().remove(o);
        ComboBoxF.getItems().add(o);
        ComboBoxE.getItems().add(o);

    }

    @FXML
    private void RemoveEnemy() {

        Object o = myEnemies.getSelectionModel().getSelectedItem();

        myEnemies.getItems().remove(o);
//        ComboBoxF.getItems().clear();
//        ComboBoxE.getItems().clear();

        ComboBoxF.getItems().add(o);
        ComboBoxE.getItems().add(o);

    }

    @FXML
    private void fillItems() {
        ComboBoxIt.getItems().clear();
        String selectedDictionary = (ComboBoxDic.getSelectionModel().getSelectedItem()).toString();
        dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        ComboBoxIt.getItems().addAll(dic.getKeySet());
    }

    public void editLists() {

        Object usr = null;

        String selectedDictionary = ComboBoxDic.getSelectionModel().getSelectedItem().toString();
        Dictionary dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
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
