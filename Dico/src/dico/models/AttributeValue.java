/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.models;

/**
 *
 * @author k.shehady
 */
public class AttributeValue<T> {

    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    private T value;

    public AttributeValue(Attribute attribute) {
        this.attribute = attribute;
    }

    public AttributeValue(Attribute attribute, T value) {
        this.attribute = attribute;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
