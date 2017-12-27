/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.CallingMethods;

import dico.ObjectFactory;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ObjectNotFoundException;
import dico.models.ClassModel;
import dico.models.DicoMethod;
import dico.models.ObjectModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MethodsController1 implements Initializable {

    @FXML
    private ComboBox comboMethods;
    @FXML
    private ComboBox comboFirstObject;
    @FXML
    private ComboBox comboSecondObject;
    @FXML
    private Button btnApply;
    @FXML
    private TextField tf;

    /**
     * Initializes the controller class.
     */
    @FXML

    private void MethodDeciderHandler() {

        ArrayList<DicoMethod> methodList = DicoMethod.getMethods();
        ArrayList<String> methodNames = new ArrayList<>();
        
        for (DicoMethod method : DicoMethod.getMethods()) {
            methodNames.add(method.getName());
        }

        String s = comboMethods.getSelectionModel().getSelectedItem().toString();
        int parameterCount = 0;

        for (DicoMethod mth : methodList) {
            if (mth.getName().equals(s)) {
                parameterCount = mth.getParameterCount();
            }
        }
        if (parameterCount == 0) {
            comboSecondObject.setDisable(true);
        } else {
            comboSecondObject.setDisable(false);
        }
    }

    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        for (String str : myArrStr) {
            combo.getItems().add(str);
        }

    }

    private ClassModel FindInstance(ArrayList<ClassModel> ca, String name) {

        for (ClassModel c : ca) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;

    }

    private void addToCombo(ComboBox combo, ArrayList<ObjectModel> myArrStr) {
        for (ObjectModel str : myArrStr) {
            combo.getItems().add(str.getVariableName());
        }
    }

    @FXML
    private void ApplyMethodHandler(ActionEvent event) {

        try {
            String method = comboMethods.getSelectionModel().getSelectedItem().toString();
            String second = comboSecondObject.getSelectionModel().getSelectedItem().toString();
            String first = comboFirstObject.getSelectionModel().getSelectedItem().toString();

            ObjectModel firstObject = ObjectFactory.Instance.GetObject(first);
            ObjectModel secondbject = ObjectFactory.Instance.GetObject(second);

            if (method.equals("toString")) {
                tf.setText(DicoCompilerIntiator.Instance.InvokeToString(firstObject));
            }

            /*      if (s.equals("equal")) {
                if (c1.equals(c2)) {
                    tf.setText("they are equal");
                } else {
                    tf.setText("they are not equal");
                }
            }

            //
            if (s.equals("compareTo")) {
                if (c1.getClass() == c2.getClass()) {
//             Comparable c1=(Comparable) s1;
//             Comparable c2=(Comparable) s2;
                    if (c1.compareTo(c2) > 0) {
                        tf.setText(c1.getName() + "is greater");
                    } else if (c1.compareTo(c2) < 0) {
                        tf.setText(c2.getName() + " is greater");
                    } else {
                        tf.setText("they are equal");
                    }

                } else {
                    tf.setText("2 Objects of different classes cannot be compared");
                }

            }

            if (s.equals("Hashcode")) {
                String a = " " + c1.hashCode();
                tf.setText(a);

            }

           
             */
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(MethodsController1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tf.setEditable(false);
        comboFirstObject.setDisable(true);
        comboSecondObject.setDisable(true);
        ArrayList<DicoMethod> methodList = DicoMethod.getMethods();
        ArrayList<String> methodNames = new ArrayList<>();

        for (DicoMethod method : DicoMethod.getMethods()) {
            methodNames.add(method.getName());
        }

        addToComboBox(comboMethods, methodNames);
        ArrayList<ObjectModel> objlist = ObjectFactory.Instance.Objects;

        addToCombo(comboFirstObject, objlist);
        addToCombo(comboSecondObject, objlist);

        comboSecondObject.getSelectionModel().selectFirst();//just to avoid NullPointrException
        comboFirstObject.getSelectionModel().selectFirst();

    }
}
