package com.example.allwalksoflife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by shvow on 12/3/2017.
 */


public class WelcomeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startClicked(View view){
        //check to see if at lease one radio button has been clicked, if not toast
        //if so, go to next activity
        boolean isReadyForNextActivity = true;
        boolean isClicked = isOneSelected();
        if(isClicked ){
            Intent intent = new Intent(WelcomeActivity.this, CurrentRunActivity.class);

            RadioButton walkButton = findViewById(R.id.walkRadioButton);
            RadioButton runButton = findViewById(R.id.runRadioButton);
            RadioButton bikeButton = findViewById(R.id.bikeRadioButton);

            //if walk selected, send through string of run
            if(walkButton.isChecked()){
                intent.putExtra("activityType", "Walk");

            }
            //if run selected, send through string of run
            else if(runButton.isChecked()){
                intent.putExtra("activityType", "Run");
            }

            else if(bikeButton.isChecked()) {
                intent.putExtra("activityType", "Bike");
            }
            //if bike selected, send through string of run
            else{
                intent.putExtra("activityType", "Run");
            }

            //check to see if a time goal was entered.
            //convert minutes to seconds
            EditText editTextM = findViewById(R.id.timeGoalMinutesAmount);
            String minText = editTextM.getText().toString();

            EditText editTextS = findViewById(R.id.timeGoalSecondsAmount);
            String secText = editTextS.getText().toString();
            if(!minText.equals("") && !secText.equals("")){
                //convert minutes to seconds
                //add seconds and minutes
                double minAmount = Double.parseDouble(minText);
                double secAmount = Double.parseDouble(secText);
                double totalTimeGoal = minAmount*60 + secAmount;
                intent.putExtra("timeGoal",totalTimeGoal);
            }
            else if(!minText.equals("") || !secText.equals("")){
                //only one was entered
                Toast.makeText(this, "Please enter time goals for both minutes and seconds", Toast.LENGTH_SHORT).show();
                isReadyForNextActivity = false;
            }
            else{
                //no time values entered.
                isReadyForNextActivity = true;
            }

            EditText editTextDistance = findViewById(R.id.distanceGoalAmount);
            String distanceText = editTextDistance.getText().toString();
            if(!distanceText.equals("")){
                //try:
                //convert minutes to seconds
                //add seconds and minutes
                double distanceGoalAmount = Double.parseDouble(minText);

                intent.putExtra("distanceGoal",distanceGoalAmount);
            }

            if(isReadyForNextActivity) {
                startActivity(intent);
                finish();
            }
        }else{
            Toast.makeText(this, "Please select an activity.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOneSelected(){
        RadioButton walkButton = findViewById(R.id.walkRadioButton);
        RadioButton runButton = findViewById(R.id.runRadioButton);
        RadioButton bikeButton = findViewById(R.id.bikeRadioButton);
        return (walkButton.isChecked() == true) || (runButton.isChecked() == true)
                || (bikeButton.isChecked() == true);
    }

}
