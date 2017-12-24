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

    private final SimpleStringProperty name;//بدنا نشوف شو هي

    private final SimpleStringProperty type;

    private final SimpleStringProperty equals;

    private final SimpleStringProperty compareTo;

    public AttributeRow() {
        name = new SimpleStringProperty("");
        type = new SimpleStringProperty("");
        equals = new SimpleStringProperty("");
        compareTo = new SimpleStringProperty("");
    }

    public AttributeRow(String attribute, String type, String equals, String compareTo) {
        this.name = new SimpleStringProperty(attribute);;
        this.type = new SimpleStringProperty(type);
        this.equals = new SimpleStringProperty(equals);
        this.compareTo = new SimpleStringProperty(compareTo);
    }

    public String getName() {
        return name.getValue();
    }

    public String getType() {
        return type.getValue();
    }
    
    public String getCompareTo(){
        return compareTo.getValue();
    }
    
     public String getEquals(){
        return equals.getValue();
    }

}
