package dico.gui.Media;

import java.io.File;

public class Media {

    public static enum MediaType {
        VIDEO, PHOTO, AUDIO
    };
    private String address;
    private MediaType chosenType;

    public Media(String address, MediaType chosenType) {
        this.address = address;
        this.chosenType = chosenType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MediaType getChosenType() {
        return chosenType;
    }

    public void setChosenType(MediaType chosenType) {
        this.chosenType = chosenType;
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public static Media.MediaType getMediaType(String ext) {
        String[] photos = new String[]{"jpg", "jpeg", "png"};
        String[] videos = new String[]{"mp4"};
        String[] audio = new String[]{"mp3"};
        for (String tmp : photos) {
            if (ext.equalsIgnoreCase(tmp)) {
                return Media.MediaType.PHOTO;
            }
        }
        for (String tmp : videos) {
            if (ext.equalsIgnoreCase(tmp)) {
                return Media.MediaType.VIDEO;
            }
        }
        for (String tmp : audio) {
            if (ext.equalsIgnoreCase(tmp)) {
                return Media.MediaType.AUDIO;
            }
        }
        return null;    //if not supported
    }

}
