/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import dico.exceptions.FriendIsEnemyOrEnemyIsFriendException;
import dico.exceptions.FriendOrEnemyAlreadyExistsException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author k.shehady
 */
public class Dictionary<T> implements Collection<T>, Iterable<T> {

    private Map<T, Pair<T>> elements;

    public T Test;
    public String name;
    public ClassModel classModel;

    public Dictionary(String name, ClassModel classModel) {
        this.name = name;
        this.classModel = classModel;
        this.elements = new HashMap<>();
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    public void setClassModel(ClassModel classModel) {
        this.classModel = classModel;
    }

    /*public Dictionary() {(Class<T> type) {     
       elements = new HashMap<>();
    }*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String getGenericName() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();
    }

    @Override
    public boolean add(T e) {
        Pair<T> pair = new Pair<>();
        elements.put(e, pair);
        return true;
    }

    public void printSet(Set<T> set) {
        for (T s : set) {
            System.out.println("\t" + s);
        }
    }

    public void printPair(T e) {
        Pair<T> p = elements.get(e);
        System.out.println("Friends of " + e + ":");
        printSet(p.getFriends());
        System.out.println("Enemies of " + e + ":");
        printSet(p.getEnemies());
    }

    public Pair<T> getPair(T e) {
        return elements.get(e);
    }

    public Set<T> findFriends(T e) {
        if (elements.containsKey(e)) {
            Pair<T> p = elements.get(e);
            return p.getFriends();
        }
        return null;
    }

    public Set<T> findEnemies(T e) {
        if (elements.containsKey(e)) {
            Pair<T> p = elements.get(e);
            return p.getEnemies();
        }
        return null;
    }

    public boolean addFriend(T element, T friend) throws FriendIsEnemyOrEnemyIsFriendException, FriendOrEnemyAlreadyExistsException {
        /*to understand the comments:
          consider I'am the variable 'element' and I want to add the variable 'friend' to my friends*/

        Pair<T> elementPair = elements.get(element);              //my pair
        Pair<T> friendSet = elements.get(friend);           //his pair        

        System.out.println(elementPair);

        if (elementPair == null) {
            throw new NullPointerException(" elementPair not added to dictionary!");
        }
        if (friendSet == null) {
            throw new NullPointerException( " friendSet not added to dictionary!");
        }
        //|| friendSet == null || mySet.getFriends() == null || mySet.getEnemies() == null || friendSet.getEnemies() == null || friendSet.getFriends() == null
        if (elementPair.getEnemies() != null && elementPair.getEnemies().contains(friend)) {
            throw new FriendIsEnemyOrEnemyIsFriendException("A chosen friend is already an enemy or a chosen enemy is already a friend.");
        }
        if (elementPair.getFriends() != null && elementPair.getFriends().contains(friend)) {
            throw new FriendOrEnemyAlreadyExistsException("A chosen friend or a chosen enemy already exists.");
        }

        for (T elem : elementPair.getFriends()) {                 //for each one as my friends
            Pair<T> individual = elements.get(elem);
            individual.addFriend(friend);                   //add him
            individual.addFriends(friendSet.getFriends());  //add his friends
            individual.addEnemies(friendSet.getEnemies());  //add his enemies
        }

        for (T elem : friendSet.getFriends()) {             //for each one as his friends
            Pair<T> individual = elements.get(elem);
            individual.addFriend(element);                  //add myself
            individual.addFriends(elementPair.getFriends());      //add my friends
            individual.addEnemies(elementPair.getEnemies());      //add my enemies
        }

        for (T elem : elementPair.getEnemies()) {                 //for each one as my enemy
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(friend);                    //add him
            individual.addEnemies(friendSet.getFriends());  //add his friends (to enemies)
        }

        for (T elem : friendSet.getEnemies()) {             //for each one as his enemy
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(element);                   //add me
            individual.addEnemies(elementPair.getFriends());      //add my friends (to enemies)
        }

        friendSet.addFriends(elementPair.getFriends());           //my friends must be his friends
        friendSet.addEnemies(elementPair.getEnemies());           //my enemies must be his enemies
        elementPair.addFriends(friendSet.getFriends());           //his friends must be my friends
        elementPair.addEnemies(friendSet.getEnemies());           //his enemies must be my enemies

        friendSet.addFriend(element);                       //i must be his friend
        elementPair.addFriend(friend);                            //he must be my friend
        return true;
    }

