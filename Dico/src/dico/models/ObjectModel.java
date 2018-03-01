/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import dico.compiler.DicoCompilerIntiator;
import java.util.Map;

/**
 *
 * @author Me
 */
public class ObjectModel implements Comparable<ObjectModel> {

    private Map<String, AttributeValue> AttributesValues;
    private ClassModel classModel;
    private String variableName;
    private Object instance;

    public Object getInstance() {
        return instance;
    }

    public Map<String, AttributeValue> getAttributesValues() {
        return AttributesValues;
    }

    public void setAttributesValues(Map<String, AttributeValue> Attributes) {
        this.AttributesValues = Attributes;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    public void setClassModel(ClassModel classModel) {
        this.classModel = classModel;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean equals(Object other) {
        System.out.println("ObjectModel.equals");
        // return DicoCompilerIntiator.Instance.InvokeToString(this);
        return this.getInstance().equals(other);
    }

    @Override
    public int compareTo(ObjectModel other) {
        System.out.println("ObjectModel.compareTo");
        return DicoCompilerIntiator.Instance.InvokeCompareTo(this, other);
    }

    @Override
    public int hashCode() {
       System.out.println("ObjectModel.hashCode");
        return this.getInstance().hashCode();
    }

    @Override
    public String toString() {
          System.out.println("ObjectModel.toString");
        // return DicoCompilerIntiator.Instance.InvokeToString(this);
        return this.classModel.getName() + " " + getVariableName();
    }

}
