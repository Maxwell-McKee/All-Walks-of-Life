package com.example.allwalksoflife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shvow on 12/3/2017.
 */

public class ResultActivity  extends AppCompatActivity {
    //fields
    private Run finishedRun;

    ImageView activityTypeImageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent  = getIntent();
        if(intent == null) finish(); // Should not enter this activity if it was not started
        //get extra information out
        finishedRun = intent.getParcelableExtra(CurrentRunActivity.RUN_KEY);
        int time = finishedRun.getTotalTime();
        String timeAmount = String.format("%d:%02d:%02d", time/3600, (time/60)%60, time%60);
        String distanceAmount = String.format("%.2f %s", finishedRun.getTotalDistance(), getString(R.string.distance_measure));
        String paceAmount = String.format("%.2f %s", finishedRun.getAveragePace(), getString(R.string.speed_measure));
        ((TextView)findViewById(R.id.totalTimeAmount)).setText(timeAmount);
        ((TextView)findViewById(R.id.totalDistanceAmount)).setText(distanceAmount);
        ((TextView)findViewById(R.id.totalPaceAmount)).setText(paceAmount);

        //display toast showing the username and pin
        Toast.makeText(this, "activityType: " + finishedRun.getActivityType(), Toast.LENGTH_SHORT).show();
        activityTypeImageView = findViewById(R.id.activityTypeImageView);

        switch (finishedRun.getActivityType()) {
            case "Run":
                //set the image
                activityTypeImageView.setImageResource(R.drawable.runner_blue);
                break;
            case "Walk":
                //set the image
                activityTypeImageView.setImageResource(R.drawable.walker_purple);
                break;
            case "Bike":
                //set the image
                activityTypeImageView.setImageResource(R.drawable.biker_green);
                break;
            default:
                // Invalid case. Abort
                finish();
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get the menuinflater to inflate our menu
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.result_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //called when user clicks on one of out actions
        //add, preferences, about

        //first, figure out which item was clicked
        int menuId = item.getItemId();

        switch(menuId){
            case R.id.result_save:
                //check to see if the user entered a name for the activity
                EditText editText = findViewById(R.id.activityNameEditText);
                String text = editText.getText().toString();
                if(!text.equals("")){
                    saveRun(text);
                    finish();
                }

            case R.id.result_discard:
                //ask users if they want to leave
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);

                alertDialog.setTitle("My First Alert Dialog")
                        .setMessage("Would you like to discard this Activity?")
                        .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                //go back to mainActivity
                                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Negative", null);
                alertDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRun(String runName) {
        RunDatabaseHelper runDatabaseHelper = new RunDatabaseHelper(this);
        finishedRun.setName(runName);
        runDatabaseHelper.addRun(finishedRun);
        runDatabaseHelper.close();
    }


}