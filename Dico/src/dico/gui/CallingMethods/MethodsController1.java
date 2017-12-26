/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.CallingMethods;
import dico.ObjectFactory;
import dico.models.ClassModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author user
 */
public class MethodsController1 implements Initializable {
    ArrayList<ClassModel> Objects=ObjectFactory.Instance.Objects;

      @FXML
    private ComboBox comboMethods;
    @FXML
    private ComboBox comboClass1;
    @FXML
    private ComboBox comboClass2;
    @FXML
    private Button btnApply;
    @FXML
    private TextField tf;

    /**
     * Initializes the controller class.
     */
    @FXML

    private void MethodDeciderHandler() {
int x=0;

        ArrayList<Method> methodList = new ArrayList<>();
        ArrayList<String> classList = new ArrayList<>();
        Method ct = new Method("compareTo", 1);
        methodList.add(ct);
        classList.add(ct.getName());
        Method eq = new Method("equal", 1);
        methodList.add(eq);
        classList.add(eq.getName());
        Method hc = new Method("Hashcode", 0);

        methodList.add(hc);
        classList.add(hc.getName());

        Method ts = new Method("ToString", 0);
         methodList.add(ts);
         classList.add(ts.getName());
         
       String s= comboMethods.getSelectionModel().getSelectedItem().toString();

        
         for (Method mth : methodList) {
            if(mth.getName().equals(s))
                x=mth.getPara();
            
        
         }
         if(x==0)
             comboClass2.setDisable(true);
         else
             comboClass2.setDisable(false);
    }

    private void addToComboBox(ComboBox combo, ArrayList<String> myArrStr) {
        for (String str : myArrStr) {
            combo.getItems().add(str);
        }
    }
@FXML
     private void ApplyMethodHandler(ActionEvent event){
         
       
         
     String s= comboMethods.getSelectionModel().getSelectedItem().toString();
       Object s2= comboClass2.getSelectionModel().getSelectedItem().toString();
        Object s1= comboClass1.getSelectionModel().getSelectedItem().toString();
        
        
        if(s.equals("equal"))
            if(s1.equals(s2)){
              tf.setText("they are equal");
            }
              else{  tf.setText("they are not equal");}
        
        
        //
        if(s.equals("compareTo")){
            if(s1.getClass()==s2.getClass()){
             Comparable c1=(Comparable) s1;
             Comparable c2=(Comparable) s2;  
             if(c1.compareTo(c2)>0){
                 tf.setText(c1.toString()+"is greater");
             }
             else 
                 if(c1.compareTo(c2)<0)
                 {
                      tf.setText(c2.toString()+"is greater");
                 }
             else
                      tf.setText("they are equal");
                 
             }
            
            else
                 tf.setText("2 Objects of different classes cannot be compared");
             
           
           
           
        }
            
               
            
        
        
        if(s.equals("Hashcode")){
            String a=""+s1.hashCode();
         tf.setText(a);
            
            
        }
        
        if(s.equals("ToString")){
            tf.setText(s2.toString());
        }
      
        }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        
//
//        
tf.setEditable(false);
    ArrayList<Method> methodList = new ArrayList<>();
       ArrayList<String> classList = new ArrayList<>();
       Method ct = new Method("compareTo", 1);
        methodList.add(ct);
        classList.add(ct.getName());
       Method eq = new Method("equal", 1);
        methodList.add(eq);
       classList.add(eq.getName());
       Method hc = new Method("Hashcode", 0);
         Method ts = new Method("ToString", 0);
         methodList.add(ts);
         classList.add(ts.getName());

        methodList.add(hc);
        classList.add(hc.getName());
//
        addToComboBox(comboMethods, classList);
        ArrayList<String> objlist = new ArrayList<>();
//        
//        objlist.add("jhh");
//        objlist.add("Jad");
//        objlist.add("Jayer");
//        
        addToComboBox(comboClass1,objlist);
        addToComboBox(comboClass2,objlist);

   

 
 for(Object o:Objects){
     objlist.add(o.toString());
     
     
     
 }
 
  addToComboBox(comboClass1,objlist);
        addToComboBox(comboClass2,objlist);
 
 
        comboClass2.getSelectionModel().selectFirst();//just to avoid NullPointrException
      comboClass1.getSelectionModel().selectFirst();
 
 
 
 
 


        

    }
     }
    

