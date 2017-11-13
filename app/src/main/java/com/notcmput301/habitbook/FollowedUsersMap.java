/*
 * FollowedUsersMap
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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Class for map of followed user habits - Not yet implemented
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */
public class FollowedUsersMap extends AppCompatActivity {
    private User loggedInUser;
    private ArrayList<HabitEvent> FollowerHabits;
    private TextView habitMapTitle;
    private ImageView map; //ImageView only temp class,
    //likely use diff class in final vers
    private ToggleButton highlightHabits;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followed_users_map);
    }
}
