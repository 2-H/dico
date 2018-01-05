/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.Dictionary;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import dico.DictionaryFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import dico.models.ClassModel;
import dico.models.ObjectModel;
import dico.ClassFactory;
import dico.ObjectFactory;
import dico.exceptions.DicoClassNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Hasan Ghrayeb
 */
public class CreateDictionaryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox ComboTypes;
    @FXML
    private Button CreateDictionary;

    @FXML
    private void CreateDictionary() {
        
        String selected = ComboTypes.getSelectionModel().getSelectedItem().toString();
        try {
            ClassModel cls= ClassFactory.Instance.GetClass(selected);
            DictionaryFactory.createDictionary(cls);
        } catch (DicoClassNotFoundException ex) {
            Logger.getLogger(CreateDictionaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClassFactory.CreateDemoClass();
        
        for (ClassModel c : ClassFactory.Classess) {
            ComboTypes.getItems().add(c.getName());
        }
    }
}
