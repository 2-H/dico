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

    public void createDictionary(ClassModel cls, String Dicname) {
        Dictionary<ObjectModel> d = new Dictionary<ObjectModel>();//cls.getClassReference()
        d.setName(Dicname);
        //Check for duplicates
        Dictionaries.put(d.getName(), d);
    }

    public ArrayList<String> getObjectsByType(Dictionary d) {
        try {
            ArrayList<String> list = new ArrayList<>();
            // TestPerson ali = new TestPerson("Ali");
            //TestPerson hassan = new TestPerson("Hassan");
            // TestPerson zahi = new TestPerson("zahi");
            
            d.add("Person 1");
            d.add("Person 2");
            d.add("Person 3");
            d.add("Person 4");
            list.add("Person 1");
            list.add("Person 2");
            list.add("Person 3");
            list.add("Person 4");
            d.addFriend("Person 1", "Person 2");
            d.addFriend("Person 1", "Person 3");
            d.addEnemy("Person 1", "Person 4");
            
            // d.addEnemy(hassan, zahi);
            return list;
        } catch (FriendIsEnemyOrEnemyIsFriendException ex) {
            Logger.getLogger(DictionaryFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FriendOrEnemyAlreadyExistsException ex) {
            Logger.getLogger(DictionaryFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public static void createDemoDictionaries() {
        Dictionary d = new Dictionary<TestPerson>();
        TestPerson ali = new TestPerson("Ali");
        TestPerson hassan = new TestPerson("Hassan");

        d.setName("Person");

        DictionaryFactory.Instance.Dictionaries.put(d.getName(), d);

        Dictionary d2 = new Dictionary<TestPerson>();
        TestShape square = new TestShape("Square");
        TestShape circle = new TestShape("Circle");

        d2.setName("Shape");

        DictionaryFactory.Instance.Dictionaries.put(d2.getName(), d2);

    }

    public static void main(String[] args) throws ComplierFailedException, ClassNotFoundException, Exception {
        ClassFactory.CreateDemoClass();
        DicoCompilerIntiator.Instance.CreateAndComplieFiles();
        for (ClassModel c : ClassFactory.Classess) {
            DictionaryFactory.Instance.createDictionary(c, "Dictionary " + c.getName());
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
