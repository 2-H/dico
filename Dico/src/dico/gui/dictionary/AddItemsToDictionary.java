/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.dictionary;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 *
 * @author User
 */
public class AddItemsToDictionary extends Application {
          @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("pic.png")));
        Parent root = FXMLLoader.load(getClass().getResource("AddItemsToMyDictionary.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("2H - Dictionary");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
