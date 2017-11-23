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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity for viewing details of habit event - Not yet implemented
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
 */

public class HabitEventDetailsActivity extends AppCompatActivity {
    private User loggedInUser;
    private int position;

    private HabitType habit;
    private HabitEvent habitEvent;
    private TextView habitEventInfo;
    private TextView commentView;
    private EditText commentText;
    private TextView insertImage;
    private ImageView imageView;
    private Button uploadPic;
    private Button removePic;
    private Button uploadLocation;
    private Button removeLocation;
    private TextView eventLocationView;
    private boolean loactionUploaded;
    private boolean imageUploaded;
    private Button cancel;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);
        Intent receiver = getIntent();
        this.loggedInUser = receiver.getParcelableExtra("passedUser");
        this.position = receiver.getExtras().getInt("position");
    }
}
