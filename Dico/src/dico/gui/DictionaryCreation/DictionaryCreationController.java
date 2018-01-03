/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.DictionaryCreation;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.models.ClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Hussien Wehbi
 */
public class DictionaryCreationController implements Initializable{
    
    @FXML
    private Button btnCreateDico;
    @FXML
    private ComboBox comboTypes;
    
    @FXML
    private void createDico()
    {
        DictionaryFactory factory=new DictionaryFactory();
        factory.createDictionary(comboTypes.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try{
        System.out.println(comboTypes.getItems());
            
        for(ClassModel model:ClassFactory.Classess)
        {
            System.out.println(model.getName());
            comboTypes.getItems().add(model.getName());
        }
        }
        catch(Exception io)
        {
            System.out.println("dico.gui.DictionaryCreation.DictionaryCreationController.initialize()");
        }
        
    }
    
}
