/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.MainForm;

import dico.gui.classCreator.ClassCreator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import dico.gui.classCreator.*;/**
 * FXML Controller class
 *
 * @author Ali Al-Jobouri
 */
public class MainFormController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnCreateClass;
    @FXML
    private Button btnCreateObject;
    @FXML
    private Button btnInstantiate;
    
    @FXML
    private void OpenCreateClass() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("..//classCreator//ClassCreator.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
           // stage.initStyle(StageStyle.TRANSPARENT);
           // stage.setTitle("abc");
 
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void OpenCreateObject() {

    }

    @FXML
    private void OpenInstantiate() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
