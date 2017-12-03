package com.example.allwalksoflife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RunDatabaseHelper extends SQLiteOpenHelper {
    static final String TAG = "RunDatabaseHelper";
    private static final String DATABASE_NAME = "runDatabase";
    private static final int DATABASE_VERSION = 1;
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
                                LONGEST_RUN = "longestRun";     // String
    // Runs table columns
    private static final String NAME = "name",                  // String
                                TIME = "time",                  // int
                                DISTANCE = "distance",          // double
                                AVG_SPEED = "avdSpeed";         // double

    // Run table columns
    private static final String LATITUDE = "latitude",          // double
                                LONGITUDE = "longitude";        // double

    public RunDatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + USER_TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                AGE + " INTEGER, " +
                LOCATION + " TEXT, " +
                TYPE + " TEXT, " +
                FASTEST_RUN + " TEXT, " +
                FURTHEST_RUN + " TEXT, " +
                LONGEST_RUN + " TEXT)";
        String createRunsTable = "CREATE TABLE " + RUNS_TABLE + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                TIME + " INTEGER, " +
                DISTANCE + " FLOAT, " +
                AVG_SPEED + " FLOAT)";

        Log.d(TAG, "onCreate: " + createUserTable);
        Log.d(TAG, "onCreate: " + createRunsTable);
        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createRunsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //TODO
    public Cursor getRuns(){
        Cursor c = null;
        return c;
    }

    /**
     * Helper method to get the user info stored in the sqlite database.
     * Only one user should ever be in the database
     * @return
     */
    public User getUser() {
        String getUserQuery = "SELECT * FROM " + USER_TABLE + " LIMIT 1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor userCursor = db.rawQuery(getUserQuery, null);
        if (userCursor.getCount() == 0) {
            addUser();
            userCursor = db.rawQuery(getUserQuery, null);
        }
        userCursor.moveToFirst();
        String username = userCursor.getString(1);
        int age = userCursor.getInt(2);
        String location = userCursor.getString(3);
        String type = userCursor.getString(4);
        String fastestRun = userCursor.getString(5);
        String furthestRun = userCursor.getString(6);
        String longestRun = userCursor.getString(7);
        userCursor.close();
        return new User(username,
                        location,
                        type,
                        fastestRun,
                        furthestRun,
                        longestRun,
                        age);
    }

    //TODO
    public Cursor getSingleRun(String runName) {
        Cursor c = null;
        return c;
    }

    /**
     * Updates the user in the database
     * @param u The new user information
     */
    public void updateUser(User u) {
        String updateString = "UPDATE " + USER_TABLE + " SET " +
                            USERNAME + " = '" + u.getUsername() + "', " +
                            AGE + " = " + u.getAge() + ", " +
                            LOCATION + " = '" + u.getLocation() + "', " +
                            TYPE + " = '" + u.getType() + "', " +
                            FASTEST_RUN + " = '" + u.getFastestRun() + "', " +
                            FURTHEST_RUN + " = '" + u.getFurthestRun() + "', " +
                            LONGEST_RUN + " = '" + u.getLongestRun() + "' " +
                            "WHERE " + _ID + " = 1";
        Log.d(TAG, "updateUser: " + updateString);
        getWritableDatabase().execSQL(updateString);
    }

    //TODO
    public void addRun(Run run) {

    }

    //TODO
    public void deleteRun(Run run) {

    }


    /**
     * Creates the default user using the default constructor
     * Should only be called when the database is created
     */
    private void addUser() {
        User u = new User();
        String insertString = "INSERT INTO " + USER_TABLE + " VALUES(null, '" +
                u.getUsername() + "', " +
                u.getAge() + ", '" +
                u.getLocation() + "', '" +
                u.getType() + "', '" +
                u.getFastestRun() + "', '" +
                u.getFurthestRun() + "', '" +
                u.getLongestRun() + "')";
        Log.d(TAG, "addUser: " + insertString);
        getWritableDatabase().execSQL(insertString);
    }
}
