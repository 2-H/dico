/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.exceptions;

/**
 *
 * @author Ali Al-Jobouri
 */
public class FriendIsEnemyOrEnemyIsFriendException extends Exception {

    public FriendIsEnemyOrEnemyIsFriendException() {
    }

    public FriendIsEnemyOrEnemyIsFriendException(String message) {
        super(message);
    }
}
