package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HabitTypeDetailsActivity extends AppCompatActivity {
    private User LoggedInUser;
    private HabitType habit;
    private TextView title;
    private TextView reason;
    private EditText titleText;
    private EditText reasonText;
    private TextView habitSchedule;
    private TextView weekdays;
    private ToggleButton mondayButton;
    private ToggleButton tuesdayButton;
    private ToggleButton wednesdayButton;
    private ToggleButton thursdayButton;
    private ToggleButton fridayButton;
    private ToggleButton saturdayButton;
    private ToggleButton sundayButton;
    private TextView completionStatusView;
    private Button back;
    private Button update;
    private Button delete;
    private Button addNewHabitEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_details);
    }

    public void displayCompletionStatus(HabitType Habit){

    }
}
