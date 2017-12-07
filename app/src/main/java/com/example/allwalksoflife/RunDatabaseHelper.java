package com.example.allwalksoflife;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RunDatabaseHelper extends SQLiteOpenHelper {
    static final String TAG = "RunDatabaseHelper";
    private static final String DATABASE_NAME = "runDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "userTable";
    private static final String RUNS_TABLE = "runsTable";
    public static final String _ID = "_id";
    // User table columns
    public static final String USERNAME = "username",          // String
                                AGE = "age",                    // int
                                LOCATION = "location",          // String
                                TYPE = "type",                  // String
                                FASTEST_RUN = "fastestRun",     // String
                                FURTHEST_RUN = "furthestRun",   // String
                                LONGEST_RUN = "longestRun";     // String
    // Runs table columns
    public static final String NAME = "name",                  // String
                                TIME = "time",                  // int
                                DISTANCE = "distance",          // double
                                AVG_SPEED = "avgSpeed";         // double

    // Run table columns
    public static final String LATITUDE = "latitude",          // double
                                LONGITUDE = "longitude";        // double

    private boolean foundRun = false;

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
                AVG_SPEED + " FLOAT, " +
                TYPE + " TEXT)";

        Log.d(TAG, "onCreate: " + createUserTable);
        Log.d(TAG, "onCreate: " + createRunsTable);
        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createRunsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Gets all runs from the runs table
     * @return A cursor object for all runs in the database
     */
    public Cursor getRuns(){
        String getAllRuns = "SELECT * FROM " + RUNS_TABLE
                            + " ORDER BY " + _ID + " DESC";
        return getReadableDatabase().rawQuery(getAllRuns, null);
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

    public Cursor getUserRecords() {
        Cursor[] cursors = new Cursor[3];
        SQLiteDatabase db = getReadableDatabase();
        String fastestQuery = "SELECT * FROM " + RUNS_TABLE +
                                " ORDER BY " + AVG_SPEED + " DESC LIMIT 1";
        String farthestQuery = "SELECT * FROM " + RUNS_TABLE +
                                " ORDER BY " + DISTANCE + " DESC LIMIT 1";
        String longestQuery = "SELECT * FROM " + RUNS_TABLE +
                                " ORDER BY " + TIME + " DESC LIMIT 1";
        cursors[0] = db.rawQuery(fastestQuery, null);
        cursors[1] = db.rawQuery(farthestQuery, null);
        cursors[2] = db.rawQuery(longestQuery, null);
        return new MergeCursor(cursors);
    }

    /*
    public List<LatLng> getSingleRunRoute(String runName) {
        List<LatLng> route = new ArrayList<LatLng>();
        Cursor
    }*/


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

    public void addRun(Run run) {
        SQLiteDatabase db = getWritableDatabase();
        run.setName(run.getName().replace("'", "''")); // Sanitize input
        addSingleRun(run);
        if (foundRun) { // Duplicate name found, name changed on save
            StringBuilder newName = new StringBuilder(run.getName());
            newName.setCharAt(run.getName().lastIndexOf('_'), '(');
            newName.append(')');
            run.setName(newName.toString().replace('_', ' '));
            foundRun = false;
        }
        String insertString = "INSERT INTO " + RUNS_TABLE + " VALUES(null, '" +
                            run.getName() + "', " +
                            run.getTotalTime() + ", " +
                            run.getTotalDistance() + ", " +
                            run.getAveragePace() + ", '" +
                            run.getActivityType() + "')";
        db.execSQL(insertString);
    }

    private void addSingleRun(Run run) {
        SQLiteDatabase db = getWritableDatabase();
        String originalName = run.getName().replace(' ', '_');
        String safeTableQuery = "SELECT DISTINCT tbl_name FROM sqlite_master " +
                                "WHERE tbl_name = '" + originalName.replace("''", "") + "'";
        Log.d(TAG, "addSingleRun: " + safeTableQuery + db.rawQuery(safeTableQuery, null).getCount());
        int i = 1;
        while(db.rawQuery(safeTableQuery, null).getCount() != 0) { // See if table already exists
            foundRun = true;
            run.setName(originalName +  '_' + i);
            safeTableQuery = "SELECT DISTINCT tbl_name FROM sqlite_master " +
                    "WHERE tbl_name = '" + run.getName().replace(' ', '_').replace("''", "") + "'";
        }
        String runTableName = run.getName().replace(' ', '_').replace("''", "");
        String createString = "CREATE TABLE " + runTableName + "(" +
                            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            LATITUDE + " DOUBLE, " +
                            LONGITUDE + " DOUBLE)";
        Log.d(TAG, "addSingleRun: " + createString);
        db.execSQL(createString);
        List<LatLng> runList = run.getRouteLatLng();
        for (LatLng coordinates : runList) {
            String insertString = "INSERT INTO " + runTableName + " VALUES(null, " +
                    coordinates.latitude + ", " + coordinates.longitude + ")";
            Log.d(TAG, "addSingleRun: " + insertString);
            db.execSQL(insertString);
        }
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
