package Message;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Message {

    public static void show(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
