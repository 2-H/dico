/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.MediaShow;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Ali Al-Jobouri
 */
public class MediaShowMain extends Application {
    
     @Override
    public void start(Stage stage) throws Exception {
    
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("pic.png")));
        Parent root=new Parent() {
};
        try{
         root = FXMLLoader.load (getClass().getResource("MediaShowFXML.fxml"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);
        stage.setTitle("2H - Dico");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
