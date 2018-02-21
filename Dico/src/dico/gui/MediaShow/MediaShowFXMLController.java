/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dico.gui.MediaShow;

import Message.Message;
import dico.DictionaryFactory;
import dico.ObjectFactory;
import dico.exceptions.ObjectNotFoundException;
import dico.gui.Media.Media;
import dico.models.Dictionary;
import dico.models.ObjectModel;
import dico.models.Triplet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaShowFXMLController implements Initializable {

    @FXML
    private ComboBox comboDictionary;
    @FXML
    private ComboBox comboObjects;
    @FXML
    private ComboBox comboMedia;
    @FXML
    private ListView lstPaths;
    @FXML
    private MediaView mediaView;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnPausePlay;

    private String selectedDictionary;
    private Dictionary dic;
    private MediaPlayer mp;

    public ArrayList<String> getMedia(Media.MediaType mediaType) {
        ArrayList<String> paths = new ArrayList<>();
        if (comboObjects == null || comboObjects.getSelectionModel().getSelectedItem() == null) {
            return null;
        }
        String user = comboObjects.getSelectionModel().getSelectedItem().toString();
        Triplet<Object> triplet = null;
        try {
            triplet = dic.getPair(ObjectFactory.Instance.GetObject(user));
        } catch (ObjectNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        if (triplet != null) {
            for (Media m : triplet.getMedias()) {
                if (m.getChosenType().equals(mediaType)) {
                    paths.add(m.getAddress());
                }
            }
        }
        return paths;
    }

    public void runVideoAudio(String source) {
        imageView.setVisible(false);
        mediaView.setVisible(true);
        btnPausePlay.setVisible(true);
        String path = new File(source).getAbsolutePath();
        javafx.scene.media.Media m = new javafx.scene.media.Media(new File(path).toURI().toString());
        mp = new MediaPlayer(m);
        mediaView.setMediaPlayer(mp);
        mp.setAutoPlay(true);

    }

    public void showImage(String source) {
        imageView.setVisible(true);
        mediaView.setVisible(false);
        btnPausePlay.setVisible(false);
        if (mp != null) {
            mp.stop();
        }
        Image image = null;
        try {
            image = new Image(new FileInputStream(source));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        imageView.setImage(image);
    }

    @FXML
    public void fillObjects() {
        comboObjects.getItems().clear();
        selectedDictionary = comboDictionary.getSelectionModel().getSelectedItem().toString();
        Dictionary dictionary = DictionaryFactory.Instance.getDictionary(selectedDictionary);
        ArrayList<ObjectModel> arr = DictionaryFactory.Instance.getObjectsByType(dictionary);
        for (ObjectModel o : arr) {
            comboObjects.getItems().add(o.getVariableName());
        }
    }

    @FXML
    public void fillList() {
        lstPaths.getItems().clear();
        if (comboObjects.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            dic = DictionaryFactory.Instance.getDictionary(selectedDictionary);
            String selectedObject = comboObjects.getSelectionModel().getSelectedItem().toString();
            Object o = ObjectFactory.Instance.GetObject(selectedObject);
            Triplet<Object> t = dic.getPair(o);
            for (Media m : t.getMedias()) {
                lstPaths.getItems().add(m.getAddress());
            }
        } catch (ObjectNotFoundException ex) {
            Message.show("Fatal Error", "Object not found.", AlertType.ERROR);
        }
        comboMedia.setValue("All");
    }

    @FXML
    public void show() {
        if (lstPaths.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String path = lstPaths.getSelectionModel().getSelectedItem().toString();
        String ext = Media.getFileExtension(new File(path));
        if (Media.getMediaType(ext).equals(Media.MediaType.VIDEO) || Media.getMediaType(ext).equals(Media.MediaType.AUDIO)) {
            runVideoAudio(path);
        } else if (path != null && Media.getMediaType(ext).equals(Media.MediaType.PHOTO)) {
            showImage(path);
        }
        btnPausePlay.setText("Pause");
    }

    public void pausePlay() {
        if (btnPausePlay.getText().equals("Pause")) {
            btnPausePlay.setText("Play");
            mp.pause();
        } else if (btnPausePlay.getText().equals("Play")) {
            btnPausePlay.setText("Pause");
            mp.play();
        }
    }

    @FXML
    public void editOnList() {
        lstPaths.getItems().clear();
        String selectedType = comboMedia.getSelectionModel().getSelectedItem().toString();
        ArrayList<String> paths = null;
        if (comboDictionary == null || comboObjects == null) {
            return;
        }
        switch (selectedType) {
            case "All":
                fillList();
                return;
            case "Videos":
                paths = getMedia(Media.MediaType.VIDEO);
                break;
            case "Images":
                paths = getMedia(Media.MediaType.PHOTO);
                break;
            case "Audio":
                paths = getMedia(Media.MediaType.AUDIO);
                break;
            default:
                break;
        }
        lstPaths.getItems().addAll(paths);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (String str : DictionaryFactory.Instance.getDictionaryNames()) {
            comboDictionary.getItems().add(str);
        }
        btnPausePlay.setVisible(false);
        comboMedia.getItems().addAll("All", "Videos", "Images", "Audio");
    }
}
