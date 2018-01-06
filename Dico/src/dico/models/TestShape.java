/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

/**
 *
 * @author Ali Al-Jobouri
 */
public class TestShape {
 private String name;   

    @Override
    public String toString() {
        return "Shape{" + "name=" + name + '}';
    }

    public TestShape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
