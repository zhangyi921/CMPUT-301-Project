/*
 * CreateHabitAccount
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private ArrayList<Integer> tempDays = new ArrayList<Integer>();
    private User user;
    private String target;
    private Gson gson;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        Bundle bundle = getIntent().getExtras();
        this.target = bundle.getString("user");
        this.gson = new Gson();
        this.user = gson.fromJson(target, User.class);
    }

    // Method based of similar method in this YouTube Video
    // https://www.youtube.com/watch?v=NGRV2qY9ZiU
    // Dynamically adds / removes days to tempDays when checkBox is clicked

    /**
     * Sets value of weekdays for habit types
     *
     * @param v view of input info
     */
    public void createHabitBoxClick(View v) {
        boolean check = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.createHabitMonBox:
                if (check) tempDays.add(1);
                else tempDays.remove((Integer) 1 );
                break;
            case R.id.createHabitTueBox:
                if (check) tempDays.add(2);
                else tempDays.remove((Integer) 2);
                break;
            case R.id.createHabitWedBox:
                if (check) tempDays.add(3);
                else tempDays.remove((Integer) 3);
                break;
            case R.id.createHabitThuBox:
                if (check) tempDays.add(4);
                else tempDays.remove((Integer) 4);
                break;
            case R.id.createHabitFriBox:
                if (check) tempDays.add(5);
                else tempDays.remove((Integer) 5);
                break;
            case R.id.createHabitSatBox:
                if (check) tempDays.add(6);
                else tempDays.remove((Integer) 6);
                break;
            case R.id.createHabitSunBox:
                if (check) tempDays.add(7);
                else tempDays.remove((Integer) 7);
                break;
        }


    }

    /**
     * Creates habit using given info
     *
     * @param v view of input info
     */
    public void createHabitButtonClick(View v) {
        // Get editTexts to be used
        EditText titleBar = (EditText) findViewById(R.id.createHabitTitleBar);
        EditText reasonBar = (EditText) findViewById(R.id.createHabitReasonBar);
        EditText dateBar = (EditText) findViewById(R.id.createHabitDateBar);

        // Extract info from editTexts
        String title = titleBar.getText().toString();
        String reason = reasonBar.getText().toString();
        String dateString = dateBar.getText().toString();
        Date date;

        // If title of habitType is blank or > 30 chars, display message and stop

        if (! HabitTypeValid.validName(title) ) {
            this.displayMessage("Name can't be blank/ over 20 chars");
            return;
        }

        // If title of habitType already exists, display message and stop
        System.out.print("----\n" + this.user.getHabitTypes() + "\n----" );
        if (! HabitTypeValid.isUnique(title, this.user) ) {
            this.displayMessage("Title must be unique");
            return;
        }

        // If habitType reason is blank or > 30 chars, display message and stop
        if (! HabitTypeValid.validReason(reason) ) {
            this.displayMessage("Reason can't be blank/ over 30 chars");
            return;
        }

        // If date is not in YYYY-MM-DD format, or is incorrect, display message and stop
        if (! HabitTypeValid.validDate(dateString)) {
            this.displayMessage("Invalid date format");
            return;
        }

        // If no boxes are checked, display message and stop
        if (! HabitTypeValid.validCheckbox(tempDays)){
            this.displayMessage("At least 1 box must be checked");
            return;
        }

        //User cUser = UserAccountManager.getLoggedInUser();

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(dateString);
        } catch (java.text.ParseException e) {
            this.displayMessage("Exception!");
            return;
        }
        HabitType ht = new HabitType(this.user.getUsername(), title, reason, date, tempDays);
        Intent returnIntent = new Intent();
        target = gson.toJson(ht);
        returnIntent.putExtra("HabitType", target);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    /**
     * Button for returning to previous menu
     *
     * @param v view of current activity
     */
    public void createHabitBackButton(View v) {
        this.goBack();
    }

    private void displayMessage(String s) {
        TextView tv = (TextView) findViewById(R.id.createHabitMessage);
        tv.setText(s);
    }

    private void goBack() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
