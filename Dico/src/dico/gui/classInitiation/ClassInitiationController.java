/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classInitiation;

import dico.ClassFactory;
import dico.ObjectFactory;
import dico.TypesFactory;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.ObjectCreationException;
import dico.models.Attribute;
import dico.models.ClassModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 *
 * @author Hussien Wehbi
 */
public class ClassInitiationController implements Initializable {

    ClassModel model;
    @FXML
    private Label labelInstance;
    @FXML
    private TextField txtObjectName;
    @FXML
    private ComboBox comboClassType;
    @FXML
    private Button btnCreateInstance;
    @FXML
    private TableView<ObjectInstanceRow> tableInstances;
    @FXML
    private TableColumn tblColFeildName;
    @FXML
    private TableColumn tblColFeildValue;

    private ArrayList<Attribute> getAllAttributes(ClassModel c) {
        ArrayList<Attribute> allAtributes = new ArrayList<Attribute>();
        ClassModel model;
        while (!(c == null)) {
            allAtributes.addAll(0, c.getAttribute());
            c = c.getParent();
        }
        return allAtributes;
    }

    private void addToComboBox(ArrayList<String> myArrStr) {
        for (String str : myArrStr) {
            comboClassType.getItems().add(str);
        }
    }

    public void changeValue(CellEditEvent e) {
        ObjectInstanceRow o = tableInstances.getSelectionModel().getSelectedItem();
        o.setValue(e.getNewValue().toString());
    }

    @FXML
    private void Select(ActionEvent event) {
        try {
            model = null;
            tableInstances.getItems().clear();
            tableInstances.refresh();
            String selected = comboClassType.getSelectionModel().getSelectedItem().toString();
            model = ClassFactory.Instance.GetClass(selected);
            System.out.println("Model name before: " + model.getName());
            ArrayList<Attribute> allAtributes = getAllAttributes(model);
            System.out.println("Model name after: " + model.getName());
            for (Attribute t : allAtributes) {
                ObjectInstanceRow row = new ObjectInstanceRow(t.getName());
                tableInstances.getItems().add(row);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CreateInstanceButtonHandler(ActionEvent event) {
        /*if (txtObjectName == null || (comboClassType.getValue() == null)) {
            
                #Alert 
            
            return;
        }
         */

        for (ObjectInstanceRow row : tableInstances.getItems()) {
            ClassModel tmp = model;
            while (tmp != null) {
                for (Attribute atr : tmp.getAttribute()) {
                    if (atr.getName().equals(row.getField()) && row.getValue() != null) {
                        atr.setValue(row.getValue());
                    }
                }
                tmp = tmp.getParent();

            }
        }
        try {
            //System.out.println(model.getAttribute().get(0).getValue());
            ObjectFactory.Instance.createObject(model,txtObjectName.getText());
        } catch (ComplierFailedException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCreationException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) btnCreateInstance.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblColFeildName.setCellValueFactory(new PropertyValueFactory<>("field"));
        tblColFeildValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tblColFeildValue.setCellFactory(TextFieldTableCell.forTableColumn());
        // ClassFactory.CreateDemoClass();
        addToComboBox(ClassFactory.Instance.GetClassNames());
        tableInstances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableInstances.getSelectionModel().cellSelectionEnabledProperty().set(true);

    }

}
