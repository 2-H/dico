/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.CallingMethods;

/**
 *
 * @author user
 */
public class Method {
    
    
    private String name;
    private int parameter;
    
    public Method(){
        
    }
    
    public Method(String name,int para){
        
        this.name=name;
        this.parameter=para;
    }
    
    public int getPara(){
        
        return this.parameter;
    }
    
    public String getName(){
        
        return this.name;
}

    public void setPara(int p){
        
       this.parameter=p;
    }
    
    public void setName(String n){
        
        this.name=n;
    }
}