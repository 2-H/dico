/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classCreator;

import dico.ClassFactory;
import dico.TypesFactory;
import dico.exceptions.DicoClassNotFoundException;
import dico.exceptions.DuplicateAttributesException;
import dico.exceptions.DuplicateClassesException;
import dico.models.Attribute;
import dico.models.ClassModel;
import dico.models.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private void CreateClassButtonHandler(ActionEvent event) throws DuplicateAttributesException {
        try {
            if (txtClassName == null || txtClassName.getText().trim().equals("") || tblAtd.getItems().size() == 0 || (comboInheritedTypes.getValue() == null && chkInherited.isSelected())) {

                return;
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);

            ClassModel classmodel = new ClassModel();
            classmodel.setName(txtClassName.getText());

            ArrayList<Attribute> list = new ArrayList<>();

            for (AttributeRow at : tblAtd.getItems()) {
                Type t = TypesFactory.Instance.Get(at.getType());
                if (t == null) {
                    ClassModel c = ClassFactory.Instance.GetClass(at.getType());
                    t = new Type(c.getName(), "", c.getClassReference(), true, true);
                }

                list.add(new Attribute(at.getName(), t, at.getEquals().equals("Yes"), at.getCompareTo().equals("Yes")));
            }

            classmodel.setAttribute(list);

            if (comboInheritedTypes.getValue() != null) {
                String selected = comboInheritedTypes.getSelectionModel().getSelectedItem().toString();
                classmodel.setParent(ClassFactory.Instance.GetClass(selected));
            }

            ClassFactory.Instance.generateJavaCode(classmodel);

            //alert.showAndWait();
            Stage stage = (Stage) btnCreateClass.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (DicoClassNotFoundException | DuplicateAttributesException | DuplicateClassesException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblColAttributeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblColAtrributeType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblColEquals.setCellValueFactory(new PropertyValueFactory<>("equals"));
        tblColCompareTo.setCellValueFactory(new PropertyValueFactory<>("compareTo"));
        tblColAttributeName.setStyle("-fx-alignment: CENTER;");
        tblColAtrributeType.setStyle("-fx-alignment: CENTER;");
        tblColEquals.setStyle("-fx-alignment: CENTER;");
        tblColCompareTo.setStyle("-fx-alignment: CENTER;");
        ArrayList<String> typeList = new ArrayList<>();
        for (Type t : TypesFactory.Instance.Types) {
            typeList.add(t.getName());
        }
        addToComboBox(comboTypes, typeList);

        for (String m : ClassFactory.Instance.GetClassNames()) {
            comboTypes.getItems().add(m);
        }

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
