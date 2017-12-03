package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TodaysHabitActivity extends AppCompatActivity {
    private User loggedInUser;
    private HabitListStore HLS;
    private ArrayList<HabitEvent> todaysHabits;
    private ListView habitList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_habit);

    }

}

