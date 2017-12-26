/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classCreator;

import dico.ClassFactory;
import dico.TypesFactory;
import dico.gui.MainForm.MainForm;
import dico.gui.MainForm.MainFormController;
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
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TableColumn tblColEquals;
    @FXML
    private TableColumn tblColCompareTo;
    @FXML
    private CheckBox checkCt;
    @FXML
    private CheckBox checkEq;

    @FXML
    private void RemoveAttributeButtonClicked() {
        AttributeRow atr = tblAtd.getSelectionModel().getSelectedItem();
        if (atr != null) {
            tblAtd.getItems().remove(atr);
        }
    }

    @FXML
    private void AddAttributeButtonClicked() {
        if (txtAttributeName != null && !(txtAttributeName.getText().trim().equals("")) && comboTypes.getValue() != null) {
            String adt = txtAttributeName.getText();
            String type = comboTypes.getSelectionModel().getSelectedItem().toString();
            String equals = checkEq.isSelected() ? "Yes" : "No";
            String compareTo = checkCt.isSelected() ? "Yes" : "No";
            AttributeRow row = new AttributeRow(adt, type, equals, compareTo);
            tblAtd.getItems().add(row);
            txtAttributeName.clear();
            checkEq.setSelected(false);
            checkCt.setSelected(false);

        }
    }

    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        for (String str : myArrStr) {
            combo.getItems().add(str);
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
        if (txtClassName == null || txtClassName.getText().trim().equals("") || tblAtd.getItems().size() == 0 || (comboInheritedTypes.getValue() == null && chkInherited.isSelected())) {
            /*Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Some fields are not filled properly.");
            alert.showAndWait();
            //System.out.println("created class");
             */
            return;

        }

        /*Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
         */
        ClassModel classmodel = new ClassModel();
        classmodel.setName(txtClassName.getText());

        ArrayList<Attribute> list = new ArrayList<>();

        for (AttributeRow at : tblAtd.getItems()) {
            list.add(new Attribute(at.getName(), TypesFactory.Instance.Get(at.getType()), at.getEquals().equals("Yes"), at.getCompareTo().equals("Yes")));
        }

        classmodel.setAttribute(list);

        String classText = ClassFactory.Instance.create(classmodel);
        //alert.setContentText(classText);

        //alert.showAndWait();
        Stage stage = (Stage) btnCreateClass.getScene().getWindow();
        // do what you have to do
        stage.close();
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblColAttributeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblColAtrributeType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblColEquals.setCellValueFactory(new PropertyValueFactory<>("equals"));
        tblColCompareTo.setCellValueFactory(new PropertyValueFactory<>("compareTo"));

        ArrayList<String> typeList = new ArrayList<>();
        for (Type t : TypesFactory.Instance.Types) {
            typeList.add(t.getName());
        }
        addToComboBox(comboTypes, typeList);

        ArrayList<String> classList = new ArrayList<>();
        for (ClassModel c : ClassFactory.Classess) {
            classList.add(c.getName());
        }
        addToComboBox(comboInheritedTypes, classList);

        tblAtd.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        chkInherited.setSelected(false);
        comboInheritedTypes.setDisable(true);
    }

}
