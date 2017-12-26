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

    private SimpleStringProperty field;
    private SimpleStringProperty value;

    public ObjectInstanceRow() {
        field = new SimpleStringProperty("");
        value = new SimpleStringProperty("");
    }

    public ObjectInstanceRow(String field) {
        this.field = new SimpleStringProperty(field);
        this.value = new SimpleStringProperty("");
    }

    public String getField() {
        return field.getValue();
    }

    public void setField(String value) {
        this.field = new SimpleStringProperty(value);
    }

    public String getValue() {
        return value.getValue();
    }

    public void setValue(String value) {
        this.value = new SimpleStringProperty(value);
    }

}
