/*
 * HabitTypeDetailsActivity
 *
 * Version 1.0
 *
 * November 12, 2017
 *
 * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

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
<<<<<<< HEAD
import android.widget.ToggleButton;
=======
>>>>>>> yi

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Activity for viewing details of habit type
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitType
 * @since 1.0
 */

public class HabitTypeDetailsActivity extends AppCompatActivity {
    private HabitType habit;
<<<<<<< HEAD
    private EditText titleText;
    private EditText reasonText;
    private TextView startDate;
    private TextView habitSchedule;
    private ArrayList<Integer> weekdays;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;
    private TextView completionStatus;
    private Button back;
    private Button update;
    private Button delete;
    private Button addNewHabitEvent;

    private String target;
    private Gson gson;
    private User user;
    private ArrayList<HabitType> habitTypes;
    private LocalFileController localFileControler;
    private HabitType currentHabitType;
=======
    private User loggedInUser;
    private EditText titleE;
    private EditText reasonE;
    private EditText startDateE;
    private ArrayList<CheckBox> weekdays = new ArrayList<>();
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Gson gson = new Gson();
>>>>>>> yi


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
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

<<<<<<< HEAD
        Bundle bundle = getIntent().getExtras();
        int index = bundle.getInt("index");
        this.target = bundle.getString("user");
        this.gson = new Gson();
        this.user = gson.fromJson(target, User.class);
        this.habitTypes = user.getHabitTypes();
        this.currentHabitType = this.habitTypes.get(index);
        //this.target = bundle.getString("localfilecontroller");
        //this.localFileControler = gson.fromJson(target, LocalFileControler.class);
        this.weekdays = currentHabitType.getWeekdays();

        titleText = (EditText) findViewById(R.id.Title);
        titleText.setText(currentHabitType.getTitle());
        reasonText = (EditText) findViewById(R.id.Reason);
        reasonText.setText(currentHabitType.getReason());
        startDate = (TextView) findViewById(R.id.StartDate);
        startDate.setText("Starting Date: "+currentHabitType.getStartDate().toString());
        monday = (CheckBox) findViewById(R.id.Mon);
        monday.setChecked(this.weekdays.indexOf(1) != -1);
        tuesday = (CheckBox) findViewById(R.id.Tu);
        tuesday.setChecked(this.weekdays.indexOf(2) != -1);
        wednesday = (CheckBox) findViewById(R.id.Wes);
        wednesday.setChecked(this.weekdays.indexOf(3) != -1);
        thursday = (CheckBox) findViewById(R.id.Thu);
        thursday.setChecked(this.weekdays.indexOf(4) != -1);
        friday = (CheckBox) findViewById(R.id.Fri);
        friday.setChecked(this.weekdays.indexOf(5) != -1);
        saturday = (CheckBox) findViewById(R.id.Sat);
        saturday.setChecked(this.weekdays.indexOf(6) != -1);
        sunday = (CheckBox) findViewById(R.id.Sun);
        sunday.setChecked(this.weekdays.indexOf(7) != -1);
        completionStatus = (TextView) findViewById(R.id.Status);
        //completionStatus.setText(currentHabitType.get);

        back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
=======
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
>>>>>>> yi
            }
        }
        titleE.setText(habit.getTitle());
        reasonE.setText(habit.getReason());
        startDateE.setText(df.format(habit.getStartDate()));
    }

