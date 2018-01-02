/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import dico.models.Dictionary;

/**
 *
 * @author k.shehady
 */
public class DictionaryFactory<T> {

    public  Dictionary<T> createDictionary(T instance) {
        Dictionary<T> dic = new Dictionary<>();

        return dic;
    }
}
