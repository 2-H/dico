/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.exceptions;

import java.io.IOException;

/**
 *
 * @author pc
 */
public class ClassNotFoundException extends IOException {

    public ClassNotFoundException() {
    }

    public ClassNotFoundException(String message) {
        super(message);
    }
    
}
