/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.TestGeneric;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author k.shehady
 */
public class DictionaryFactory<T> {

    public static ArrayList<Dictionary> Dictionaries = new ArrayList<>();
    public ArrayList<T> Test = new ArrayList<>();

    public static void createDictionary(ClassModel cls) {
        Dictionary d = new Dictionary(cls.getClassReference());
        //Dictionary a = new Dictionary<TestGeneric>(TestGeneric.class) {};      
       // System.out.println(((ParameterizedType) a.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        
        
        Dictionaries.add(d);
    }

    public static void main(String[] args) throws ComplierFailedException, ClassNotFoundException, Exception {
        ClassFactory.CreateDemoClass();
        DicoCompilerIntiator.Instance.CreateAndComplieFiles();
        for (ClassModel c : ClassFactory.Classess) {
            DictionaryFactory.createDictionary(c);
        }

    }
}


/*
class ParameterizedClass<T> {
    private Class<T> type;

    public static <T> ParameterizedClass<T> of(Class<T> type) {
        return new ParameterizedClass<T>(type);
    }

    private ParameterizedClass(Class<T> type) {
        this.type = type;
    }

    // Do something useful with type
}*/