<<<<<<< HEAD
                if (monday.isChecked()){
                    if (weekdays.indexOf(1) == -1){
                        weekdays.add(1);
                    }
                }
                else{
                    weekdays.remove(new Integer(1));
                }
                if (tuesday.isChecked()){
                    if (weekdays.indexOf(2) == -1){
                        weekdays.add(2);
                    }
                }
                else{
                    weekdays.remove(new Integer(2));}
                if (wednesday.isChecked()){
                    if (weekdays.indexOf(3) == -1){
                        weekdays.add(3);
                    }
                }
                else{
                    weekdays.remove(new Integer(3));}
                if (thursday.isChecked()){
                    if (weekdays.indexOf(4) == -1){
                        weekdays.add(4);
                    }
                }
                else{
                    weekdays.remove(new Integer(4));}
                if (friday.isChecked()){
                    if (weekdays.indexOf(5) == -1){
                        weekdays.add(5);
                    }
                }
                else{
                    weekdays.remove(new Integer(5));}
                if (saturday.isChecked()){
                    if (weekdays.indexOf(6) == -1){
                        weekdays.add(6);
                    }
                }
                else{
                    weekdays.remove(new Integer(6));}
                if (sunday.isChecked()){
                    if (weekdays.indexOf(7) == -1){
                        weekdays.add(7);
                    }
                }
                else{
                    weekdays.remove(new Integer(7));}

                if (weekdays.isEmpty()){
                    Toast.makeText(HabitTypeDetailsActivity.this, "weekdays cannot be empty ", Toast.LENGTH_LONG).show();
                    return;
                }
                HabitType Newhabit = new HabitType(user.getUsername(), titleText.getText().toString(),
                        reasonText.getText().toString(),
                        currentHabitType.getStartDate(),
                        weekdays);
                Newhabit.setEvents(currentHabitType.getEvents());
                Newhabit.setEventsCompleted(currentHabitType.getEventsCompleted());
                Newhabit.setStartDate(currentHabitType.getStartDate());
                Newhabit.setCreationDate(currentHabitType.getCreationDate());
                Newhabit.setTotalEvents(currentHabitType.getTotalEvents());
                user.updateHabitType(currentHabitType, Newhabit);
                //localFileControler.Save(user);
                ElasticSearch.UpdateUserTask updateUserTask = new ElasticSearch.UpdateUserTask();
                updateUserTask.execute(user);
                Intent intent = new Intent(HabitTypeDetailsActivity.this, HabitTypeListActivity.class);
                target = gson.toJson(Newhabit);
                intent.putExtra("Newhabit", target);
                finish();
=======
    public void HTDback(View view){
        Intent habitTypeList = new Intent(HabitTypeDetailsActivity.this, HabitTypeListActivity.class);
        habitTypeList.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(habitTypeList);
    }
>>>>>>> yi

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
<<<<<<< HEAD
        }   );
        delete = (Button) findViewById(R.id.Delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.removeHabitType(currentHabitType);
                ElasticSearch.UpdateUserTask updateUserTask = new ElasticSearch.UpdateUserTask();
                updateUserTask.execute(user);
=======
        }catch(Exception e){
            Log.e("Error", "Failed to delete");
            e.printStackTrace();
            return;
        }
        HabitType newHabit = new HabitType(loggedInUser, title, reason, startdateD, checked);
        ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
        aht.execute(newHabit);
        try{
            boolean success = aht.get();
            if (!success){
                Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
            }else{
                habit = newHabit;
                Toast.makeText(this, "Updated Habit Type!", Toast.LENGTH_SHORT).show();
                Intent habittypelist = new Intent(HabitTypeDetailsActivity.this, HabitTypeList2.class);
                habittypelist.putExtra("passedUser", gson.toJson(loggedInUser));
>>>>>>> yi
                finish();
                startActivity(habittypelist);
                return;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    /**
     * Displays completion percentage of habit events
     *
     */
    public void displayCompletionStatus(HabitType Habit){
=======
    public void HTDDelete(View view){
        ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
        delHT.execute(loggedInUser.getUsername(), habit.getTitle());
        try{
            boolean result = delHT.get();
            if (result){
                Intent habitTypeList = new Intent(HabitTypeDetailsActivity.this, HabitTypeListActivity.class);
                Toast.makeText(this, "deleted item!", Toast.LENGTH_SHORT).show();
                habitTypeList.putExtra("passedUser", gson.toJson(loggedInUser));
                startActivity(habitTypeList);
                return;
            }else{
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }
>>>>>>> yi

    public void HTDAddEvent(View view){
        Intent createHabitEvent = new Intent(HabitTypeDetailsActivity.this, CreateHabitEventActivity.class);
        createHabitEvent.putExtra("passedUser", gson.toJson(loggedInUser));
        createHabitEvent.putExtra("passedHabitType", gson.toJson(habit));
        startActivity(createHabitEvent);
    }
}
