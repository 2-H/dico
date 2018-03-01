/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import static dico.ClassFactory.Classess;
import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.DuplicateAttributesException;
import dico.exceptions.DuplicateClassesException;
import dico.exceptions.ObjectCreationException;
import dico.exceptions.ObjectNotFoundException;
import dico.models.Attribute;
import dico.models.AttributeValue;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    public ArrayList<String> getObjectNames() {
        ArrayList<String> objects = new ArrayList<>();
        for (ObjectModel cls : Objects) {
            objects.add( cls.getVariableName());           
        }
        return objects;
    }

    public ObjectModel createObject(ClassModel modelClass, String variableName, Map<String, AttributeValue> attributeValues) throws ComplierFailedException, ObjectCreationException, ClassNotFoundException {

        if (modelClass.getClassReference() == null) {
            throw new ComplierFailedException("Compile First");
        }

        ObjectModel object = DicoCompilerIntiator.Instance.createObject(modelClass, variableName, attributeValues);

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

    public static void Demo() throws ObjectNotFoundException {
        Map<String, AttributeValue> attributeValues = new HashMap<>();

        try {
            ClassModel person = new ClassModel();
            person.setName("Person");
            Attribute atrId = new Attribute("id", TypesFactory.INT, true, true);

            attributeValues.put("id", new AttributeValue(atrId, Pool.Instance.GetValue(atrId, "1")));
            Attribute atrName = new Attribute("name", TypesFactory.STRING, true, true);

            attributeValues.put("name", new AttributeValue(atrName, Pool.Instance.GetValue(atrName, "ali")));

            ArrayList<Attribute> list = new ArrayList<>();
            list.add(atrId);
            list.add(atrName);

            person.setAttribute(list);
            ClassFactory.Instance.generateJavaCode(person);
            System.out.println(person.getJavaCode());

            ClassModel manager = new ClassModel();
            manager.setName("Manager");
            Attribute atrSalary = new Attribute("salary", TypesFactory.DOUBLE, true, true);
            attributeValues.put("salary", new AttributeValue(atrSalary, Pool.Instance.GetValue(atrSalary, "1700.25")));
            ArrayList<Attribute> list2 = new ArrayList<>();
            list2.add(atrSalary);
            manager.setAttribute(list2);
            manager.setParent(person);
            ClassFactory.Instance.generateJavaCode(manager);
            System.out.println(manager.getJavaCode());
            System.out.println(Arrays.toString(manager.getAttributesWithSuper().toArray()));

            ClassModel cto = new ClassModel();
            cto.setName("CTO");
            Attribute atrYears = new Attribute("yearsOfExperience", TypesFactory.INT, true, true);
            attributeValues.put("yearsOfExperience", new AttributeValue(atrYears, Pool.Instance.GetValue(atrYears, "8")));
            ArrayList<Attribute> list3 = new ArrayList<>();
            list3.add(atrYears);
            cto.setAttribute(list3);
            cto.setParent(manager);
            ClassFactory.Instance.generateJavaCode(cto);
            System.out.println(cto.getJavaCode());

            System.out.println(Arrays.toString(cto.getAttributesWithSuper().toArray()));
        } catch (DuplicateAttributesException | DuplicateClassesException ex) {
            System.out.println(ex.toString());
        }

        try {
            DicoCompilerIntiator.Instance.CreateAndComplieFiles();

            for (ClassModel cls : ClassFactory.Instance.Classess) {
                for (int i = 0; i < 10; i++) {
                    attributeValues.get("id").setValue(i);
                    attributeValues.get("name").setValue("hassan" + i);
                    ObjectModel object = Pool.Instance.createObject(cls, cls.getName().toLowerCase() + i,attributeValues);
                    DicoCompilerIntiator.Instance.InvokeToString(object);
                }

            }
        } catch (ComplierFailedException | ObjectCreationException | ClassNotFoundException ex) {
            Logger.getLogger(DicoCompilerIntiator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object GetValue(Attribute atr, String value) throws ObjectNotFoundException {
        System.out.println("Value " + value);
        if (atr.getType().isCustomType()) {
            Object ob = Pool.Instance.GetObject(value).getInstance();
            return ob;
            //return atr.getType().getClassName().cast(ob);
        }
        if (atr.getType().getClassName() == int.class) {
            return Integer.parseInt(value);
        } else if (atr.getType().getClassName() == Double.class) {
            return Double.parseDouble(value);
        } else if (atr.getType().getClassName() == String.class) {
            return value;
        }
        return value;
    }

    public static void main(String[] args) throws ObjectNotFoundException {
        Demo();
    }
}
