/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classCreator;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Ali Al-Jobouri
 */
public class AttributeRow {

    private final SimpleStringProperty name;

    private final SimpleStringProperty type;

    public AttributeRow() {
        name = new SimpleStringProperty("");
        type = new SimpleStringProperty("");
    }

    public AttributeRow(String attribute, String type) {
        this.name = new SimpleStringProperty(attribute);;
        this.type = new SimpleStringProperty(type);
    }

    public String getName() {
        return name.getValue();
    }

    public String getType() {
        return type.getValue();
    }

}
