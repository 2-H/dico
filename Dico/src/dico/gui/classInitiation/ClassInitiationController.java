/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classInitiation;

import dico.ClassFactory;
import dico.Pool;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.DicoClassNotFoundException;
import dico.exceptions.ObjectCreationException;
import dico.exceptions.ObjectNotFoundException;
import dico.models.Attribute;
import dico.models.ClassModel;
import dico.models.ObjectModel;
import dico.models.Type;
import java.awt.event.MouseEvent;
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
    @FXML
    private TextField txtfieldvalue;
    @FXML
    private ComboBox cbxobjectvalue;
    @FXML
    private Button SaveValueBtn;
    @FXML
    private Label FieldNameLabel;
    private ObjectInstanceRow selectedRow;
    private ArrayList<Attribute> allAtributes;

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
    public void Start() {
        SaveValueBtn.setDisable(false);
        selectedRow = tableInstances.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            cbxobjectvalue.getItems().clear();
            txtfieldvalue.clear();
            Type t = selectedRow.getAtr().getType();
            FieldNameLabel.setText(t.getName() + " " + selectedRow.getField());
            boolean isobject = t.isCustomType();
            cbxobjectvalue.setDisable(!isobject);
            txtfieldvalue.setDisable(isobject);
            if (isobject) {
                for (ObjectModel o : Pool.Instance.getObjectsByType(t.getName())) {
                    cbxobjectvalue.getItems().add(o.getVariableName());
                }
            }
        }
    }

    @FXML
    public void SaveValueButtonHandler() {

        if (selectedRow != null) {
            String val = "";
            if (cbxobjectvalue.isDisable()) {
                val = txtfieldvalue.getText();
            } else {
                val = cbxobjectvalue.getSelectionModel().getSelectedItem().toString();
            }
            for (Attribute at : allAtributes) {
                if (at.getName().equals(selectedRow.getField())) {
                    at.setValue(val);
                    RefreshTable(false);
                    return;
                }
            }
        } else {
            System.out.println("selected null");
        }

    }

    private void RefreshTable(boolean firstload) {
        tableInstances.getItems().clear();

        System.out.println("Model name after: " + model.getName());
        for (Attribute t : allAtributes) {
            ObjectInstanceRow row = new ObjectInstanceRow(t.getName(), t);
            if (!firstload && t.getValue() != null) {
                row.setValue(t.getValue().toString());
            }
            tableInstances.getItems().add(row);
        }
    }

    @FXML
    private void Select(ActionEvent event) {
        try {
            model = null;
            //tableInstances.refresh();
            String selected = comboClassType.getSelectionModel().getSelectedItem().toString();
            model = ClassFactory.Instance.GetClass(selected);
            System.out.println("Model name before: " + model.getName());
            allAtributes = model.getAttributesWithSuper();
            RefreshTable(true);
        } catch (DicoClassNotFoundException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object GetValue(Attribute atr, String value) throws ObjectNotFoundException {
        if (atr.getType().isCustomType()) {
            Object ob = Pool.Instance.GetObject(value).getInstance();
            return ob;
            //return atr.getType().getClassName().cast(ob);
        }
        if (atr.getType().getClassName() == int.class) {
            return Integer.parseInt(value);
        } else if (atr.getType().getClassName() == Double.class) {
            return Double.parseDouble(value);
        } else if (atr.getType().getClassName() == String.class) {
            return value;
        }
        return value;
    }

    @FXML
    private void CreateInstanceButtonHandler(ActionEvent event) {
        /*if (txtObjectName == null || (comboClassType.getValue() == null)) {
         #Alert
         return;
         }
         */
        try {
            for (ObjectInstanceRow row : tableInstances.getItems()) {
                ClassModel tmp = model;
                while (tmp != null) {
                    for (Attribute atr : tmp.getAttribute()) {
                        if (atr.getName().equals(row.getField()) && row.getValue() != null) {
                            atr.setValue(GetValue(atr, row.getValue()));
                        }
                    }
                    tmp = tmp.getParent();

                }
            }
            //System.out.println(model.getAttribute().get(0).getValue());
            Pool.Instance.createObject(model, txtObjectName.getText());
        } catch (ComplierFailedException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ObjectCreationException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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
        tblColFeildName.setStyle("-fx-alignment: CENTER;");
        tblColFeildValue.setStyle("-fx-alignment: CENTER;");
        // ClassFactory.CreateDemoClass();
        addToComboBox(ClassFactory.Instance.GetClassNames());
        tableInstances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableInstances.getSelectionModel().cellSelectionEnabledProperty().set(true);

        cbxobjectvalue.setDisable(true);
        txtfieldvalue.setDisable(true);
        SaveValueBtn.setDisable(true);
    }

}
