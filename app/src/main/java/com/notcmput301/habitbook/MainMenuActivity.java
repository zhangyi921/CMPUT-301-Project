/*
 * MainMenuActivity
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Activity for using the main menu to access app functions
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */


public class MainMenuActivity extends AppCompatActivity {
    private User loggedInUser;
    private TextView mainMenu;
    private Button habitTypes;
    private Button todaysHabits;
    private Button habitEventHistory;
    private Button onlineFunctions;
    private Button logout;
    private String target;
    private Gson gson;
    private LocalFileController localFileController;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //Intent receiver = getIntent();
        Bundle bundle = getIntent().getExtras();
        String target = bundle.getString("passedUser");
        Gson gson = new Gson();
        this.loggedInUser = gson.fromJson(target, User.class);
        this.gson = new Gson();
        //this.localFileController = new LocalFileController();
    }
    /**
     * Logs a user out of the app
     *
     * @param view view of current activity status
     */
    public void logoutUser(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    /**
     * Changes activity to view list of habits
     *
     * @param view view of current activity status
     */
    public void viewHabitTypeList(View view) {
        Intent intent = new Intent(this, HabitTypeListActivity.class);
        target = gson.toJson(loggedInUser);
        intent.putExtra("user", target);
        //target = gson.toJson(localFileController);
        //intent.putExtra("localfilecontroller", target);
        startActivity(intent);
    }
}

