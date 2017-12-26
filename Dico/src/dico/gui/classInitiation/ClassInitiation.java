/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.classInitiation;

import dico.ClassFactory;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Hussien Wehbi
 */
public class ClassInitiation extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    
        stage.getIcons().add(new Image(getClass().getResourceAsStream("pic.png")));
        Parent root=new Parent() {
};
        try{
         root = FXMLLoader.load (getClass().getResource("ClassInitiation.fxml"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
