/*
 * HabitEventDetailsActivity
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Activity for displaying HabitEvent Details
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class HabitEventDetailsActivity extends AppCompatActivity {

    private User loggedInUser;
    private HabitEvent habitEvent;
    private HabitTypeSingleton HTS;
    private ArrayList<HabitType> habitTypes;
    private NetworkHandler nH;

    private EditText titleE;
    private EditText commentE;
    private Button addPicB;
    private Button backB;
    private CircleImageView imageV;
    private Gson gson = new Gson();

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);

        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        String e = receiver.getExtras().getString("passedHabitEvent");

        loggedInUser = gson.fromJson(u, User.class);
        habitEvent = gson.fromJson(e, HabitEvent.class);
        HTS=HabitTypeSingleton.getInstance();
        habitTypes=HTS.getHabitTypes();
        nH = new NetworkHandler(this);

        commentE = (EditText) findViewById(R.id.HED_Comment);
        commentE.setText(habitEvent.getComment());
        try {
            CircleImageView imageV = (CircleImageView) findViewById(R.id.HED_Image);
            imageV.setImageBitmap(habitEvent.imageToBitmap());
        }catch (Exception e1){

        }

        //Checks if Network Connection is detected.
        BroadcastReceiver br = new NetworkStateChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(br, filter);
    }

    /**
     * Updates HabitEvent with provided info
     *
     * @param view view of current activity info
     */
    public void update(View view){

        String comment = commentE.getText().toString();
        if (comment.length() > 20){
            Toast.makeText(this, "comment should be less than 20 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nH.isNetworkAvailable() && habitTypes.size() == 0){
            habitTypes = nH.getHabitList(loggedInUser.getUsername());
            HTS.setHabitTypes(habitTypes);
        }

        for(int i=0; i < habitTypes.size(); i++){

            HabitType h = habitTypes.get(i);
            ArrayList<HabitEvent> he = h.getEvents();

            for (int j =0; j < he.size(); j++){
                HabitEvent eve = he.get(j);

                if (eve.getComment().equals(habitEvent.getComment())){

                    if (nH.isNetworkAvailable()){
                        nH.deleteHabitType(h);
                        h.removeHabitEvent(eve);
                        habitEvent.setComment(comment);
                        h.addHabitEvent(habitEvent);
                        nH.addHabitType(h);
                    }else{
                        nH.putString("d", gson.toJson(h));
                        h.removeHabitEvent(eve);
                        habitEvent.setComment(comment);
                        h.addHabitEvent(habitEvent);
                        nH.putString("au", gson.toJson(h));
                    }
                    habitTypes.set(i, h);
                    HTS.setHabitTypes(habitTypes);
                    break;
                }
            }
        }
        back();
    }

    /**
     * Deletes a HabitEvent.
     *
     * @param view view of current activity status
     */
    public void delete(View view){

        if (nH.isNetworkAvailable() && habitTypes.size() == 0){
            habitTypes=nH.getHabitList(loggedInUser.getUsername());
            HTS.setHabitTypes(habitTypes);
        }

        for(int i=0; i < habitTypes.size(); i++){

            HabitType h = habitTypes.get(i);
            ArrayList<HabitEvent> he = h.getEvents();

            for (int j =0; j < he.size(); j++){
                HabitEvent eve = he.get(j);

                if (eve.getComment().equals(habitEvent.getComment())){

                    if (nH.isNetworkAvailable()){
                        nH.deleteHabitType(h);
                        h.removeHabitEvent(eve);
                        nH.addHabitType(h);
                    }else{
                        nH.putString("d", gson.toJson(h));
                        h.removeHabitEvent(eve);
                        nH.putString("au", gson.toJson(h));
                    }
                    habitTypes.set(i, h);
                    HTS.setHabitTypes(habitTypes);
                    break;
                }
            }
        }
        back();
    }

    /**
     * return to previous activity
     *
     */
    public void back(){
        Intent habitEventHistory = new Intent(HabitEventDetailsActivity.this, HabitEventHistory2.class);

        habitEventHistory.putExtra("passedUser", gson.toJson(loggedInUser));

        finish();
        startActivity(habitEventHistory);
    }

    /**
     * return to previous activity
     *
     * @param view view of current activity status
     */
    public void back(View view){
        back();
    }
}
