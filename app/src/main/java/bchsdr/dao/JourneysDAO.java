package bchsdr.dao;
import android.content.Context;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import bchsdr.model.Journey;

/**
 * Created by Rudy_DEAL on 10/10/2017.
 */

public class JourneysDAO {
    private static JourneysDAO journeysDAO;

    public static JourneysDAO getInstance() {
        if (journeysDAO == null){
            journeysDAO = new JourneysDAO();
        }
        return journeysDAO;
    }

    public  List<Journey> getDBJourneys(Context context){
        try {
            return ConnectionSQLiteHelper.getInstance(context).getDBJourneysDAO();
        } catch (ParseException e) {
            return null;
        }

    }

    public void editJourney(Context context, Journey journey) {
        if (journey.getId() < 0) {
            ConnectionSQLiteHelper.getInstance(context).insertJourney(journey);
        }
        else{
            ConnectionSQLiteHelper.getInstance(context).updateJourney(journey);
        }
    }

    public void deleteJourney(Context context, Journey journey) {
        ConnectionSQLiteHelper.getInstance(context).deleteJourney(journey.getId());
    }

}
