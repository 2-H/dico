/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.exceptions;

/**
 *
 * @author Me
 */
public class ObjectCreationException extends Exception {

    /**
     * Creates a new instance of <code>ObjectCreationException</code> without
     * detail message.
     */
    public ObjectCreationException() {
    }

    /**
     * Constructs an instance of <code>ObjectCreationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ObjectCreationException(String msg) {
        super(msg);
    }
}
