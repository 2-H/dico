/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classInitiation;

import dico.models.Attribute;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hussien Wehbi
 */
public class ObjectInstanceRow {

    private SimpleStringProperty field;

    public Attribute getAtr() {
        return atr;
    }

    public void setAtr(Attribute atr) {
        this.atr = atr;
    }
    private SimpleStringProperty value;
    private Attribute atr;

    public ObjectInstanceRow() {
        field = new SimpleStringProperty("");
        value = new SimpleStringProperty("");
    }

    public ObjectInstanceRow(String field,Attribute atr) {
        this.atr=atr;
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
