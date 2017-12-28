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
public class DicoClassNotFoundException extends IOException {

    public DicoClassNotFoundException() {
    }

    public DicoClassNotFoundException(String message) {
        super(message);
    }
    
}
