/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author k.shehady
 */
public class TestReflection {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ClassLoader classLoader = TestReflection.class.getClassLoader();

        try {
            Class managerClass = classLoader.loadClass("Test.Manager");
            System.out.println("Manager.getName() = " + managerClass.getName());

            Constructor constrManager = managerClass.getConstructor(int.class);

            Object manager = constrManager.newInstance(1500);

            try {
                Method method = managerClass.getDeclaredMethod("toString", null);
                Object str = method.invoke(manager);
                String text = str.toString();
                System.out.println(text);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Class personClass = classLoader.loadClass("Test.Person");
            System.out.println("Person.getName() = " + personClass.getName());

            Constructor constrPerson = personClass.getConstructor(String.class, managerClass);
            Object person = constrPerson.newInstance("Kassem", manager);

            try {
                Method method = personClass.getDeclaredMethod("toString", null);
                Object str = method.invoke(person);
                String text = str.toString();
                System.out.println(text);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
