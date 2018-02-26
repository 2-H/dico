/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.dictionary;

import dico.DictionaryFactory;
import dico.Pool;
import dico.gui.classCreator.AttributeRow;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class AddItemsToDictionaryController implements Initializable {

    @FXML
    private Button btnAddToDictionary;
    @FXML
    private Button btnSaveDictionary;
    @FXML
    private ComboBox ComboBoxDictionaries;
    @FXML
    private ComboBox Items;
    @FXML
    private ListView ListviewofItems;

    private static int counter = 0;
    Dictionary dic;

    @FXML
    private void SaveDictionary() {
        //dic.clear();
        //Save all addding 
        for (Object o : ListviewofItems.getItems()) {
            dic.add(o);
        }
        System.out.println(dic.size() + "");
        Stage stage = (Stage) btnSaveDictionary.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void RemoveItem() {
        Object selected = ListviewofItems.getSelectionModel().getSelectedItem();
        Items.getItems().add(selected);
        ListviewofItems.getItems().remove(selected);

        if (Items.getItems().size() != 0) {
            btnAddToDictionary.setDisable(false);
        }
    }

    @FXML
    private void SelectDictionary() {
        Items.getSelectionModel().clearSelection();
        //Add all Items(objects) of this type of Dictionary.
        String selectedDictionary = ComboBoxDictionaries.getSelectionModel().getSelectedItem().toString();
        dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        
        Items.getItems().setAll(DictionaryFactory.Instance.getObjectsByType(dic));

    }

    @FXML
    private void AddToDictionary() {
        if (Items.getSelectionModel().getSelectedItem() != null) {
            if (counter == 0) {
                ComboBoxDictionaries.setDisable(true);
            }
            Object selectedItem = Items.getSelectionModel().getSelectedItem();
            Items.getItems().remove(Items.getSelectionModel().getSelectedItem());
            ListviewofItems.getItems().add(selectedItem);

            if (Items.getItems().size() == 0) {
                btnAddToDictionary.setDisable(true);
            }
            //add the item to Dictionary
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (String d : DictionaryFactory.Instance.getDictionaryNames()) {
            ComboBoxDictionaries.getItems().add(d);
        }

        if (ComboBoxDictionaries.getItems().size() == 0) {
            btnAddToDictionary.setDisable(true);
            btnSaveDictionary.setDisable(true);
        }

    }
}
