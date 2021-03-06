package com.example.allwalksoflife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Profile menu icon: https://material.io/icons/#ic_person
 * Start run menu icon: https://material.io/icons/#ic_directions_run
 */

public class MainActivity extends AppCompatActivity {

    public final int START_RUN = 0;
    private CursorAdapter runsCursorAdapter;
    private RunDatabaseHelper runDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView runsListView = new ListView(this);
        runDatabaseHelper = new RunDatabaseHelper(this);

        /*
        Run testRun = new Run();
        testRun.setName("My Test Run");
        testRun.setActivityType("Run");
        testRun.setTotalTime(3672);
        testRun.setTotalDistance(30.2f);
        List<LatLng> coordinates = new ArrayList<>();
        coordinates.add(new LatLng(49.45672, -21.443241));
        coordinates.add(new LatLng(49.45671, -21.443211));
        coordinates.add(new LatLng(49.45673, -21.443221));
        coordinates.add(new LatLng(49.45677, -21.443231));
        testRun.setRouteLatLng(coordinates);
        runDatabaseHelper.addRun(testRun);*/

        runsCursorAdapter = new RunsCursorAdapter(this, runDatabaseHelper.getRuns());

        runsListView.setAdapter(runsCursorAdapter);
        runsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = ((TextView)view.findViewById(R.id.runTitle)).getText().toString();
                String type = ((TextView)view.findViewById(R.id.activityType)).getText().toString();
                ArrayList<LatLng> ghostRoute = runDatabaseHelper.getSingleRunRoute(title);
                Intent ghostRun = new Intent(MainActivity.this, CurrentRunActivity.class);
                ghostRun.putParcelableArrayListExtra(CurrentRunActivity.GHOST_KEY, ghostRoute);
                ghostRun.putExtra("activityType", type);
                startActivity(ghostRun);
            }
        });
        setContentView(runsListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runsCursorAdapter.changeCursor(runDatabaseHelper.getRuns());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.runMenuItem) {
            Intent runIntent = new Intent(this, WelcomeActivity.class);
            startActivity(runIntent);
            return true;
        } else if (item.getItemId() == R.id.profileMenuItem) {
            Intent profileIntent = new Intent(this, UserInfoActivity.class);
            startActivity(profileIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
