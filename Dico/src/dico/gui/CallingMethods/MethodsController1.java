/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.CallingMethods;

import Message.Message;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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
    private TextArea tf;

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
        if (comboMethods.getValue() == null) {
            return;
        }
        try {
            String method = comboMethods.getSelectionModel().getSelectedItem().toString();
            String second = comboSecondObject.getSelectionModel().getSelectedItem().toString();
            String first = comboFirstObject.getSelectionModel().getSelectedItem().toString();

            ObjectModel firstObject = ObjectFactory.Instance.GetObject(first);
            ObjectModel secondObject = ObjectFactory.Instance.GetObject(second);

            if (method.equals("toString")) {
                tf.setText(DicoCompilerIntiator.Instance.InvokeToString(firstObject));
            } else if (method.equals("equals")) {
                if (DicoCompilerIntiator.Instance.InvokeEquals(firstObject, secondObject)) {
                    tf.setText(first + " is equal to " + second);
                } else {
                    tf.setText(first + " is not equal to " + second);
                }
            } else if (method.equals("compareTo")) {
                Integer res = DicoCompilerIntiator.Instance.InvokeCompareTo(firstObject, secondObject);
                if (res == 0) {
                    tf.setText(first + " is equal to " + second);
                } else if (res > 0) {
                    tf.setText(first + " is greater than " + second);
                } else if (res < 0) {
                    tf.setText(first + " is less than " + second);
                } else {
                    tf.setText("Failed to invoke compareTo.");
                }
            }
        } /*
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

           
         */ catch (ObjectNotFoundException | ClassCastException ex) {
            Message.show("Fatal Error", ex.getMessage(), AlertType.ERROR);
            tf.clear();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tf.setEditable(false);
        //comboFirstObject.setDisable(true);
        //comboSecondObject.setDisable(true);
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
