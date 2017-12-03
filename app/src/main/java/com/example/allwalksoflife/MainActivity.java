package com.example.allwalksoflife;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView runsListView = new ListView(this);
        RunDatabaseHelper runDatabaseHelper = new RunDatabaseHelper(this);

        Run testRun = new Run();
        testRun.setName("My Test Run");
        testRun.setActivityType("Run");
        testRun.setTotalTime(3672);
        testRun.setTotalDistance(30.2f);
        List<LatLng> coordinates = new ArrayList<>();
        coordinates.add(new LatLng(147.79907, -21.443241));
        coordinates.add(new LatLng(147.79910, -21.443211));
        coordinates.add(new LatLng(147.79904, -21.443221));
        coordinates.add(new LatLng(147.79903, -21.443231));
        testRun.setRouteLatLng(coordinates);
        //runDatabaseHelper.addRun(testRun);

        CursorAdapter runsCursorAdapter = new ResourceCursorAdapter(this,
                                                R.layout.run_list_item,
                                                runDatabaseHelper.getRuns(), 0){
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Get references to the views
                TextView titleView = view.findViewById(R.id.runTitle);
                TextView timeView = view.findViewById(R.id.time);
                TextView distanceView = view.findViewById(R.id.distance);
                TextView speedView = view.findViewById(R.id.speed);
                TextView typeView = view.findViewById(R.id.activityType);

                // Get numerical values for conversion
                int timeInt = cursor.getInt(2);
                float distanceFloat = cursor.getFloat(3);
                float speedFloat = cursor.getFloat(4);

                // Convert numerical values
                String title = cursor.getString(1);
                String time = String.format("%d:%02d:%02d", timeInt/3600, (timeInt/60)%60, timeInt%60);
                String distance = distanceFloat + " " + getString(R.string.distance_measure);
                String speed = String.format("%.3f %s", speedFloat, getString(R.string.speed_measure));
                String type = cursor.getString(5);

                // Set text in the views
                titleView.setText(title);
                timeView.setText(time);
                distanceView.setText(distance);
                speedView.setText(speed);
                typeView.setText(type);
            }
        };

        runsListView.setAdapter(runsCursorAdapter);
        setContentView(runsListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.runMenuItem) {
            Intent runIntent = new Intent(this, CurrentRunActivity.class);
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
