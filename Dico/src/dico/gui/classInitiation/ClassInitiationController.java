/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classInitiation;

import dico.ClassFactory;
import dico.TypesFactory;
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

/**
 *
 * @author Hussien Wehbi
 */
public class ClassInitiationController implements Initializable {

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
        String selected = comboClassType.getSelectionModel().getSelectedItem().toString();
        ClassModel model;
        try {
            model = ClassFactory.Instance.GetClass(selected);
            for (Attribute t : model.getAttribute()) {
                ObjectInstanceRow row = new ObjectInstanceRow(t.getName());
                tableInstances.getItems().add(row);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassInitiationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CreateInstanceButtonHandler(ActionEvent event) {
        if (txtObjectName == null || (comboClassType.getValue() == null)) {

            return;
        }

        ClassModel classmodel = new ClassModel();
        classmodel.setName(txtObjectName.getText());

        ArrayList<Attribute> list = new ArrayList<Attribute>();

        classmodel.setAttribute(list);
        //String classText = ClassFactory.create(classmodel);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblColFeildName.setCellValueFactory(new PropertyValueFactory<>("feild"));
        tblColFeildValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tblColFeildValue.setCellFactory(TextFieldTableCell.forTableColumn());

        addToComboBox(ClassFactory.Instance.GetClassNames());
        tableInstances.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableInstances.getSelectionModel().cellSelectionEnabledProperty().set(true);

    }

}
