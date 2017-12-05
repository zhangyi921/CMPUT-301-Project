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

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private User loggedInUser;
    private HabitTypeSingleton HTS;
    private ArrayList<HabitType> habitTypes;
    private int position;
    private NetworkHandler nH;

    private EditText titleE;
    private EditText reasonE;
    private EditText startDateE;
    private ArrayList<CheckBox> weekdays = new ArrayList<>();
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Gson gson = new Gson();

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
        position = Integer.parseInt(receiver.getExtras().getString("passedPos"));

        loggedInUser = gson.fromJson(u, User.class);
        HTS = HabitTypeSingleton.getInstance();
        habitTypes=HTS.getHabitTypes();
        habit = habitTypes.get(position);
        nH = new NetworkHandler(this);

        //Checks if Network Connection is detected.
        BroadcastReceiver br = new NetworkStateChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(br, filter);

        loadData();
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(habit.getProgress());
    }

    /**
     * Loads Habit Type settings and info
     *
     */
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

    /**
     * Function for back button
     *
     */
    public void back(){
        Intent habitList = new Intent(HabitTypeDetailsActivity.this, HabitTypeList2.class);
        habitList.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(habitList);
    }

    /**
     * Function for back button
     *
     * @param view view of current activity status
     */
    public void HTDback(View view){
        back();
    }


    /**
     * Function for updating Habit Type
     *
     * @param view view of current activity status
     */
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

        //------------------------------------------

        HabitType newHabitType = new HabitType(loggedInUser.getUsername(), title, reason, startdateD, checked);
        for (HabitEvent event : habit.getEvents()){ //make sure habit events are updated too
            event.setHabit(title);
            newHabitType.addHabitEvent(event);
        }

        if (nH.isNetworkAvailable()){
            nH.deleteHabitType(habit);
            nH.addHabitType(newHabitType);
        }else{
            nH.putString("d", gson.toJson(habit));
            nH.putString("au", gson.toJson(newHabitType));
        }
        habitTypes.set(position, newHabitType);
        HTS.setHabitTypes(habitTypes);
        back();
    }

    /**
     * Function for deleting Habit Type
     *
     * @param view view of current activity status
     */
    public void HTDDelete(View view){

        if(nH.isNetworkAvailable()){
            nH.deleteHabitType(habit);
        }else{
            nH.putString("d", gson.toJson(habit));
        }
        habitTypes.remove(position);
        HTS.setHabitTypes(habitTypes);
        back();
    }

    /**
     * Function for pressing add event button
     *
     * @param view view of current activity status
     */
    public void HTDAddEvent(View view){
        HTS.setHabitTypes(habitTypes);
        Intent createHabitEvent = new Intent(HabitTypeDetailsActivity.this, CreateHabitEventActivity.class);
        createHabitEvent.putExtra("passedUser", gson.toJson(loggedInUser));
        createHabitEvent.putExtra("passedPos", Integer.toString(position));
        finish();
        startActivity(createHabitEvent);
    }
}
