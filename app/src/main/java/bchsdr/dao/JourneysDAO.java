package bchsdr.dao;
import android.content.Context;

import bchsdr.dao.ConnectionSQLiteHelper;
import java.text.ParseException;
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

    public void edit_journey(Context context, Journey journey) {
        if (journey.get_id() < 0) {
            ConnectionSQLiteHelper.getInstance(context).insertJourney(journey);
        }
        else{
            ConnectionSQLiteHelper.getInstance(context).updateJourney(journey);
        }
    }

}
