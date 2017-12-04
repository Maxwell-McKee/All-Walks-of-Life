package com.example.allwalksoflife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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

            startActivity(intent);
            Toast.makeText(this, "Go to the run activity.", Toast.LENGTH_SHORT).show();
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
