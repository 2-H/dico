/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.SearchForm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Me
 */
public class SearchForm extends Application{

    
    
    @Override
    public void start(Stage stage) throws Exception {
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("pic.png")));
    
        Parent root = FXMLLoader.load(getClass().getResource("SearchForm.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("2H - Dico");
        stage.setScene(scene);
        stage.show();    
    }
    public static void main(String[] args) {
        launch(args);
    }

    
}
