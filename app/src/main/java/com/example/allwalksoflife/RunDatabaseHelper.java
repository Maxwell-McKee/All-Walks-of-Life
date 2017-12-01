package com.example.allwalksoflife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RunDatabaseHelper extends SQLiteOpenHelper {
    static final String TAG = "RunDatabaseHelper";
    private static final String DATABASE_NAME = "runDatabase";
    private static final String USER_TABLE = "userTable";
    private static final String RUNS_TABLE = "runsTable";
    private static final String _ID = "_id";
    // User table columns
    private static final String USERNAME = "username",          // String
                                AGE = "age",                    // int
                                LOCATION = "location",          // String
                                TYPE = "type",                  // String
                                FASTEST_RUN = "fastestRun",     // String
                                FURTHEST_RUN = "furthestRun",   // String
                                LONGEST_RUN = "longestRun",     // String
                                IMAGE_ID = "imageID";           // int
    // Runs table columns
    private static final String NAME = "name",                  // String
                                TIME = "time",                  // int
                                DISTANCE = "distance",          // int
                                AVG_SPEED = "avdSpeed";         // int

    // Run table columns
    private static final String LATITUDE = "latitude",          // double
                                LONGITUDE = "longitude";        // double

    public RunDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //TODO
    public Cursor getRuns(){
        Cursor c = null;
        return c;
    }

    //TODO
    public Cursor getUser() {
        Cursor c = null;
        return c;
    }

    //TODO
    public Cursor getSingleRun(String runName) {
        Cursor c = null;
        return c;
    }

    //TODO
    public void updateUser(User u) {

    }

    //TODO
    public void addRun(Run run) {

    }

    //TODO
    public void deleteRun(Run run) {

    }
}
