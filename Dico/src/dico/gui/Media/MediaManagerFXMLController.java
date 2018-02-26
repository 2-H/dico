/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.Media;

import Message.Message;
import dico.DictionaryFactory;
import dico.Pool;
import dico.exceptions.ObjectNotFoundException;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import dico.models.Triplet;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private String currentPath = null;
    private String selectedDictionary = null;
    private Dictionary dic = null;
    
    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        combo.getItems().clear();
        for (String str : myArrStr) {
            combo.getItems().add(str);
        }
    }
    
    @FXML
    private void selectDictionary() {
        comboUsers.getItems().clear();
        selectedDictionary = comboDictionary.getSelectionModel().getSelectedItem().toString();
        Dictionary dictionary = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        ArrayList<ObjectModel> arr = DictionaryFactory.Instance.getObjectsByType(dictionary);
        for (ObjectModel o : arr) {
            comboUsers.getItems().add(o.getVariableName());
        }
    }
    
    @FXML
    public void fillObjects() {
//        String chosenDictionary = comboDictionary.getSelectionModel().getSelectedItem().toString();
//        Dictionary dictionary = DictionaryFactory.Instance.getDictionary(chosenDictionary);
//        for (Object o : dictionary.getKeySet()) {
//
//        }
    }
    
    @FXML
    public void browse() {
        Stage stage = (Stage) btnBrowse.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null && Media.getMediaType(Media.getFileExtension(selectedFile)) != null) {
            //String ext = getFileExtension(selectedFile);
            lblFileName.setText(selectedFile.getName());
            currentPath = selectedFile.getAbsolutePath();
        }
    }
    
    @FXML
    public void addPathToListBox() {
        if (currentPath == null || lstFileNames.getItems().contains(currentPath)) {
            return;
        }
        lstFileNames.getItems().add(currentPath);
        currentPath = null;
        lblFileName.setText("");
    }
    
    @FXML
    public void fillOldMedias() {
        lstFileNames.getItems().clear();
        try {
            dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
            String selectedObject = comboUsers.getSelectionModel().getSelectedItem().toString();
            Object o = Pool.Instance.GetObject(selectedObject);
            Triplet<Object> t = dic.getPair(o);
            for (Media m : t.getMedias()) {
                lstFileNames.getItems().add(m.getAddress());
            }
        } catch (ObjectNotFoundException ex) {
            Message.show("Fatal Error", "Object not found.", AlertType.ERROR);
        }
    }
    
    @FXML
    public void remove() {
        if (lstFileNames.getItems() != null && lstFileNames.getSelectionModel().getSelectedItem() != null) {
            lstFileNames.getItems().remove(lstFileNames.getSelectionModel().getSelectedItem());
        }
    }
    
    @FXML
    public void save() {
        if (lstFileNames.getItems().size() == 0 || comboUsers.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
            String selectedObject = comboUsers.getSelectionModel().getSelectedItem().toString();
            Object o = Pool.Instance.GetObject(selectedObject);
            Triplet<Object> t = dic.getPair(o);
            Set<Media> set = new HashSet<>();
            Media m;
            for (Object tmp : lstFileNames.getItems()) {
                m = new Media(tmp.toString(), Media.getMediaType(Media.getFileExtension(new File((String) tmp))));
                set.add(m);
            }
            t.setMedias(set);
        } catch (ObjectNotFoundException ex) {
            Message.show("Fatal Error", "Object not found.", AlertType.ERROR);
        }
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblFileName.setText("");
        //Fill combobox with dictionaries
        for (String str : DictionaryFactory.Instance.getDictionaryNames()) {
            comboDictionary.getItems().add(str);
        }
    }
}
