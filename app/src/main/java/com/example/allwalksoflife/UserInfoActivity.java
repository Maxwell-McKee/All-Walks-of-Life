package com.example.allwalksoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class UserInfoActivity extends AppCompatActivity {
    private RunDatabaseHelper databaseHelper;
    private ArrayAdapter<CharSequence> activityArrayAdapter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseHelper = new RunDatabaseHelper(this);
        currentUser = databaseHelper.getUser();
        ((EditText)findViewById(R.id.usernameEditText)).setText(currentUser.getUsername());
        EditText ageText = findViewById(R.id.ageEditText);
        if (currentUser.getAge() <= 0) {
            ageText.setText("");
        } else {
            ageText.setText("" + currentUser.getAge());
        }
        Spinner activitySpinner = findViewById(R.id.activitySpinner);
        activityArrayAdapter = ArrayAdapter.createFromResource(this, R.array.activity_types, R.layout.custom_spinner_item);
        activitySpinner.setAdapter(activityArrayAdapter);
        activitySpinner.setSelection(activityArrayAdapter.getPosition(currentUser.getType()));
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
            currentUser.setAge(Integer.parseInt(ageText));
            currentUser.setType(((Spinner)findViewById(R.id.activitySpinner)).getSelectedItem().toString());
            databaseHelper.updateUser(currentUser);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
