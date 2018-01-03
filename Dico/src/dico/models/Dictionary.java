/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

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

    public Dictionary() {
        elements = new HashMap<>();
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

    public void addFriend(T element, T friend) {
       
        Pair<T> mySet = elements.get(element);
        Pair<T> friendSet = elements.get(friend);
        
        //0-He must be added to each set of friends of my friends + his friends must be added 
        //  to each individual of my friends
        for(T elem : mySet.getFriends()){
             Pair<T> individual = elements.get(elem);
             individual.addFriend(friend);
             individual.addFriends(friendSet.getFriends());
        }
        
        //1-Me must be added to his friends + my friends must be added to each individual in the set of his friends
        for (T elem : friendSet.getFriends()) {
            Pair<T> individual = elements.get(elem);
            individual.addFriend(element);
            individual.addFriends(mySet.getFriends());
        }
        
        //2-My friends must be added to the set of his friends
        friendSet.addFriends(mySet.getFriends());
        
        //3-Friends of the the new friend must be added to the set of my friends
        mySet.addFriends(friendSet.getFriends());
         
        //4-Me must be his friend
        friendSet.addFriend(element); 
        
        //5-He must be my friend
        mySet.addFriend(friend);
        
    }
//    public void addFriend(T element, T friend) {
////        if(elements.get(element).getFriends()==null || elements.get(friend).getFriends()==null || elements.get(element)==null || elements.get(friend)==null){
////        return;
////        }
//        Pair<T> p = elements.get(element);
//        Pair<T> p2 = elements.get(friend);
//        Set<T> myFriends = p.getFriends();
//        Set<T> fof = p2.getFriends();
//        Set<T> eof = p2.getEnemies();
//        for (T elem : myFriends) {
//            Pair<T> tmp = elements.get(elem);
//            tmp.addFriend(friend);
//            tmp.getFriends().addAll(fof);
//        }
//        p.addFriend(friend);
//    
//        // tmp.getFriends().remove(elem);
//
//        p.getFriends().addAll(fof);
//        p.getEnemies().addAll(eof);
//
//        for (T elem : fof) {
//
//            Pair<T> tmp = elements.get(elem);
//
//            tmp.addFriend(element);
//            
//            tmp.getFriends().addAll(myFriends);
//            tmp.removeFriends(friend);
//            //tmp.removeFriends(friend);
////            if(tmp.getFriends().contains(element)){
////                tmp.getFriends().remove(element);
////            }
////             if(tmp.getFriends().contains(friend)){
////                tmp.getFriends().remove(friend);
////            }
//        }
//            
//
//    }

    public void addEnemy(T element, T enemy) {
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public void Demo() {
        Dictionary<String> dico = new Dictionary<>();
        dico.add("Ahmad");
        dico.add("Ali");
        dico.add("Hasan");
        dico.add("Fakhre");
        dico.add("Jad");
        dico.add("Bandar");
        dico.add("Jozef");
        dico.addFriend("Bandar", "Jozef");
        dico.addFriend("Ali", "Ahmad");
        dico.addFriend("Ali", "Hasan");
        dico.addFriend("Jad", "Bandar");
        dico.addFriend("Jad", "Fakhre");
        dico.addFriend("Jad", "Ali");
    }

    public static void main(String[] myArgs) {
        Dictionary<String> dico = new Dictionary<>();
        dico.Demo();
    }
}
