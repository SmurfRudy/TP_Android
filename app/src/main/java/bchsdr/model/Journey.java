package bchsdr.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Maxime on 09/10/2017.
 */

public class Journey implements Serializable {
    private int id;
    private String name;
    private Calendar from;
    private Calendar to;
    private String description;

    public Journey(){
        name="";
        from = Calendar.getInstance();
        from.set(Calendar.HOUR_OF_DAY, 0);
        to = Calendar.getInstance();
        to.set(Calendar.HOUR_OF_DAY, 0);
        description="";
        id = -1;
    }




    public Journey (String name, Calendar from, Calendar to, int id, String description) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.id = id;
        this.description =description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getFrom() {
        return from;
    }

    public void setFrom(Calendar from) {
        this.from = from;
    }

    public Calendar getTo() {
        return to;
    }

    public void setTo(Calendar to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
