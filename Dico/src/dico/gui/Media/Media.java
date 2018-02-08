package dico.gui.Media;

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

}
