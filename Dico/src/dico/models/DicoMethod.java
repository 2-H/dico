/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import java.util.ArrayList;

/**
 *
 * @author Me
 */
public class DicoMethod {

    public static DicoMethod TOSTRING = new DicoMethod("toString", 0);
    public static DicoMethod EQUALS = new DicoMethod("equals", 1);

    public static ArrayList<DicoMethod> getMethods() {
        ArrayList<DicoMethod> list = new ArrayList<>();
        list.add(TOSTRING);
        list.add(EQUALS);
        return list;
    }

    private String name;
    private int parameterCount;

    public String getName() {
        return name;
    }

    public DicoMethod(String name, int parameterCount) {
        this.name = name;
        this.parameterCount = parameterCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }

}
