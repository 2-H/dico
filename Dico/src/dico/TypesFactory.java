/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico;

import dico.models.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Singleton
 */
public class TypesFactory {

    public static Type INT = new Type("int", "", int.class, false);
    public static Type STRING = new Type("String", "", String.class, true);
    public static Type DOUBLE = new Type("Double", "", Double.class, true);
    public static Type DATE = new Type("Date", "java.util.", Date.class, true);

    public static final TypesFactory Instance = new TypesFactory();

    public ArrayList<Type> Types;

    private TypesFactory() {
        Types = new ArrayList<>();
        Types.add(INT);
        Types.add(STRING);
        Types.add(DOUBLE);
        Types.add(DATE);
    }

    public Type Get(String name) {
        for (Type t : Types) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

}
