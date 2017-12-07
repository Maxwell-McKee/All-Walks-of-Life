package com.example.allwalksoflife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shvow on 12/4/2017.
 */
//time icons
//<div>Icons made by <a href="https://www.flaticon.com/authors/revicon" title="Revicon">Revicon</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
//roman numerals
    //<div>Icons made by <a href="https://www.flaticon.com/authors/roundicons" title="Roundicons">Roundicons</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
public class BadgesActivity extends AppCompatActivity {
    //fields
    private int totalElementsNum;

    private int longestTime;
    private float longestDistance;
    private float maxPace;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges2);

        totalElementsNum = 0;

        Intent intent  = getIntent();
        if(intent != null){
            longestTime = intent.getIntExtra("maxSeconds", 0);
            longestDistance = intent.getFloatExtra("maxDistance", 0);
            maxPace = intent.getFloatExtra("maxPace", 0);


            if(longestDistance >= 1){
                ImageView oneMile = (ImageView) findViewById(R.id.oneMileBadge);
                oneMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 2){
                ImageView twoMile = (ImageView) findViewById(R.id.twoMileBadge);
                twoMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 3){
                ImageView threeMile = (ImageView) findViewById(R.id.threeMileBadge);
                threeMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 5){
                ImageView fiveMile = (ImageView) findViewById(R.id.fiveMileBadge);
                fiveMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 7){
                ImageView sevenMile = (ImageView) findViewById(R.id.sevenMileBadge);
                sevenMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 10){
                ImageView tenMile = (ImageView) findViewById(R.id.tenMileBadge);
                tenMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 20){
                ImageView twentyMile = (ImageView) findViewById(R.id.twentyMileBadge);
                twentyMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 50){
                ImageView fiftyMile = (ImageView) findViewById(R.id.fiftyMileBadge);
                fiftyMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 70){
                ImageView seventyMile = (ImageView) findViewById(R.id.seventyMileBadge);
                seventyMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 100){
                ImageView oneHundredMile = (ImageView) findViewById(R.id.oneHundredMileBadge);
                oneHundredMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }

            //for fastest and number of activities
            //check to see if a badge should be made visible
            //setCol = totalElementNum % 3;
            //setRow = totalElementNum / 3;
            //.setVisibility(View.VISIBLE);

            if(longestTime >= 60){
                ImageView oneMin = (ImageView) findViewById(R.id.fiveMinuteBadge);
                oneMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 300){
                ImageView fiveMin = (ImageView) findViewById(R.id.fiveMinuteBadge);
                fiveMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }

            if(longestTime >= 600){
                ImageView tenMin = (ImageView) findViewById(R.id.tenMinuteBadge);
                tenMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 900){
                ImageView fifteenMin = (ImageView) findViewById(R.id.fifteenMinuteBadge);
                fifteenMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 1800){
                ImageView thirtyMin = (ImageView) findViewById(R.id.thirtyMinuteBadge);
                thirtyMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 2700){
                ImageView fortyFiveMin = (ImageView) findViewById(R.id.fortyFiveMinuteBadge);
                fortyFiveMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 3600){
                ImageView oneHour = (ImageView) findViewById(R.id.oneHourBadge);
                oneHour.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }


            //pace
            if(maxPace >= 1){
                ImageView onePace = (ImageView) findViewById(R.id.onePaceBadge);
                onePace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(maxPace >= 3){
                ImageView threePace = (ImageView) findViewById(R.id.threePaceBadge);
                threePace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }

            if(maxPace >= 5){
                ImageView fivePace = (ImageView) findViewById(R.id.fivePaceBadge);
                fivePace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(maxPace >= 10){
                ImageView tenPace = (ImageView) findViewById(R.id.tenPaceBadge);
                tenPace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get the menuinflater to inflate our menu
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.badges_menu, menu);

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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BadgesActivity.this);

                alertDialog.setTitle(getString(R.string.badges_info))
                        .setMessage(getString(R.string.badges_info_message))
                        .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                //go back to mainActivity
                                finish();
                            }
                        });
                alertDialog.show();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
