/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import dico.compiler.DicoCompilerIntiator;
import dico.exceptions.ComplierFailedException;
import dico.exceptions.FriendIsEnemyOrEnemyIsFriendException;
import dico.exceptions.FriendOrEnemyAlreadyExistsException;
import dico.models.ClassModel;
import dico.models.Dictionary;
import dico.models.MainType;
import dico.models.ObjectModel;
import dico.models.TestPerson;
import dico.models.TestShape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author k.shehady
 */
public class DictionaryFactory {

    public static final DictionaryFactory Instance = new DictionaryFactory();

    private Map<String, Dictionary> Dictionaries;

    private DictionaryFactory() {
        Dictionaries = new HashMap<>();
    }

    public Dictionary createDictionary(ClassModel cls, String Dicname) {
        Dictionary<ObjectModel> d = new Dictionary<ObjectModel>(Dicname, cls);//
        //Check for duplicates
        Dictionaries.put(d.getName(), d);
        return d;
    }

    public ArrayList<ObjectModel> getObjectsByType(Dictionary d) {
        ArrayList<ObjectModel> list = new ArrayList<>();
        for (ObjectModel obj : ObjectFactory.Instance.Objects) {
            if (obj.getClassModel().getName().equals(d.getClassModel().getName())) {
                list.add(obj);
            }
        }
        return list;
    }

    public ArrayList<String> getDictionaryNames() {
        ArrayList<String> list = new ArrayList<>();
        for (Dictionary d : Dictionaries.values()) {
            list.add(d.getName());
        }
        return list;
    }

    public Dictionary getDictionary(String name) {
        return Dictionaries.get(name);
    }

    public static void Demo() throws ComplierFailedException, ClassNotFoundException, Exception {
        ObjectFactory.Demo();
        
        for (ClassModel c : ClassFactory.Classess) {
            Dictionary<ObjectModel> dic = DictionaryFactory.Instance.createDictionary(c, "Dictionary " + c.getName());
            for (ObjectModel objectModel : DictionaryFactory.Instance.getObjectsByType(dic)) {
                dic.add(objectModel);
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, Exception {
        Demo();
    }
}
