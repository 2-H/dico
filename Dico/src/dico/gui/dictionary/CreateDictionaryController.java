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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Button btnCreateDictionary;
    @FXML
    private TextField DictionaryName;

    @FXML
    private void CreateDictionary() {
        Object selectedtype = ComboTypes.getSelectionModel().getSelectedItem();
        String selectedname = DictionaryName.getText();
        try {
            if (selectedname == null || !selectedname.equals("")) {
                throw new IOException("Name is required");
            }
            ClassModel cls = ClassFactory.Instance.GetClass(selectedtype.toString());
            DictionaryFactory.Instance.createDictionary(cls, selectedname);
            Stage stage = (Stage) btnCreateDictionary.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
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
