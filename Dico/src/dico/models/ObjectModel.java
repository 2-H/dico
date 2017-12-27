/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

/**
 *
 * @author Me
 */
public class ObjectModel {

    private ClassModel classModel;
    private String variableName;

    private Class classReference;
    private Object instance;

    public Class getClassReference() {
        return classReference;
    }

    public void setClassReference(Class classReference) {
        this.classReference = classReference;
    }

    public Object getInstance() {
        return instance;
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
    public String toString() {
        return getClass().getName() + " " + getVariableName();
    }

}
