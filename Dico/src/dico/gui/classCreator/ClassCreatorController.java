/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classCreator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Ali Al-Jobouri
 */
public class ClassCreatorController implements Initializable {
    @FXML
    private Label lblClassName;
    @FXML
    private Label lblAttributes;
    @FXML
    private TextField txtClassName;
    @FXML
    private TextField txtAttributeName;
    @FXML
    private ComboBox comboTypes;
    @FXML
    private Button btnCreateClass;
    
    @FXML
    private void CreateClassButtonHandler(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Class is created successfully");

        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
