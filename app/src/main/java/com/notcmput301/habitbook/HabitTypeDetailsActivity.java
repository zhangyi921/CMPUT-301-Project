package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class HabitTypeDetailsActivity extends AppCompatActivity {
    private User LoggedInUser;
    private HabitType habit;
    private EditText titleText;
    private EditText reasonText;
    private TextView startDate;
    private TextView habitSchedule;
    private ArrayList<Boolean> weekdays;
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

//        titleText = (EditText) findViewById(R.id.CHT_Title);
//        titleText.setText(currentHabitType.getTitle());
//        reasonText = (EditText) findViewById(R.id.CHT_Reason);
//        reasonText.setText(currentHabitType.getReason());
//        startDate = (TextView) findViewById(R.id.CHT_Startdate);
//        startDate.setText("Starting Date: "+currentHabitType.getStartDate().toString());
//        monday = (CheckBox) findViewById(R.id.CHT_M);
//        monday.setChecked(this.weekdays.indexOf(1) != -1);
//        tuesday = (CheckBox) findViewById(R.id.CHT_T);
//        tuesday.setChecked(this.weekdays.indexOf(1) != -1);
//        wednesday = (CheckBox) findViewById(R.id.CHT_W);
//        wednesday.setChecked(this.weekdays.indexOf(1) != -1);
//        thursday = (CheckBox) findViewById(R.id.CHT_Tr);
//        thursday.setChecked(this.weekdays.indexOf(1) != -1);
//        friday = (CheckBox) findViewById(R.id.CHT_F);
//        friday.setChecked(this.weekdays.indexOf(1) != -1);
//        saturday = (CheckBox) findViewById(R.id.CHT_Sa);
//        saturday.setChecked(this.weekdays.indexOf(1) != -1);
//        sunday = (CheckBox) findViewById(R.id.Sun);
//        sunday.setChecked(this.weekdays.indexOf(1) != -1);
//        completionStatus = (TextView) findViewById(R.id.Status);
//        //completionStatus.setText(currentHabitType.get);
//
//        back = (Button) findViewById(R.id.Back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        }   );
//        update = (Button) findViewById(R.id.Update);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (monday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(1);
//                    }
//                }
//                if (tuesday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(2);
//                    }
//                }
//                if (wednesday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(3);
//                    }
//                }
//                if (thursday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(4);
//                    }
//                }
//                if (friday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(5);
//                    }
//                }
//                if (saturday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(6);
//                    }
//                }
//                if (sunday.isChecked()){
//                    if (weekdays.indexOf(1) == -1){
//                        weekdays.add(7);
//                    }
//                }
//                HabitType Newhabit = new HabitType(user, titleText.getText().toString(),
//                        reasonText.getText().toString(),
//                        currentHabitType.getStartDate(),
//                        weekdays);
//                Newhabit.setEvents(currentHabitType.getEvents());
//                Newhabit.setEventsCompleted(currentHabitType.getEventsCompleted());
//                Newhabit.setStartDate(currentHabitType.getStartDate());
//                Newhabit.setCreationDate(currentHabitType.getCreationDate());
//                Newhabit.setTotalEvents(currentHabitType.getTotalEvents());
//                user.updateHabitType(currentHabitType, Newhabit);
//                localFileControler.Save(user);
//                finish();
//
//            }
//        }   );
//        delete = (Button) findViewById(R.id.Delete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                user.removeHabitType(currentHabitType);
//                finish();
//            }
//        }   );


    }

    public void displayCompletionStatus(HabitType Habit){

    }
}
