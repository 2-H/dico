/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import static dico.ClassFactory.Classess;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.ObjectCreationException;
import dico.exceptions.ObjectNotFoundException;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class Pool {

    public static final Pool Instance = new Pool();
    public static ArrayList<ObjectModel> Objects;

    public Pool() {
        Objects = new ArrayList<>();
    }

    public ObjectModel GetObject(String name) throws ObjectNotFoundException {
        for (ObjectModel cls : Objects) {
            String s = cls.getVariableName();
            if (s.equals(name)) {
                return cls;
            }
        }
        throw new ObjectNotFoundException("Object Not Found");
    }

    public ObjectModel createObject(ClassModel modelClass, String variableName) throws ComplierFailedException, ObjectCreationException, ClassNotFoundException {

        if (modelClass.getClassReference() == null) {
            throw new ComplierFailedException("Compile First");
        }

        ObjectModel object = DicoCompilerIntiator.Instance.createObject(modelClass, variableName);
        Objects.add(object);
        return object;

    }
    public ArrayList<ObjectModel> getObjectsByType(String type) {
        ArrayList<ObjectModel> list = new ArrayList<>();
       // ArrayList<ClassModel> test = new ArrayList<>();
        for (ObjectModel obj : Pool.Instance.Objects) {
//           test.addAll(getKids(obj.getClassModel()));
            if (obj.getClassModel().getName().equals(type)) {
                list.add(obj);
            }
        }
        return list;
    }
    public static void Demo() {
        ClassFactory.Demo();
        try {
            DicoCompilerIntiator.Instance.CreateAndComplieFiles();

            for (ClassModel cls : ClassFactory.Instance.Classess) {
                for (int i = 0; i < 10; i++) {
                    ObjectModel object = Pool.Instance.createObject(cls, cls.getName().toLowerCase() + i);
                    DicoCompilerIntiator.Instance.InvokeToString(object);
                }

            }
        } catch (ComplierFailedException | ObjectCreationException | ClassNotFoundException ex) {
            Logger.getLogger(DicoCompilerIntiator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Demo();
    }
}
