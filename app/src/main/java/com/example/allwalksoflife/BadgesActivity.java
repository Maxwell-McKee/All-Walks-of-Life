package com.example.allwalksoflife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
                ImageView oneMile = findViewById(R.id.oneMileBadge);
                oneMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 2){
                ImageView twoMile = findViewById(R.id.twoMileBadge);
                twoMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 3){
                ImageView threeMile = findViewById(R.id.threeMileBadge);
                threeMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 5){
                ImageView fiveMile = findViewById(R.id.fiveMileBadge);
                fiveMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 7){
                ImageView sevenMile = findViewById(R.id.sevenMileBadge);
                sevenMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 10){
                ImageView tenMile = findViewById(R.id.tenMileBadge);
                tenMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 20){
                ImageView twentyMile = findViewById(R.id.twentyMileBadge);
                twentyMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 50){
                ImageView fiftyMile = findViewById(R.id.fiftyMileBadge);
                fiftyMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 70){
                ImageView seventyMile = findViewById(R.id.seventyMileBadge);
                seventyMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestDistance >= 100){
                ImageView oneHundredMile = findViewById(R.id.oneHundredMileBadge);
                oneHundredMile.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }

            //for fastest and number of activities
            //check to see if a badge should be made visible
            //setCol = totalElementNum % 3;
            //setRow = totalElementNum / 3;
            //.setVisibility(View.VISIBLE);

            if(longestTime >= 60){
                ImageView oneMin = findViewById(R.id.oneMinuteBadge);
                oneMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 300){
                ImageView fiveMin = findViewById(R.id.fiveMinuteBadge);
                fiveMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }

            if(longestTime >= 600){
                ImageView tenMin = findViewById(R.id.tenMinuteBadge);
                tenMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 900){
                ImageView fifteenMin = findViewById(R.id.fifteenMinuteBadge);
                fifteenMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 1800){
                ImageView thirtyMin = findViewById(R.id.thirtyMinuteBadge);
                thirtyMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 2700){
                ImageView fortyFiveMin = findViewById(R.id.fortyFiveMinuteBadge);
                fortyFiveMin.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(longestTime >= 3600){
                ImageView oneHour = findViewById(R.id.oneHourBadge);
                oneHour.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }


            //pace
            if(maxPace >= 1){
                ImageView onePace = findViewById(R.id.onePaceBadge);
                onePace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(maxPace >= 3){
                ImageView threePace = findViewById(R.id.threePaceBadge);
                threePace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }

            if(maxPace >= 5){
                ImageView fivePace = findViewById(R.id.fivePaceBadge);
                fivePace.setVisibility(View.VISIBLE);
                totalElementsNum++;
            }
            if(maxPace >= 10){
                ImageView tenPace = findViewById(R.id.tenPaceBadge);
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
            case R.id.infoMenuItem:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BadgesActivity.this);

                alertDialog.setTitle(getString(R.string.badges_info))
                        .setMessage(getString(R.string.badges_info_message))
                        .setPositiveButton(getString(R.string.okay), null)
                        .show();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
