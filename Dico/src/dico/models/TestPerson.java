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
public class TestPerson {
 private String name;   

    @Override
    public String toString() {
        return "TestPerson{" + "name=" + name + '}';
    }

    public TestPerson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
