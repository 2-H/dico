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
    private ClassModel FindInstance(ArrayList<ClassModel>ca,String name){
        
        for(ClassModel c:ca){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    
        
    }
         private void addToCombo(ComboBox combo, ArrayList<ClassModel> myArrStr) {
        for (ClassModel str : myArrStr) {
            combo.getItems().add(str.getName());
        }
    }
@FXML
     private void ApplyMethodHandler(ActionEvent event){
         
       
         
     String s= comboMethods.getSelectionModel().getSelectedItem().toString();
       String s2= comboClass2.getSelectionModel().getSelectedItem().toString();
        String s1= comboClass1.getSelectionModel().getSelectedItem().toString();
        
        ClassModel c1=FindInstance(Objects,s1);
        ClassModel c2=FindInstance(Objects,s2);
       
        if(s.equals("equal"))
            if(c1.equals(c2)){
              tf.setText("they are equal");
            }
              else{  tf.setText("they are not equal");}
        
        
        //
        if(s.equals("compareTo")){
            if(c1.getClass()==c2.getClass()){
//             Comparable c1=(Comparable) s1;
//             Comparable c2=(Comparable) s2;  
             if(c1.compareTo(c2)>0){
                 tf.setText(c1.getName()+"is greater");
             }
             else 
                 if(c1.compareTo(c2)<0)
                 {
                      tf.setText(c2.getName()+" is greater");
                 }
             else
                      tf.setText("they are equal");
                 
             }
            
            else
                 tf.setText("2 Objects of different classes cannot be compared");
             
           
           
           
        }
            
               
            
        
        
        if(s.equals("Hashcode")){
            String a=" "+c1.hashCode();
         tf.setText(a);
            
            
        }
        
        if(s.equals("ToString")){
            tf.setText(c1.toString());
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
        ArrayList<ClassModel> objlist = new ArrayList<>();
//        
//        objlist.add("jhh");
//        objlist.add("Jad");
//        objlist.add("Jayer");
//        
        addToCombo(comboClass1,objlist);
        addToCombo(comboClass2,objlist);

   

 
 for(ClassModel o:Objects){
     objlist.add(o);
     
     
     
 }
 
  addToCombo(comboClass1,objlist);
        addToCombo(comboClass2,objlist);
 
 
        comboClass2.getSelectionModel().selectFirst();//just to avoid NullPointrException
      comboClass1.getSelectionModel().selectFirst();
 
 
 
 
 


        

    }
     }
    

