/*
 * HabitEventHistoryActivity
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activity for creating new account - Not yet implemented
 *
 * @author NOTcmput301
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
 */

public class HabitEventHistoryActivity extends AppCompatActivity {
    private User loggedInUser;
    private ArrayList<HabitEvent> habitEvents;
    private TextView habitEventHistoryView;
    private ListView habitEventHistoryList;
    private TextView habitTypeFilterView;
    private TextView commentFilterView;
    private EditText HabitTypeFilterText;
    private EditText commentFilterText;
    private Button filter;
    private Button viewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_history);
    }

    public void filterHabitEvents(HabitType habit){
        return;
    }

    public void filterHabitEvents(String comment) {
        return;
    }

    public void filterHabitEvents(HabitType habit, String comment){
        return;
    }
}
