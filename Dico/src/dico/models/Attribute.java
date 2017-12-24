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
    private String type;
    private boolean useInEquals;
    private boolean useInCompareTo;

    public Attribute(String name, String type, boolean useInEquals, boolean useInCompareTo) {
        this.name = name;
        this.type = type;
        this.useInEquals = useInEquals;
        this.useInCompareTo = useInCompareTo;
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

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

}
