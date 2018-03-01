/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.MainForm;

import dico.ClassFactory;
import dico.DictionaryFactory;
import dico.Pool;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
import dico.gui.Media.Media;
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
import dico.persistence.ImportFromXML;
import static java.awt.SystemColor.window;
import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.nio.file.Files;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import dico.persistence.exportToXML;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.xml.stream.XMLStreamException;

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
    private Button btnAddMedia;
    @FXML
    private Button btnViewMedia;
    @FXML
    private ListView lstBoxClass;
    @FXML
    private ListView lstBoxObject;
    @FXML
    private ListView lstBoxDictionaries;
    @FXML
    private Button btnexport;
    @FXML
    private Button btnimport;

    public void refreshForm() {
        ArrayList<ObjectModel> Objects = Pool.Instance.Objects;
        ObservableList<String> names = FXCollections.observableArrayList();
        if (ClassFactory.Instance.Classess.size() > 0) {
            btnCreateObject.setDisable(false);
            btnCompileClasses.setDisable(false);

        }
        if (Pool.Instance.Objects.size() > 0) {
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
    private void exportDictionary() throws XMLStreamException, IOException {

        Stage stage = (Stage) btnexport.getScene().getWindow();
        //FileChooser fileChooser = new FileChooser();
        //fileChooser.setTitle("Open Resource File");
        //File selectedFile = fileChooser.showOpenDialog(null);
        DirectoryChooser dirChooser = new DirectoryChooser();
        File selectedDirectory = dirChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String code = exportToXML.ExportToXml();
            try {
                // Create file 
                String path = selectedDirectory.getAbsolutePath() + "/Dictionaries.xml";
                FileWriter fstream = new FileWriter(path);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(code);
                //Close the output stream
                out.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }

    }

    @FXML
    private void importDictionary() throws XMLStreamException, IOException {

        Stage stage = (Stage) btnimport.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open XML File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null ) {
            ImportFromXML.Import(selectedFile.getAbsolutePath());
            refreshForm();
        }

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
        opener("..//dictionaryMethods//dicoMethods.fxml");
    }

    @FXML
    private void ViewMedia() {
        opener("..//MediaShow//MediaShowFXML.fxml");
    }

    @FXML
    private void AddMedia() {
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