    public boolean addEnemy(T element, T enemy) throws FriendIsEnemyOrEnemyIsFriendException, FriendOrEnemyAlreadyExistsException {
        /*to understand the comments:
          consider I'am the variable 'element' and I want to add the variable 'enemy' to my enemies*/

        Pair<T> mySet = elements.get(element);              //my pair
        Pair<T> enemySet = elements.get(enemy);             //his pair
        if (mySet == null || enemySet == null) {
            throw new NullPointerException("Sets are still null.");
        }
        //|| mySet.getFriends() == null || mySet.getEnemies() == null || enemySet.getEnemies() == null || enemySet.getFriends() == null
        if (mySet.getFriends().contains(enemy)) {
            throw new FriendIsEnemyOrEnemyIsFriendException("A chosen friend is already an enemy or a chosen enemy is already a friend.");
        }
        if (mySet.getEnemies().contains(enemy)) {
            throw new FriendOrEnemyAlreadyExistsException("A chosen friend or a chosen enemy already exists.");
        }

        for (T elem : mySet.getFriends()) {                 //for each one as my friend
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(enemy);                     //add the enemy (to enemies)
            individual.addEnemies(enemySet.getFriends());   //add friends of enemy (to enemies)
            individual.addFriends(enemySet.getEnemies());   //add enemies of enemy (to friends)
        }

        for (T elem : enemySet.getFriends()) {              //for each one as his friend
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(element);                   //add me (to enemies)
            individual.addEnemies(mySet.getFriends());      //add my friends (to enemies)
            individual.addFriends(mySet.getEnemies());      //add my enemies (to friends)
        }

        for (T elem : mySet.getFriends()) {                 //for each one as my friends
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(enemy);                     //add him to enemies
            individual.addEnemies(enemySet.getFriends());   //add his friends (to enemies)
        }

        for (T elem : enemySet.getEnemies()) {              //for each one as his enemy
            Pair<T> individual = elements.get(elem);
            individual.addFriend(element);                  //add me (to friends)
            individual.addFriends(mySet.getFriends());      //add my friends (to friends)
        }

        enemySet.addFriends(mySet.getEnemies());            //add my enemies to his friends' list
        enemySet.addEnemies(mySet.getFriends());            //add my friends to his enemies' list
        mySet.addFriends(enemySet.getEnemies());            //add his enemies to my friends
        mySet.addEnemies(enemySet.getFriends());            //add his friends to my enemies

        enemySet.addEnemy(element);                         //i must be his enemey
        mySet.addEnemy(enemy);                              //he must be my enemy
        return true;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.containsKey(o);
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            private boolean usedNext = false;
            private T current;

            @Override
            public boolean hasNext() {
                return elements.keySet().iterator().hasNext();
            }

            @Override
            public T next() {
                usedNext = true;
                current = elements.keySet().iterator().next();
                return current;
            }

            @Override
            public void remove() {
                if (!usedNext) {
                    return;

                }
                usedNext = false;
                elements.remove(current);
            }

        };
        return it;
    }

    @Override
    public Object[] toArray() {
        return elements.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elements.keySet().toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        T element = (T) o;
        Pair<T> mySet = elements.get(o);
        for (T elem : mySet.getFriends()) {
            Pair<T> individual = elements.get(elem);
            individual.removeFriend(element);
        }
        for (T elem : mySet.getEnemies()) {
            Pair<T> individual = elements.get(elem);
            individual.removeEnemy(element);
        }
        if (elements.remove(element) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!elements.containsKey(o)) {
                return false;
            }
        }
        return 2 != 4;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        elements.putAll((Map<? extends T, Pair<T>>) c);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (Object o : c) {
            if (!elements.containsKey(o)) {
                elements.remove(o);
            }
        }
        return true;
    }

    public ArrayList<T> getKeySet() {
        ArrayList<T> keySet = new ArrayList<>();
        for (T o : elements.keySet()) {
            keySet.add(o);
        }
        return keySet;
    }

    @Override
    public void clear() {
        elements.clear();
    }

    public static void Demo() {

        try {
            ClassModel cls = new ClassModel();
            cls.setName("TestPerson");
            Dictionary<TestPerson> dico = new Dictionary<>("TestPerson Dictionary", cls);
            TestPerson fawze = new TestPerson("Fawze");
            TestPerson fakhre = new TestPerson("Fakhre");
            TestPerson fathe = new TestPerson("Fathe");
            TestPerson jawad = new TestPerson("Jawad");
            TestPerson mofeed = new TestPerson("Mofeed");
            TestPerson sobhe = new TestPerson("Sobhe");
            TestPerson lotfi = new TestPerson("Lotfi");
            dico.add(fawze);
            dico.add(fakhre);
            dico.add(fathe);
            dico.add(sobhe);
            dico.add(mofeed);
            dico.add(lotfi);
            dico.addFriend(lotfi, mofeed);
            dico.addFriend(mofeed, fathe);
            for (TestPerson o : dico) {
                dico.findFriends(o);
            }
            //System.out.println(dico.contains(mofeed));
            ArrayList<TestPerson> list = new ArrayList<>();
            list.add(mofeed);
            list.add(sobhe);
            dico.removeAll(list);
            System.out.println(dico.contains(mofeed));
            System.out.println(dico.contains(fakhre));
        } catch (FriendIsEnemyOrEnemyIsFriendException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FriendOrEnemyAlreadyExistsException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] myArgs) {
        Demo();
    }
}
