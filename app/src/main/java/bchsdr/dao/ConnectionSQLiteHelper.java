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
import java.util.List;

import bchsdr.model.Journey;
import bchsdr.model.Note;

/**
 * Created by Rudy_DEAL on 12/10/2017.
 */

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {

    //Initialisation de la base
    private static ConnectionSQLiteHelper ConnectionSQLiteHelper;
    private static final String DB_NAME = "TP_android2.sqlite";
    private static final int VERSION = 1;

    //Fonction et procédure de la base
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //à surcharger
    };
    public ConnectionSQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    protected static ConnectionSQLiteHelper getInstance(Context context){
        if (ConnectionSQLiteHelper == null){
            ConnectionSQLiteHelper =new ConnectionSQLiteHelper(context.getApplicationContext());
        }
        return ConnectionSQLiteHelper;
    }


    //Initialisation de la Table JOURNEYS
    private static final String TABLE_JOURNEYS = "journeys";
    private static final String COL_JOURNEYS_ID = "_id";
    private static final String COL_JOURNEYS_DESTINATION = "destination";
    private static final String COL_JOURNEYS_STARTDATE   = "start_date";
    private static final String COL_JOURNEYS_ENDDATE = "end_date";

    //Initialisation de la Table NOTES
    private static final String TABLE_NOTES = "notes";
    private static final String COL_NOTES_ID = "id_notes";
    private static final String COL_NOTES_IDJOURNEY = "id_journey";
    private static final String COL_NOTES_TITLE = "title";
    private static final String COL_NOTES_DESCRIPTION = "description";
    private static final String COL_NOTES_PICTURE   = "picture_location";
    private static final String COL_NOTES_LATITUDE = "latitude";
    private static final String COL_NOTES_LONGITUDE = "longitude";


    //Creation de la Base
    public void onCreate(SQLiteDatabase db){
        //creation de la table Journeys
        db.execSQL("create table journeys (_id INTEGER PRIMARY KEY AUTOINCREMENT, destination TEXT NOT NULL, start_date TEXT NOT NULL, end_date TEXT NOT NULL)");
        //creation de la table notes
        db.execSQL("create table notes (id_notes INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, id_journey TEXT NOT NULL, description TEXT NOT NULL, picture_location TEXT NOT NULL, latitude TEXT, longitude TEXT)");

    };


    //Fonction pour la partie Journeys
    protected long insertJourney(Journey journey) {
        ContentValues cv = new ContentValues();
        //cv.put(COL_JOURNEYS_ID, journey.get_id());
        cv.put(COL_JOURNEYS_DESTINATION, journey.getName());
        cv.put(COL_JOURNEYS_STARTDATE, toStringDate(journey.getFrom()));
        cv.put(COL_JOURNEYS_ENDDATE, toStringDate(journey.getTo()));

        return getWritableDatabase().insertOrThrow(TABLE_JOURNEYS, null, cv);
    }
    protected void deleteJourney(int id) {
        String idString = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_JOURNEYS_ID + " = ?";
        String[] selectionArgs = {idString};
        db.delete(TABLE_JOURNEYS, selection, selectionArgs);
        //db.close();
    }

    protected int updateJourney(Journey journey) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_JOURNEYS_ID + " = ?";

        String idString = Integer.toString(journey.getId());
        String[] selectionArgs = { idString };

        ContentValues cv = new ContentValues();
        cv.put(COL_JOURNEYS_ID, journey.getId());
        cv.put(COL_JOURNEYS_DESTINATION, journey.getName());
        cv.put(COL_JOURNEYS_STARTDATE, toStringDate(journey.getFrom()));
        cv.put(COL_JOURNEYS_ENDDATE, toStringDate(journey.getTo()));

        int i = db.update(TABLE_JOURNEYS, cv, selection, selectionArgs);
        //db.close();

        return i;
    }



    protected Cursor queryJourneys() {
        // equivalent to "select * from table_journeys order by id asc"
        Cursor cursor = getReadableDatabase().query(TABLE_JOURNEYS,
                null, null, null, null, null, COL_JOURNEYS_ID + " asc");
        return cursor;
    }

    protected List<Journey> getDBJourneysDAO() throws ParseException {

        List<Journey> journeys = new ArrayList<>();
        Cursor cursor = queryJourneys();
        while (cursor.moveToNext())
        {
            Journey sejour =new Journey();
            sejour.setId(cursor.getInt(cursor.getColumnIndex(COL_JOURNEYS_ID)));
            sejour.setName(cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_DESTINATION)));


            String start_date = cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_STARTDATE));
            sejour.setFrom(calFromString(start_date));


            String end_date = cursor.getString(cursor.getColumnIndex(COL_JOURNEYS_ENDDATE));
            sejour.setTo(calFromString(end_date));

            journeys.add(sejour);
        }
        return journeys;

    }

    // fonction de gestion de Date CAL
    private String toStringDate(Calendar cal){
        //DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }
    protected Calendar calFromString(String date){
        Calendar cal = Calendar.getInstance();
        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(3,5));
        int year = Integer.parseInt(date.substring(6,10));
        cal.set(year,month - 1 ,day);
        return cal;

    }


    //Fonction pour la partie Notes
    protected long insertNote(Note note) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTES_TITLE, note.getTitle());
        cv.put(COL_NOTES_DESCRIPTION, note.getDescription());
        cv.put(COL_NOTES_IDJOURNEY, note.getId_journey());
        cv.put(COL_NOTES_PICTURE, note.getPicture_location());
        cv.put(COL_NOTES_LATITUDE, note.getLatitude());
        cv.put(COL_NOTES_LONGITUDE, note.getLongitude());
        //TODO Parser les floats en String

        return getWritableDatabase().insertOrThrow(TABLE_NOTES, null, cv);
    }
    protected void deleteNote(int id) {
        String idString = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_NOTES_ID + " = ?";
        String[] selectionArgs = {idString};
        db.delete(TABLE_NOTES, selection, selectionArgs);
    }

    protected int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_NOTES_ID + " = ?";

        String idString = Integer.toString(note.getId_notes());
        String[] selectionArgs = { idString };

        ContentValues cv = new ContentValues();
        cv.put(COL_NOTES_TITLE, note.getTitle());
        cv.put(COL_NOTES_DESCRIPTION, note.getDescription());
        cv.put(COL_NOTES_IDJOURNEY, note.getId_journey());
        cv.put(COL_NOTES_PICTURE, note.getPicture_location());
        cv.put(COL_NOTES_LATITUDE, note.getLatitude());
        cv.put(COL_NOTES_LONGITUDE, note.getLongitude());

        int i = db.update(TABLE_NOTES, cv, selection, selectionArgs);
        return i;
    }



    protected Cursor queryNotes(int id_journey) {
        // equivalent to "select * from table_notes order by id asc"
        Cursor cursor = getReadableDatabase().query(TABLE_NOTES,
                null, COL_NOTES_IDJOURNEY +"="+ id_journey, null, null, null, COL_NOTES_ID + " asc");
        return cursor;
    }

    protected List<Note> getDBNotesDAO(int id_journey) throws ParseException {

        List<Note> notes = new ArrayList<>();
        Cursor cursor = queryNotes(id_journey);
        while (cursor.moveToNext())
        {
            Note note =new Note();
            note.setId_notes(cursor.getInt(cursor.getColumnIndex(COL_NOTES_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(COL_NOTES_TITLE)));
            note.setDescription(cursor.getString(cursor.getColumnIndex(COL_NOTES_DESCRIPTION)));
            note.setId_journey(cursor.getInt(cursor.getColumnIndex(COL_NOTES_IDJOURNEY)));
            note.setPicture_location(cursor.getString(cursor.getColumnIndex(COL_NOTES_PICTURE)));
            note.setLatitude(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_NOTES_LATITUDE))));
            note.setLongitude(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COL_NOTES_LONGITUDE))));

            notes.add(note);
        }
        return notes;

    }
}