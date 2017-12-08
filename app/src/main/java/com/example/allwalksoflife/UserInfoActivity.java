package com.example.allwalksoflife;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class UserInfoActivity extends AppCompatActivity {
    private RunDatabaseHelper runDatabaseHelper;
    private ArrayAdapter<CharSequence> activityArrayAdapter;
    private User currentUser;
    private ListView recordsListView;
    private final String TAG = "UserInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        runDatabaseHelper = new RunDatabaseHelper(this);
        currentUser = runDatabaseHelper.getUser();
        ((EditText)findViewById(R.id.usernameEditText)).setText(currentUser.getUsername());
        EditText ageText = findViewById(R.id.ageEditText);
        if (currentUser.getAge() <= 0) {
            ageText.setText("");
        } else {
            ageText.setText("" + currentUser.getAge());
        }
        final ImageView profilePic = findViewById(R.id.userPic);
        switch (currentUser.getType()) {
            case "Runner":
                profilePic.setImageResource(R.drawable.runner_blue);
                break;
            case "Biker":
                profilePic.setImageResource(R.drawable.biker_green);
                break;
            case "Walker":
            default:
                profilePic.setImageResource(R.drawable.walker_purple);
        }
        Spinner activitySpinner = findViewById(R.id.activitySpinner);
        activityArrayAdapter = ArrayAdapter.createFromResource(this, R.array.activity_types, R.layout.custom_spinner_item);
        activitySpinner.setAdapter(activityArrayAdapter);
        activitySpinner.setSelection(activityArrayAdapter.getPosition(currentUser.getType()));
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        profilePic.setImageResource(R.drawable.runner_blue);
                        break;
                    case 2:
                        profilePic.setImageResource(R.drawable.biker_green);
                        break;
                    default:
                    case 1:
                        profilePic.setImageResource(R.drawable.walker_purple);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ((EditText)findViewById(R.id.locationEditText)).setText(currentUser.getLocation());

        recordsListView = findViewById(R.id.recordsListView);
        CursorAdapter recordsCursorAdapter = new RunsCursorAdapter(this, runDatabaseHelper.getUserRecords());
        recordsListView.setAdapter(recordsCursorAdapter);
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = ((TextView)view.findViewById(R.id.runTitle)).getText().toString();
                String type = ((TextView)view.findViewById(R.id.activityType)).getText().toString();
                ArrayList<LatLng> ghostRoute = runDatabaseHelper.getSingleRunRoute(title);
                Intent ghostRun = new Intent(UserInfoActivity.this, CurrentRunActivity.class);
                ghostRun.putParcelableArrayListExtra(CurrentRunActivity.GHOST_KEY, ghostRoute);
                ghostRun.putExtra("activityType", type);
                startActivity(ghostRun);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_user) {
            currentUser.setUsername(((EditText)findViewById(R.id.usernameEditText)).getText().toString());
            String ageText = ((EditText)findViewById(R.id.ageEditText)).getText().toString();
            try {
                currentUser.setAge(Integer.parseInt(ageText));
            } catch (NumberFormatException e) {
                currentUser.setAge(-1);
            }
            currentUser.setType(((Spinner)findViewById(R.id.activitySpinner)).getSelectedItem().toString());
            String locationText = ((EditText)findViewById(R.id.locationEditText)).getText().toString();
            currentUser.setLocation(locationText);
            runDatabaseHelper.updateUser(currentUser);
            finish();
        }
        if(item.getItemId() == R.id.view_badges){
            Intent intent = new Intent(UserInfoActivity.this, BadgesActivity.class);
            Cursor userRecords = runDatabaseHelper.getUserRecords();
            float pace = 0.0f;
            float distance = 0.0f;
            int time = 0;
            if (userRecords.getCount() != 0) {
                userRecords.moveToFirst();
                pace = userRecords.getFloat(4);
                userRecords.moveToNext();
                distance = userRecords.getFloat(3);
                userRecords.moveToNext();
                time = userRecords.getInt(2);
            }
            userRecords.close();

            Log.d(TAG, "view_badges :" + pace + " " + distance + " " + time);

            intent.putExtra("maxPace", pace);
            intent.putExtra("maxDistance", distance);
            intent.putExtra("maxSeconds", time);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
