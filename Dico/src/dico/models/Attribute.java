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
public class Attribute {

    private String name;
    private String valueString;

    private Type type;
    private boolean useInEquals;
    private boolean useInCompareTo;

    public Attribute(String name, Type type, boolean useInEquals, boolean useInCompareTo) {
        this.name = name;
        this.type = type;
        this.useInEquals = useInEquals;
        this.useInCompareTo = useInCompareTo;       
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public boolean isUseInEquals() {
        return useInEquals;
    }

    public void setUseInEquals(boolean useInEquals) {
        this.useInEquals = useInEquals;
    }

    public boolean isUseInCompareTo() {
        return useInCompareTo;
    }

    public void setUseInCompareTo(boolean useInCompareTo) {
        this.useInCompareTo = useInCompareTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
