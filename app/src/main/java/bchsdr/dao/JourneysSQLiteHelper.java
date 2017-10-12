package bchsdr.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bchsdr.model.Journey;

/**
 * Created by Rudy_DEAL on 10/10/2017.
 */

public class JourneysSQLiteHelper extends SQLiteOpenHelper {

    private static JourneysSQLiteHelper journeysSQLiteHelper;

    private static final String DB_NAME = "TP_android.sqlite";
    private static final int VERSION = 1;

    private static final String TABLE_JOURNEYS = "journeys";
    private static final String COL_JOURNEYS_ID = "_id";
    private static final String COL_JOURNEYS_DESTINATION = "destination";
    private static final String COL_JOURNEYS_STARTDATE   = "start_date";
    private static final String COL_JOURNEYS_ENDDATE = "end_date";
    private static final String COL_JOURNEYS_DESCRIPTION = "description";

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table journeys (_id INTEGER PRIMARY KEY AUTOINCREMENT, destination TEXT NOT NULL, start_date TEXT NOT NULL, end_date TEXT NOT NULL, description TEXT)");
        // some sample data

    };
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //à surcharger
    };
    public JourneysSQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static JourneysSQLiteHelper getInstance(Context context){
        if (journeysSQLiteHelper == null){
            journeysSQLiteHelper =new JourneysSQLiteHelper(context.getApplicationContext());
        }
        return journeysSQLiteHelper;
    }
    String toStringDate(Calendar cal){
        //DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }

    public long insertJourney(Journey journey) {
        ContentValues cv = new ContentValues();
        //cv.put(COL_JOURNEYS_ID, journey.get_id());
        cv.put(COL_JOURNEYS_DESTINATION, journey.getName());
        cv.put(COL_JOURNEYS_STARTDATE, toStringDate(journey.getFrom()));
        cv.put(COL_JOURNEYS_ENDDATE, toStringDate(journey.getTo()));
        cv.put(COL_JOURNEYS_DESCRIPTION, journey.getDescription());

        return getWritableDatabase().insertOrThrow(TABLE_JOURNEYS, null, cv);
    }
    public void deleteJourney(int id) {
        String idString = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_JOURNEYS_ID + " = ?";
        String[] selectionArgs = {idString};
        db.delete(TABLE_JOURNEYS, selection, selectionArgs);
        //db.close();
    }

    public int updateJourney(Journey journey) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_JOURNEYS_ID + " = ?";

        String idString = Integer.toString(journey.get_id());
        String[] selectionArgs = { idString };

        ContentValues cv = new ContentValues();
        cv.put(COL_JOURNEYS_ID, journey.get_id());
        cv.put(COL_JOURNEYS_DESTINATION, journey.getName());
        cv.put(COL_JOURNEYS_STARTDATE, toStringDate(journey.getFrom()));
        cv.put(COL_JOURNEYS_ENDDATE, toStringDate(journey.getTo()));
        cv.put(COL_JOURNEYS_DESCRIPTION, journey.getDescription());

        int i = db.update(TABLE_JOURNEYS, cv, selection, selectionArgs);
        //db.close();

        return i;
    }



    public Cursor queryJourneys() {
        // equivalent to "select * from table_journeys order by id asc"
        Cursor cursor = getReadableDatabase().query(TABLE_JOURNEYS,
                null, null, null, null, null, COL_JOURNEYS_ID + " asc");
        return cursor;
    }

    public  List<Journey> getDBJourneys() throws ParseException {

        List<Journey> journeys = new ArrayList<>();
        Cursor cursor = queryJourneys();
        while (cursor.moveToNext())
             {
                 Journey sejour =new Journey();
                 sejour.set_id(cursor.getInt(cursor.getColumnIndex(COL_JOURNEYS_ID)));
                 sejour.setName(cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_DESTINATION)));
                 sejour.setDescription(cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_DESCRIPTION)));


                 String start_date = cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_STARTDATE));
                 sejour.setFrom(calFromString(start_date));


                 String end_date = cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_ENDDATE));
                 sejour.setTo(calFromString(end_date));

                 journeys.add(sejour);
            }
            return journeys;

    }
    private Calendar calFromString(String date){
        Calendar cal = Calendar.getInstance();
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(3,5));
        int year = Integer.parseInt(date.substring(6,10));
        cal.set(year,month - 1 ,day);
        return cal;

    }

    public void edit_journey(Journey journey) {
        if (journey.get_id() < 0) {
            insertJourney(journey);
        }
        else{
            updateJourney(journey);
        }
    }

}
