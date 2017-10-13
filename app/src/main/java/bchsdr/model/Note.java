package bchsdr.model;

import java.io.Serializable;

/**
 * Created by Maxime on 12/10/2017.
 */

public class Note implements Serializable{

    private int id_notes;
    private String title;
    private String description;
    private int id_journey;
    private String picture_location;
    private float latitude;
    private float longitude;

    public Note() {
        this.id_notes = -1;
        this.title = " ";
        this.description = " ";
        this.picture_location = " ";
        this.id_journey = -1;
        this.latitude = 0;
        this.longitude = 0;
    }

    public Note(int id_notes, String title, String description, int id_journey, String picture_location, float latitude, float longitude) {
        this.id_notes = id_notes;
        this.title = title;
        this.description = description;
        this.id_journey = id_journey;
        this.picture_location = picture_location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId_notes() {
        return id_notes;
    }

    public void setId_notes(int id_notes) {
        this.id_notes = id_notes;
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

    public String getPicture_location() {
        return picture_location;
    }

    public void setPicture_location(String picture_location) {
        this.picture_location = picture_location;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getId_journey() {
        return id_journey;
    }

    public void setId_journey(int id_journey) {
        this.id_journey = id_journey;
    }
}
