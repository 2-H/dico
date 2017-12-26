/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import java.util.ArrayList;

/**
 *
 * @author k.shehady
 */
public class ClassModel {

    private String name;
    private ArrayList<Attribute> attribute;

    public ArrayList<Attribute> getAttribute() {
        return attribute;
    }
    public void setAttribute(ArrayList<Attribute> attribute) {
        this.attribute = attribute;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
