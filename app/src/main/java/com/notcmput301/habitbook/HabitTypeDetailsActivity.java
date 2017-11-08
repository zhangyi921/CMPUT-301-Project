package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.ArrayList;

public class HabitTypeDetailsActivity extends AppCompatActivity {
    private User LoggedInUser;
    private HabitType habit;
    private EditText titleText;
    private EditText reasonText;
    private TextView startDate;
    private TextView habitSchedule;
    private ArrayList<Integer> weekdays;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;
    private TextView completionStatus;
    private Button back;
    private Button update;
    private Button delete;
    private Button addNewHabitEvent;

    private String target;
    private Gson gson;
    private User user;
    private ArrayList<HabitType> habitTypes;
    private LocalFileControler localFileControler;
    private HabitType currentHabitType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_details);

        Bundle bundle = getIntent().getExtras();
        int index = bundle.getInt("index");
        this.target = bundle.getString("user");
        this.gson = new Gson();
        this.user = gson.fromJson(target, User.class);
        this.habitTypes = user.getHabitTypes();
        this.currentHabitType = this.habitTypes.get(index);
        this.target = bundle.getString("localfilecontroller");
        this.localFileControler = gson.fromJson(target, LocalFileControler.class);
        this.weekdays = currentHabitType.getWeekdays();

        titleText = (EditText) findViewById(R.id.Title);
        titleText.setText(currentHabitType.getTitle());
        reasonText = (EditText) findViewById(R.id.Reason);
        reasonText.setText(currentHabitType.getReason());
        startDate = (TextView) findViewById(R.id.StartDate);
        startDate.setText("Starting Date: "+currentHabitType.getStartDate().toString());
        monday = (CheckBox) findViewById(R.id.Mon);
        monday.setChecked(this.weekdays.indexOf(1) != -1);
        tuesday = (CheckBox) findViewById(R.id.Tu);
        tuesday.setChecked(this.weekdays.indexOf(1) != -1);
        wednesday = (CheckBox) findViewById(R.id.Wes);
        wednesday.setChecked(this.weekdays.indexOf(1) != -1);
        thursday = (CheckBox) findViewById(R.id.Thu);
        thursday.setChecked(this.weekdays.indexOf(1) != -1);
        friday = (CheckBox) findViewById(R.id.Fri);
        friday.setChecked(this.weekdays.indexOf(1) != -1);
        saturday = (CheckBox) findViewById(R.id.Sat);
        saturday.setChecked(this.weekdays.indexOf(1) != -1);
        sunday = (CheckBox) findViewById(R.id.Sun);
        sunday.setChecked(this.weekdays.indexOf(1) != -1);
        completionStatus = (TextView) findViewById(R.id.Status);
        //completionStatus.setText(currentHabitType.get);




    }

    public void displayCompletionStatus(HabitType Habit){

    }
}
