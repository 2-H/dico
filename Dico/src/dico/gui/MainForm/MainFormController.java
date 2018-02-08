/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.MainForm;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.ObjectFactory;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
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
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ali Al-Jobouri
 */
public class MainFormController implements Initializable {

    @FXML
    private Button btnDemo;
    @FXML
    private Button btnCreateClass;
    @FXML
    private Button btnCompileClasses;
    @FXML
    private Button btnCreateObject;
    @FXML
    private Button btnCallMethods;
    @FXML
    private Button btnCreateDictionary;
    @FXML
    private Button btnAddToDictionary;
    @FXML
    private Button btnCallDictionaryMethods;
    @FXML
    private Button btnSearchDictionary;
    @FXML
    private Button btnManageDictionary;
    @FXML
    private Button btnMedia;
    @FXML
    private ListView lstBoxClass;
    @FXML
    private ListView lstBoxObject;
    @FXML
    private ListView lstBoxDictionaries;

    public void refreshForm() {
        ArrayList<ObjectModel> Objects = ObjectFactory.Instance.Objects;
        ObservableList<String> names = FXCollections.observableArrayList();
        if (ClassFactory.Instance.Classess.size() > 0) {
            btnCreateObject.setDisable(false);
            btnCompileClasses.setDisable(false);

        }
        if (ObjectFactory.Instance.Objects.size() > 0) {
            btnCallMethods.setDisable(false);
            btnCreateDictionary.setDisable(false);

        }
        ObservableList<String> list = FXCollections.observableArrayList();
        for (String str : ClassFactory.Instance.GetClassNames()) {
            list.add(str);
        }
        lstBoxClass.setItems(list);
        for (ObjectModel str : Objects) {
            names.add(str.getVariableName());
        }
        lstBoxObject.setItems(names);
        lstBoxDictionaries.getItems().clear();
        lstBoxDictionaries.getItems().setAll(DictionaryFactory.Instance.getDictionaryNames());
        if (!lstBoxDictionaries.getItems().isEmpty()) {
            btnAddToDictionary.setDisable(false);
            btnCallDictionaryMethods.setDisable(false);
            btnSearchDictionary.setDisable(false);
            btnManageDictionary.setDisable(false);
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
    private void CompileClasses() {
        try {
            DicoCompilerIntiator.Instance.CreateAndComplieFiles();
        } catch (ComplierFailedException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Demo() {
        try {
            DictionaryFactory.Demo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshForm();
    }

    @FXML
    private void CreateClass() {
        opener("..//classCreator//ClassCreator.fxml");
    }

    @FXML
    private void CreateObject() {
        opener("..//classInitiation//ClassInitiation.fxml");
    }

    @FXML
    private void CallMethods() {
        opener("..//CallingMethods//Methods.fxml");
    }

    @FXML
    private void CreateDictionary() {
        opener("..//dictionary//CreateDictionary.fxml");
    }

    @FXML
    private void AddToDictionary() {
        opener("..//dictionary//AddItemsToMyDictionary.fxml");
    }

    @FXML
    private void SearchDictionary() {
        opener("..//SearchForm//SearchForm.fxml");
    }

    @FXML
    private void ManageDictionary() {
        opener("..//dictionary//ManagerFriendEnemy.fxml");
    }

    @FXML
    private void CallDictionaryMethods() {
        opener("..//dictionary//ManagerFriendEnemy.fxml");
    }

    @FXML
    private void Media(){
        opener("..//Media//MediaManagerFXML.fxml");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*lstBoxClass.setStyle("-fx-align: CENTER;");
        lstBoxObject.setStyle("-fx-alignment: CENTER;");
        lstBoxClass.setMouseTransparent(true);
        lstBoxClass.setFocusTraversable(false);
        lstBoxObject.setMouseTransparent(true);
        lstBoxObject.setFocusTraversable(false);
         */
    }

}
