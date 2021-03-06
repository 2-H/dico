/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

import dico.gui.Media.Media;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author k.shehady
 */
public class Triplet<T> {

    private Set<T> friends = null;
    private Set<T> enemies = null;
    private Set<Media> medias = null;

    public Triplet() {
        friends = new HashSet<>();
        enemies = new HashSet<>();
        medias = new HashSet<>();
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }

    public Triplet(Set<T> friends, Set<T> enemies) {
        this.friends = friends;
        this.enemies = enemies;
        medias = new HashSet<>();
    }

    public Set<T> getFriends() {
        return friends;
    }

    public void setFriends(Set<T> friends) {
        this.friends = friends;
    }

    public Set<T> getEnemies() {
        return enemies;
    }

    public void setEnemies(Set<T> enemies) {
        this.enemies = enemies;
    }

    public int addFriend(T friend) {
        if (friends != null) {
            if (friends.contains(friend)) {
                return 0;   // friend already exists
            } else if (enemies.contains(friend)) {
                return -1;  // it's an enemy
            }
        }
        friends.add(friend);
        return 1;       // friend added
    }

    public void addFriends(Set<T> friends) {
        //Note: in HashSet item can't be duplicated (duplicate value will not be inserted)
        //this.friends.addAll(friends);
        for (T elem : friends) {
            addFriend(elem);
        }
    }

    public void addEnemies(Set<T> enemies) {
        this.enemies.addAll(enemies);
    }

    public int addEnemy(T enemy) {
        if (enemies.contains(enemy)) {
            return 0;   // enemy already exists
        } else if (friends.contains(enemy)) {
            return -1;  // it's a friend
        }
        enemies.add(enemy);
        return 1;       // enemy added
    }

    public boolean removeFriend(T friend) {
        if (friends.contains(friend)) {
            friends.remove(friend);
            return true;
        }
        return false;
    }

    public boolean removeEnemy(T enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
            return true;
        }
        return false;
    }

    public boolean addMedia(Media media) {
        if (!medias.contains(media)) {
            medias.add(media);
            return true;
        }
        return false;
    }

    public boolean removeMedia(Media media) {
        if (medias.contains(media)) {
            medias.remove(media);
            return true;
        }
        return false;
    }
}
