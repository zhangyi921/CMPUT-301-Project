package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

public class CreateHabitActivity extends AppCompatActivity {
    private User loggedInUser;
    private ArrayList<Boolean> weekdays = new ArrayList<>();
    private Date startdate;
    private String title;
    private String reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        Intent receiver = getIntent();
        loggedInUser = receiver.getParcelableExtra("passedUser");
    }

    public void CHTCreate(View view){

        EditText titleE = (EditText) findViewById(R.id.CHT_Title);
        EditText reasonE = (EditText) findViewById(R.id.CHT_Reason);
        EditText startdateE = (EditText) findViewById(R.id.CHT_Startdate);
        CheckBox mE = (CheckBox) findViewById(R.id.CHT_M);
        CheckBox tE = (CheckBox) findViewById(R.id.CHT_T);
        CheckBox wE = (CheckBox) findViewById(R.id.CHT_W);
        CheckBox trE = (CheckBox) findViewById(R.id.CHT_Tr);
        CheckBox fE = (CheckBox) findViewById(R.id.CHT_F);
        CheckBox saE = (CheckBox) findViewById(R.id.CHT_Sa);
        CheckBox suE = (CheckBox) findViewById(R.id.CHT_Su);

        title = titleE.getText().toString().trim().replaceAll("\\s+", " ");
        reason = reasonE.getText().toString();
        String startdate = startdateE.getText().toString();

        weekdays.add(mE.isChecked()); weekdays.add(tE.isChecked()); weekdays.add(wE.isChecked());
        weekdays.add(trE.isChecked()); weekdays.add(fE.isChecked()); weekdays.add(saE.isChecked());
        weekdays.add(suE.isChecked());

        if (title.isEmpty() || reason.isEmpty() || startdate.isEmpty()){
            Toast.makeText(this, "Some fields are not filled out!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!weekdays.contains(true)){
            Toast.makeText(this, "At least one day has to be checked", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.startdate = formatter.parse(startdate);
            Date today = new Date();
            if (this.startdate.before(today)){
                Toast.makeText(this, "Start date cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (ParseException e){
            Toast.makeText(this, "Incorrect Date formatting", Toast.LENGTH_SHORT).show();
            return;
        }

        ElasticSearch.habitTypeExists hte = new ElasticSearch.habitTypeExists();
        hte.execute(loggedInUser.getUsername(), title);
        try{
            int success = hte.get();
            if (success < 0){
                Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return;
            }if (success > 0){
                Toast.makeText(this, "Habit Type Title has to be unique!", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
        }


        HabitType newHabit = new HabitType(loggedInUser, title, reason, this.startdate, this.weekdays);
        ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
        aht.execute(newHabit);
        try{
            boolean success = aht.get();
            if (!success){
                Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Added Habit Type!", Toast.LENGTH_SHORT).show();
                Intent habittypelist = new Intent(CreateHabitActivity.this, HabitTypeListActivity.class);
                habittypelist.putExtra("passedUser", loggedInUser);
                startActivity(habittypelist);
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
        }
    }

    public void CHTBack(View view){
        Intent habittypelist = new Intent(CreateHabitActivity.this, HabitTypeListActivity.class);
        habittypelist.putExtra("passedUser", loggedInUser);
        startActivity(habittypelist);
    }
}
