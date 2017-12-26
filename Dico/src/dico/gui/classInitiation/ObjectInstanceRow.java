/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classInitiation;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hussien Wehbi
 */
public class ObjectInstanceRow {

    private final SimpleStringProperty feild;
    private  SimpleStringProperty value;

    public ObjectInstanceRow() {
        feild = new SimpleStringProperty("");
        value = new SimpleStringProperty("");
    }

    public ObjectInstanceRow(String attribute) {
        this.feild = new SimpleStringProperty(attribute);
        this.value = new SimpleStringProperty("");
    }

    public String getFeild() {
        return feild.getValue();
    }

    public String getValue() {
        return value.getValue();
    }
    public void setValue(String value)
    {
        this.value=new SimpleStringProperty(value);
    }

}
