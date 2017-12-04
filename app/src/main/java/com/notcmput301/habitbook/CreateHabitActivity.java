/*
 * CreateHabitActivity
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

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity for creating new HabitType
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitType
 * @since 1.0
 */
public class CreateHabitActivity extends AppCompatActivity {
    private User loggedInUser;
    private HabitListStore HLS;
    private ArrayList<HabitType> habitTypes;


    private ArrayList<Boolean> weekdays;
    private Date startdate = new Date();
    private String title;
    private String reason;
    private Gson gson = new Gson();
    private DatePickerDialog.OnDateSetListener dlistener;
    private NetworkHandler nH;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        Intent receiver = getIntent();

        String u = receiver.getExtras().getString("passedUser");
        String l = receiver.getExtras().getString("passedHList");

        this.loggedInUser = gson.fromJson(u, User.class);
        this.HLS = gson.fromJson(l, HabitListStore.class);
        this.habitTypes=HLS.getList();



        dlistener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    startdate = formatter.parse(year+"-"+month+"-"+dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("MONTH", month+"");
            }
        };

        //get our network handler
        nH = new NetworkHandler(this);

        //Checks if Network Connection is detected.
        BroadcastReceiver br = new NetworkStateChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(br, filter);
    }

    /**
     * Creates Habit Type with provided info
     *
     * @param view view of input info
     */
    public void CHTCreate(View view){

        EditText titleE = (EditText) findViewById(R.id.HTD_TitleE);
        EditText reasonE = (EditText) findViewById(R.id.HTD_ReasonE);
        CheckBox mE = (CheckBox) findViewById(R.id.HTD_M);
        CheckBox tE = (CheckBox) findViewById(R.id.HTD_T);
        CheckBox wE = (CheckBox) findViewById(R.id.HTD_W);
        CheckBox trE = (CheckBox) findViewById(R.id.HTD_Tr);
        CheckBox fE = (CheckBox) findViewById(R.id.HTD_F);
        CheckBox saE = (CheckBox) findViewById(R.id.HTD_Sa);
        CheckBox suE = (CheckBox) findViewById(R.id.CHT_Su);

        title = titleE.getText().toString().trim().replaceAll("\\s+", " ");
        reason = reasonE.getText().toString();

        weekdays = new ArrayList<>();
        weekdays.add(mE.isChecked()); weekdays.add(tE.isChecked()); weekdays.add(wE.isChecked());
        weekdays.add(trE.isChecked()); weekdays.add(fE.isChecked()); weekdays.add(saE.isChecked());
        weekdays.add(suE.isChecked());

        if (title.isEmpty() || reason.isEmpty() ){
            Toast.makeText(this, "Some fields are not filled out!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!weekdays.contains(true)){
            Toast.makeText(this, "At least one day has to be checked", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title.length() > 20){
            Toast.makeText(this, "Habit Title can't be longer than 20 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (reason.length() > 20){
            Toast.makeText(this, "Habit Reason can't be longer than 30 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        HabitType newHabit = new HabitType(loggedInUser.getUsername(), title, reason, this.startdate, this.weekdays);
        //check network availability
        if(nH.isNetworkAvailable()){
            if(!nH.verifyExistance(title)) {Toast.makeText(this, "title exists", Toast.LENGTH_SHORT).show(); return;};
            nH.addHabitType(newHabit);

        }else{
            if (!verifyTitleOk(newHabit.getTitle())) {Toast.makeText(this, "Title exists", Toast.LENGTH_SHORT).show(); return;}
            nH.putString("a", gson.toJson(newHabit));
        }
        habitTypes.add(newHabit);
        HLS.setList(habitTypes);
        finish();
        back();
    }

    public boolean verifyTitleOk(String title){
        for(HabitType h: habitTypes){
            if(title.equals(h.getTitle())) return false;
        }
        return true;
    };

    public void back(){
        Intent habitList = new Intent(CreateHabitActivity.this, HabitTypeList2.class);

        habitList.putExtra("passedUser", gson.toJson(loggedInUser));
        habitList.putExtra("passedHList", gson.toJson(HLS));
        startActivity(habitList);
    }

    /**
     * Displays GUI for date selection
     *
     * @param view view of activity info
     */
    public void CHTCalendar(View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,
                                                        dlistener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
