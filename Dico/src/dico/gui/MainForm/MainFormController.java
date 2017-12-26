/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.MainForm;

import dico.ClassFactory;
import dico.ObjectFactory;
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
import dico.gui.classCreator.*;

/**
 * FXML Controller class
 *
 * @author Ali Al-Jobouri
 */
public class MainFormController implements Initializable {

    @FXML
    private Button btnCreateClass;
    @FXML
    private Button btnCreateObject;
    @FXML
    private Button btnInstantiate;

    public void refreshForm() {
        if (ClassFactory.Instance.Classess.size() > 0) {
            btnCreateObject.setDisable(false);
        }
        if (ObjectFactory.Instance.Objects.size() > 0) {
            btnInstantiate.setDisable(false);
        }
    }

    private void opener(String str) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(str));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            refreshForm();

        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void OpenCreateClass() {
        opener("..//classCreator//ClassCreator.fxml");
    }

    @FXML
    private void OpenCreateObject() {
        opener("..//classInitiation//ClassInitiation.fxml");
    }

    @FXML
    private void OpenInstantiate() {
        opener("..//CallingMethods//Methods.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshForm();
    }

}