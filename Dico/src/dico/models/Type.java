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
public class Type {

    private String name;
    private String packageName;
    private Class className;
    private boolean object;
    private boolean customType;

    public Type() {

    }

    public boolean isCustomType() {
        return customType;
    }

    public void setCustomType(boolean customType) {
        this.customType = customType;
    }

    public Type(String name, String packageName, Class className, boolean isObject, boolean customType) {
        this.customType = customType;
        this.name = name;
        this.className = className;
        this.packageName = packageName;
        this.object = isObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public boolean isObject() {
        return object;
    }

    public void setIsObject(boolean isObject) {
        this.object = isObject;
    }
}
