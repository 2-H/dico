/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

/**
 *
 * @author k.shehady
 */
 public class Person {

        private String name;

        private Manager m;

        public Person() {
        }

        public Person(String name, Manager m) {
            this.name = name;
            this.m = m;
        }

        @Override
        public String toString() {
            return "Person{" + "name=" + name + ", m=" + m + '}';
        }

    }
