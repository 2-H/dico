/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.dictionaryMethods;

import dico.DictionaryFactory;
import dico.Pool;
import dico.exceptions.ObjectNotFoundException;
import dico.models.Dictionary;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author saleh saleh
 */
public class DicoMethodsController implements Initializable {

    @FXML
    private ComboBox DicosCombobox;
    @FXML
    private ComboBox ItemsComboBox;
    @FXML
    private Button ContainsButton;

    @FXML
    private void CheckContains() throws ObjectNotFoundException {
        String Dico = DicosCombobox.getSelectionModel().getSelectedItem().toString();
        String item = ItemsComboBox.getSelectionModel().getSelectedItem().toString();

        Dictionary d = DictionaryFactory.Instance.getDictionary(Dico);
       
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Result Of Contains");
     
        if( d.contains(Pool.Instance.GetObject(item)))
            alert.setContentText("The Item Exists !");
        else
            alert.setContentText("The Item Does Not Exists !");
        
        alert.showAndWait();

    }

    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        for (String str : myArrStr) {
            combo.getItems().add(str);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<String> Dicos = new ArrayList<>();
        Dicos = DictionaryFactory.Instance.getDictionaryNames();
        addToComboBox(DicosCombobox, Dicos);

        ArrayList<String> objects = new ArrayList<>();
        objects = Pool.Instance.getObjectNames();
        addToComboBox(ItemsComboBox, objects);

    }

}
