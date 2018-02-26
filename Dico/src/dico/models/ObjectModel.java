/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import dico.compiler.DicoCompilerIntiator;

/**
 *
 * @author Me
 */
public class ObjectModel {

    private ClassModel classModel;
    private String variableName;
    private Object instance;

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
        return DicoCompilerIntiator.Instance.InvokeToString(this);
        //  return this.classModel.getName() + " " + getVariableName();
    }

}
