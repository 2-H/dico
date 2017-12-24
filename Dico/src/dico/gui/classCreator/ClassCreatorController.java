/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classCreator;

import dico.ClassFactory;
import dico.models.Attribute;
import dico.models.ClassModel;
import dico.models.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private ComboBox comboInheritedTypes;
    @FXML
    private Button btnCreateClass;
    @FXML
    private TableView<AttributeRow> tblAtd;
    @FXML
    private TableColumn tblColAttributeName;
    @FXML
    private TableColumn tblColAtrributeType;
    @FXML
    private CheckBox chkInherited;
    @FXML
    private void RemoveButtonClicked() {
        AttributeRow atr = tblAtd.getSelectionModel().getSelectedItem();
        tblAtd.getItems().remove(atr);

    }

    @FXML
    private void AddButtonClicked() {
        if (txtAttributeName != null && !(txtAttributeName.getText().trim().equals("")) && comboTypes.getValue() != null) {
            String adt = txtAttributeName.getText();
            String type = comboTypes.getSelectionModel().getSelectedItem().toString();
            AttributeRow r = new AttributeRow(adt, type);
            ObservableList<AttributeRow> rows = FXCollections.observableArrayList(r);

            tblAtd.getItems().addAll(rows);
            txtAttributeName.clear();
        }
    }

   /* @FXML
    private void RemoveButtonClicked() {
        AttributeRow r = tblAtd.getSelectionModel().getSelectedItem();
        tblAtd.getItems().remove(r);
    }*/

    private void addToComboBox(ArrayList<String> myArrStr) {
        for (String str : myArrStr) {
            comboTypes.getItems().add(str);
        }
    }

    @FXML
    private void InheritedChecked() {
        if (chkInherited.isSelected()) {
            comboInheritedTypes.setDisable(false);
        } else {
            comboInheritedTypes.setDisable(true);
        }
    }

    @FXML
    private void CreateClassButtonHandler(ActionEvent event) {
        // txtClassName = new TextField();
        if (txtClassName == null || txtClassName.getText().trim().equals("") || tblAtd.getItems().size() == 0 || (comboInheritedTypes.getValue() == null && chkInherited.isSelected())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Some fields are not filled properly.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);

        ClassModel classmodel = new ClassModel();
        classmodel.setName(txtClassName.getText());

        ArrayList<Attribute> list = new ArrayList<Attribute>();

        for (AttributeRow at : tblAtd.getItems()) {
            list.add(new Attribute(at.getName(), at.getType()));
        }

        classmodel.setAttribute(list);

        String classText = ClassFactory.create(classmodel);

        alert.setContentText(classText);

        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblColAttributeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblColAtrributeType.setCellValueFactory(new PropertyValueFactory<>("type"));
        //AttributeRow r = new AttributeRow("A", "B");
        //ObservableList<AttributeRow> rows = FXCollections.observableArrayList(r);

        //tblAtd.getItems().addAll(rows);
        ArrayList<String> a = new ArrayList<String>();
        for (String t : Type.GetTypes()) {
            a.add(t);
        }
        addToComboBox(a);
        tblAtd.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        chkInherited.setSelected(false);
        comboInheritedTypes.setDisable(true);
    }

}
