/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import dico.models.ClassModel;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class ObjectFactory {

    public static final ObjectFactory Instance = new ObjectFactory();
    public static ArrayList<ClassModel> Objects;

    private ObjectFactory() {
        Objects = new ArrayList<>();
    }

    public void createObject(ClassModel model) {
        Objects.add(model);

    }
}
