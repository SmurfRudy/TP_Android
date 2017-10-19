package bchsdr.model;

import java.io.Serializable;

/**
 * Created by Maxime on 12/10/2017.
 */

public class Note implements Serializable{

    private int idNotes;
    private String title;
    private String description;
    private int idJourney;
    private String pictureLocation;
    private Float latitude;
    private Float longitude;

    public Note(int idJourney) {
        this.idNotes = -1;
        this.idJourney = idJourney;
        this.title = "";
        this.description = "";
        this.pictureLocation = "";
    }

    public Note(int idNotes, String title, String description, int idJourney, String pictureLocation, float latitude, float longitude) {
        this.idNotes = idNotes;
        this.title = title;
        this.description = description;
        this.idJourney = idJourney;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pictureLocation = pictureLocation;
    }

    public int getIdNotes() {
        return idNotes;
    }

    public void setIdNotes(int idNotes) {
        this.idNotes = idNotes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureLocation() {
        return pictureLocation;
    }

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public int getIdJourney() {
        return idJourney;
    }

    public void setIdJourney(int idJourney) {
        this.idJourney = idJourney;
    }
}
