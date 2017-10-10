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

import bchsdr.model.Journey;

/**
 * Created by Rudy_DEAL on 10/10/2017.
 */

public class JourneysSQLiteHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "journeys.sqlite";
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

    public long insertJourney(Journey journey) {
        ContentValues cv = new ContentValues();
        cv.put(COL_JOURNEYS_ID, journey.get_id());
        cv.put(COL_JOURNEYS_DESTINATION, journey.getName());
        cv.put(COL_JOURNEYS_STARTDATE, journey.getFrom().toString());
        cv.put(COL_JOURNEYS_ENDDATE, journey.getTo().toString());
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
        cv.put(COL_JOURNEYS_STARTDATE, journey.getFrom().toString());
        cv.put(COL_JOURNEYS_ENDDATE, journey.getTo().toString());
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
                 DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                 Date dateFrom = sourceFormat.parse(start_date);
                 Calendar from = Calendar.getInstance();
                 from.set(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDate());
                 sejour.setFrom(from);


                 String end_date = cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_ENDDATE));
                 Date dateTo = sourceFormat.parse(end_date);
                 Calendar to = Calendar.getInstance();
                 to.set(dateTo.getYear(),dateTo.getMonth(),dateTo.getDate());
                 sejour.setFrom(to);

                 journeys.add(sejour);
            }
            return journeys;

    }

}
