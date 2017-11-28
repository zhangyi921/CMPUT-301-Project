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

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HabitTypeDetailsActivity extends AppCompatActivity {
    private HabitType habit;
    private User loggedInUser;
    private EditText titleE;
    private EditText reasonE;
    private EditText startDateE;
    private ArrayList<CheckBox> weekdays = new ArrayList<>();
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_details);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        String h = receiver.getExtras().getString("passedHabitType");
        this.loggedInUser = gson.fromJson(u, User.class);
        this.habit = gson.fromJson(h, HabitType.class);
        loadData();
    }

    public void loadData(){
        titleE = (EditText) findViewById(R.id.HTD_TitleE);
        reasonE = (EditText) findViewById(R.id.HTD_ReasonE);
        startDateE = (EditText) findViewById(R.id.HTD_StartDateE);
        weekdays = new ArrayList<>();
        weekdays.add((CheckBox) findViewById(R.id.HTD_M));
        weekdays.add((CheckBox) findViewById(R.id.HTD_T));
        weekdays.add((CheckBox) findViewById(R.id.HTD_W));
        weekdays.add((CheckBox) findViewById(R.id.HTD_Tr));
        weekdays.add((CheckBox) findViewById(R.id.HTD_F));
        weekdays.add((CheckBox) findViewById(R.id.HTD_Sa));
        weekdays.add((CheckBox) findViewById(R.id.HTD_Su));
        ArrayList<Boolean> checked = habit.getWeekdays();
        for (int i = 0; i < weekdays.size(); i++){
            if (checked.get(i)){
                weekdays.get(i).toggle();
            }
        }
        titleE.setText(habit.getTitle());
        reasonE.setText(habit.getReason());
        startDateE.setText(df.format(habit.getStartDate()));
    }

    public void HTDback(View view){
        finish();
    }

    public void HTDUpdate(View view){
        String title = titleE.getText().toString().trim().replaceAll("\\s+", " ");
        String reason = reasonE.getText().toString();
        String startdate = startDateE.getText().toString();
        Date startdateD;
        ArrayList<Boolean> checked = new ArrayList<>();
        for (CheckBox ck: weekdays){
            if (ck.isChecked()) checked.add(true);
            else checked.add(false);
        }

        if (title.isEmpty() || reason.isEmpty() || startdate.isEmpty()){
            Toast.makeText(this, "Some fields are not filled out!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checked.contains(true)){
            Toast.makeText(this, "At least one day has to be checked", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            startdateD = formatter.parse(startdate);
            Date today = new Date();
            if (startdateD.before(today)){
                Toast.makeText(this, "Start date cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (ParseException e){
            Toast.makeText(this, "Incorrect Date formatting", Toast.LENGTH_SHORT).show();
            return;
        }
        //habit title can not be more than 20 char
        if (title.length() > 20){
            Toast.makeText(this, "Habit Title can't be longer than 20 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        //habit reason can not be more than 30 char
        if (reason.length() > 20){
            Toast.makeText(this, "Habit Reason can't be longer than 30 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        ElasticSearch.deleteHabitType dht = new ElasticSearch.deleteHabitType();
        dht.execute(loggedInUser.getUsername(), habit.getTitle());
        try{
            boolean success = dht.get();
            if (!success) {
                Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch(Exception e){
            Log.e("Error", "Failed to delete");
            e.printStackTrace();
            return;
        }
        HabitType newHabit = new HabitType(loggedInUser, title, reason, startdateD, checked);
        newHabit.setEvents(habit.getEvents());
        ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
        aht.execute(newHabit);
        try{
            boolean success = aht.get();
            if (!success){
                Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
            }else{
                habit = newHabit;
                Toast.makeText(this, "Updated Habit Type!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
        }
    }

    public void HTDDelete(View view){
        ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
        delHT.execute(loggedInUser.getUsername(), habit.getTitle());
        try{
            boolean result = delHT.get();
            if (result){
                Toast.makeText(this, "deleted item!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    public void HTDAddEvent(View view){
        Intent createHabitEvent = new Intent(HabitTypeDetailsActivity.this, CreateHabitEventActivity.class);
        createHabitEvent.putExtra("passedUser", gson.toJson(loggedInUser));
        createHabitEvent.putExtra("passedHabitType", gson.toJson(habit));
        finish();
        startActivity(createHabitEvent);
    }


}
