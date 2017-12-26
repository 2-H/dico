/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.CallingMethods;

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
       String s1= comboClass2.getSelectionModel().getSelectedItem().toString();
        String s2= comboClass1.getSelectionModel().getSelectedItem().toString();
        
        
        if(s.equals("equal"))
            if(s2.equals(s1)){
              tf.setText("Hi");
            }
              else{  tf.setText("Bye");}
        
        
        
        if(s.equals("compareTo")){
            if(s2.compareTo(s1)>0){
             
                  tf.setText(" "+s2+"    is greater");
            }
            else if(s2.compareTo(s1)<0){
           tf.setText("  "+s1+"    is greater");
             
            }
            else
               tf.setText("equal!");
            
        }
        
        if(s.equals("Hashcode")){
            String a=""+s2.hashCode();
         tf.setText(a);
            
            
        }
        
        if(s.equals("ToString")){
            tf.setText("my name is    "+s2);
        }
      
//         if(s.equals("equal")){
////             if(s1.equals(s2)){
//               tf.setText("they are equal");
////                 
////             }
//                 
//         }
//         if(s.equals("compareTo"))
//             tf.setText("hi");
////             if(s1.compareTo(s2)>0){
////                 
////                 tf.setText("first object is greater");
////                 
////                 
////             }
////         
////       else
////                 if(s1.compareTo(s2)<0)
////                 {
////                     tf.setText("second object is greater");
////                 }
////         else
////                     tf.setText("they are equal");
////     
//         
//         if(s.equals("Hashcode")){
//             String t= s1.hashCode()+"";
//             tf.setText(t);
//         }
//         
//         if(s.equals("ToString")){
//             
//             tf.setText(s1);
//         }
//           
//         
//         
//         
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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

        addToComboBox(comboMethods, classList);
        ArrayList<String> objlist = new ArrayList<>();
        
        objlist.add("jhh");
        objlist.add("Jad");
        objlist.add("Jayer");
        
        addToComboBox(comboClass1,objlist);
        addToComboBox(comboClass2,objlist);
        comboClass2.getSelectionModel().selectFirst();
        comboClass1.getSelectionModel().selectFirst();
        

    }
     }
    

