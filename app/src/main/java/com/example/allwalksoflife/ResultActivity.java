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
import android.widget.Toast;

/**
 * Created by shvow on 12/3/2017.
 */

public class ResultActivity  extends AppCompatActivity {
    //fields
    String activityType;
    int secondsElapsed;

    ImageView activityTypeImageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent  = getIntent();
        if(intent != null){
            //get extra information out
            activityType = intent.getStringExtra("activityType");
            secondsElapsed = intent.getIntExtra("secondsElapsed",0);

            //display toast showing the username and pin
            Toast.makeText(this, "activityType: " + activityType, Toast.LENGTH_SHORT).show();
        }
        activityTypeImageView = findViewById(R.id.activityTypeImageView);

        if(activityType.equals("run")){
            //set the image
            activityTypeImageView.setImageResource(R.drawable.runner_blue);
        }
        else if(activityType.equals("walk")){
            //set the image
            activityTypeImageView.setImageResource(R.drawable.walker_purple);
        }
        else{
            //set the image
            activityTypeImageView.setImageResource(R.drawable.biker_green);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get the menuinflater to inflate our menu
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.main_menu, menu);

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
                    //save item here
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


}