/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.Media;

import dico.DictionaryFactory;
import dico.models.Dictionary;
import dico.models.Triplet;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Ali Al-Jobouri
 */
public class MediaManagerFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnSave;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBrowse;
    @FXML
    private ListView lstFileNames;
    @FXML
    private ComboBox comboUsers;
    @FXML
    private ComboBox comboDictionary;
    @FXML
    private Label lblFileName;

    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        combo.getItems().clear();
        for (String str : myArrStr) {
            combo.getItems().add(str);
        }
    }
    
    @FXML
    private void selectDictionary() {
        String selectedDictionary = comboDictionary.getSelectionModel().getSelectedItem().toString();
        Dictionary dictionary = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        comboUsers.getItems().setAll(DictionaryFactory.Instance.getObjectsByType(dictionary));
    }

    @FXML
    public void fillObjects() {
        String chosenDictionary = comboDictionary.getSelectionModel().getSelectedItem().toString();
        Dictionary dictionary = DictionaryFactory.Instance.getDictionary(chosenDictionary);
        for (Object o : dictionary.getKeySet()) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Fill combobox with dictionaries
//        for (String str : DictionaryFactory.Instance.getDictionaryNames()) {
//            comboDictionary.getItems().add(str);
//        }

    }

}
