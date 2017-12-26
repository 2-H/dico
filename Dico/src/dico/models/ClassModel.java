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

    public ClassModel() {
        attribute = new ArrayList<>();
    }

    private String name;
    private ArrayList<Attribute> attribute;
    private ClassModel parent;
    private String javaCode;

    public String getJavaCode() {
        return javaCode;
    }

    public void setJavaCode(String javaCode) {
        this.javaCode = javaCode;
    }

    public ClassModel getParent() {
        return parent;
    }

    public void setParent(ClassModel parent) {
        this.parent = parent;
    }

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
