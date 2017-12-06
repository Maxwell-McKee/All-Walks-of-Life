package com.example.allwalksoflife;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class RunsCursorAdapter extends ResourceCursorAdapter {

    public RunsCursorAdapter(Context context, Cursor c) {
        super(context, R.layout.run_list_item, c, 0);
    }

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
        String title = cursor.getString(1).replace("''", "'");
        String time = String.format("%d:%02d:%02d", timeInt/3600, (timeInt/60)%60, timeInt%60);
        String distance = String.format("%.2f %s", distanceFloat, context.getString(R.string.distance_measure));
        String speed = String.format("%.2f %s", speedFloat, context.getString(R.string.speed_measure));
        String type = cursor.getString(5);

        // Set text in the views
        titleView.setText(title);
        timeView.setText(time);
        distanceView.setText(distance);
        speedView.setText(speed);
        typeView.setText(type);
    }
}

