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

    public Journey(){
        name="";
        from = Calendar.getInstance();
        from.set(Calendar.HOUR_OF_DAY, 0);
        to = Calendar.getInstance();
        to.set(Calendar.HOUR_OF_DAY, 0);
        id = -1;
    }




    public Journey (String name, Calendar from, Calendar to, int id) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.id = id;
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

}
