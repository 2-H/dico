/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.exceptions;

import java.io.IOException;

/**
 *
 * @author Ali Al-Jobouri
 */
public class DuplicateAttributesException extends IOException {

    public DuplicateAttributesException() {
    }

    public DuplicateAttributesException(String message) {
        super(message);
    }

}
