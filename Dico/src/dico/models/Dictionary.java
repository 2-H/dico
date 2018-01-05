/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author k.shehady
 */
public class Dictionary<T> implements Collection<T>, Iterable<T> {

    private Map<T, Pair<T>> elements;

    public T Test;
    
    public Dictionary(Class<T> type) {       //

        elements = new HashMap<>();
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

    public Set<T> findFriends(T e) {
        //  Set<T> friends = new HashSet<>();
        if (elements.containsKey(e)) {
            Pair<T> p = elements.get(e);
            return p.getFriends();
        }
        return null;
//        (a)The	friend	of	my	friend	is	my	friend. 
//        (b)The	friend	of	my	enemy	is	my	enemy. 

    }

    public Set<T> findEnemies(T e) {
        if (elements.containsKey(e)) {
            Pair<T> p = elements.get(e);
            return p.getEnemies();
        }
        return null;
    }

    public int addFriend(T element, T friend) {
        /*to understand the comments:
          consider I'am the variable 'element' and I want to add the variable 'friend' to my friends*/

        Pair<T> mySet = elements.get(element);              //my pair
        Pair<T> friendSet = elements.get(friend);           //his pair

        if (mySet.getEnemies().contains(friend)) {
            return -1;                                      //if he is my enemy -> I can't add him as friend
        }
        if (mySet.getFriends().contains(friend)) {
            return 0;                                       //if he is already my friend -> No need to add him
        }

        for (T elem : mySet.getFriends()) {                 //for each one as my friends
            Pair<T> individual = elements.get(elem);
            individual.addFriend(friend);                   //add him
            individual.addFriends(friendSet.getFriends());  //add his friends
            individual.addEnemies(friendSet.getEnemies());  //add his enemies
        }

        for (T elem : friendSet.getFriends()) {             //for each one as his friends
            Pair<T> individual = elements.get(elem);
            individual.addFriend(element);                  //add myself
            individual.addFriends(mySet.getFriends());      //add my friends
            individual.addEnemies(mySet.getEnemies());      //add my enemies
        }

        for (T elem : mySet.getEnemies()) {                 //for each one as my enemy
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(friend);                    //add him
            individual.addEnemies(friendSet.getFriends());  //add his friends (to enemies)
        }

        for (T elem : friendSet.getEnemies()) {             //for each one as his enemy
            Pair<T> individual = elements.get(elem);
            individual.addEnemy(element);                   //add me
            individual.addEnemies(mySet.getFriends());      //add my friends (to enemies)
        }

        friendSet.addFriends(mySet.getFriends());           //my friends must be his friends
        friendSet.addEnemies(mySet.getEnemies());           //my enemies must be his enemies
        mySet.addFriends(friendSet.getFriends());           //his friends must be my friends
        mySet.addEnemies(friendSet.getEnemies());           //his enemies must be my enemies

        friendSet.addFriend(element);                       //i must be his friend
        mySet.addFriend(friend);                            //he must be my friend
        return 1;
    }

    public int addEnemy(T element, T enemy) {
        /*to understand the comments:
          consider I'am the variable 'element' and I want to add the variable 'enemy' to my enemies*/

        Pair<T> mySet = elements.get(element);              //my pair
        Pair<T> enemySet = elements.get(enemy);             //his pair

        if (mySet.getFriends().contains(enemy)) {
            return -1;                                      //if he is in my friends' list
        }
        if (mySet.getEnemies().contains(enemy)) {
            return 0;                                       //if he is already an enemy
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
        return 1;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
