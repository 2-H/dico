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
import dico.models.ObjectModel;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class ObjectFactory {

    public static final ObjectFactory Instance = new ObjectFactory();
    public static ArrayList<ObjectModel> Objects;

    public ObjectFactory() {
        Objects = new ArrayList<>();
    }

    public ObjectModel GetObject(String name) throws ObjectNotFoundException {
        for (ObjectModel cls : Objects) {
            String s=cls.toString();
            if (s.equals(name)) {
                return cls;
            }
        }
        throw new ObjectNotFoundException("Object Not Found");
    }

    public void createObject(ClassModel modelClass, String variableName) throws ComplierFailedException, ObjectCreationException, ClassNotFoundException {
        if (Objects.size() == 0) {
            DicoCompilerIntiator.Instance.CreateAndComplieFiles();
        }

        ObjectModel object = DicoCompilerIntiator.Instance.createObject(modelClass, variableName);

        Objects.add(object);

    }
}
