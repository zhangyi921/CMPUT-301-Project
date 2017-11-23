package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
