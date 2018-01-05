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
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 *
 * @author k.shehady
 */
public class DictionaryFactory {
    
    public static ArrayList<Dictionary> Dictionaries = new ArrayList<>();
    
    public static void createDictionary(ClassModel cls) {
       // ParameterizedType t = (ParameterizedType) cls; // OtherClass<String>
        //Dictionary<?> dic = (Dictionary<?>) t.getActualTypeArguments()[0]; // Class<String>

       // Dictionaries.add(dic);
    }
    
    public static void main(String[] args) throws ComplierFailedException, ClassNotFoundException {
        ClassFactory.CreateDemoClass();
         DicoCompilerIntiator.Instance.CreateAndComplieFiles();
        for (ClassModel c : ClassFactory.Classess) {
            DictionaryFactory.createDictionary(c);
        }
        
    }
}
