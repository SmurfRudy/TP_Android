package bchsdr.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rudy_DEAL on 10/10/2017.
 */

public class journeysSQLiteHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "journeys.sqlite";
    private static final int VERSION = 1;

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table journeys (_id INTEGER PRIMARY KEY AUTOINCREMENT, destination TEXT NOT NULL, start_date TEXT NOT NULL, end_date TEXT NOT NULL, description TEXT)");
        // some sample data
        
    };
    public void onUpgrade(SQLiteDatabase db, int
            oldVersion, int newVersion){
        //à surcharger
    };
    public journeysSQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
}
