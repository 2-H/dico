/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.dictionary;

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

    private static int counter=0;
    
    public Map<String, ArrayList<String>> Dictionaries = new HashMap<>();
    public ArrayList<String> Person = new ArrayList<>();
    public ArrayList<String> Animal = new ArrayList<>();

    @FXML
    private void SaveDictionary() {
        //Save all addding 
    }

    @FXML
    private void SelectDictionary() {
        Items.getSelectionModel().clearSelection();
        //Add all Items(objects) of this type of Dictionary.
        String selectedDictionary = ComboBoxDictionaries.getSelectionModel().getSelectedItem().toString();
        Items.getItems().setAll(Dictionaries.get(selectedDictionary));
    }

    @FXML
    private void AddToDictionary() {
        if(Items.getSelectionModel().getSelectedItem().toString()!=null){
            if(counter==0){
                ComboBoxDictionaries.setDisable(true);
            }
        String selectedItem=Items.getSelectionModel().getSelectedItem().toString();
        System.out.println(selectedItem);
        ListviewofItems.getItems().add(selectedItem);
        Items.getItems().remove(selectedItem);
        if(Items.getItems().size()==0){
            btnAddToDictionary.setDisable(true);
        }
        //add the item to Dictionary
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //get all dictionary names from list of dictionaries
        Person.add("ALi");
        Person.add("Hasan");
        Person.add("fadi");
        Animal.add("dog");
        Animal.add("Monkey");
        Dictionaries.put("Person", Person);
        Dictionaries.put("Animal", Animal);
        ComboBoxDictionaries.getItems().add("Person");
        ComboBoxDictionaries.getItems().add("Animal");

        if(ComboBoxDictionaries.getItems().size()==0){
        btnAddToDictionary.setDisable(true);
        btnSaveDictionary.setDisable(true);
        }
        
    }
}
