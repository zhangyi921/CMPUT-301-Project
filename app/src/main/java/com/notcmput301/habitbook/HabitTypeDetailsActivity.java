package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HabitTypeDetailsActivity extends AppCompatActivity {
    private HabitType habit;
    private User loggedInUser;
    private EditText titleE;
    private EditText reasonE;
    private EditText startDateE;
    private ArrayList<CheckBox> weekdays = new ArrayList<>();
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private Gson gson = new Gson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_details);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        String h = receiver.getExtras().getString("passedHabitType");
        this.loggedInUser = gson.fromJson(u, User.class);
        this.habit = gson.fromJson(h, HabitType.class);
        loadData();
    }

    public void loadData(){
        titleE = (EditText) findViewById(R.id.HTD_TitleE);
        reasonE = (EditText) findViewById(R.id.HTD_ReasonE);
        startDateE = (EditText) findViewById(R.id.HTD_StartDateE);
        weekdays = new ArrayList<>();
        weekdays.add((CheckBox) findViewById(R.id.HTD_M));
        weekdays.add((CheckBox) findViewById(R.id.HTD_T));
        weekdays.add((CheckBox) findViewById(R.id.HTD_W));
        weekdays.add((CheckBox) findViewById(R.id.HTD_Tr));
        weekdays.add((CheckBox) findViewById(R.id.HTD_F));
        weekdays.add((CheckBox) findViewById(R.id.HTD_Sa));
        weekdays.add((CheckBox) findViewById(R.id.HTD_Su));
        ArrayList<Boolean> checked = habit.getWeekdays();
        for (int i = 0; i < weekdays.size(); i++){
            if (checked.get(i)){
                weekdays.get(i).toggle();
            }
        }
        titleE.setText(habit.getTitle());
        reasonE.setText(habit.getReason());
        startDateE.setText(df.format(habit.getStartDate()));
    }

    public void HTDback(View view){
        Intent habitTypeList = new Intent();
        habitTypeList.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(habitTypeList);
    }

    public void HTDUpdate(View view){

    }

    public void HTDDelete(View view){
        ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
        delHT.execute(loggedInUser.getUsername(), habit.getTitle());
        try{
            boolean result = delHT.get();
            if (result){
                Intent habitTypeList = new Intent(HabitTypeDetailsActivity.this, HabitTypeListActivity.class);
                Toast.makeText(this, "deleted item!", Toast.LENGTH_SHORT).show();
                habitTypeList.putExtra("passedUser", gson.toJson(loggedInUser));
                startActivity(habitTypeList);
                return;
            }else{
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    public void HTDAddEvent(View view){
        Intent createHabitEvent = new Intent(HabitTypeDetailsActivity.this, CreateHabitEventActivity.class);
        createHabitEvent.putExtra("passedUser", gson.toJson(loggedInUser));
        createHabitEvent.putExtra("passedHabitType", gson.toJson(habit));
        startActivity(createHabitEvent);
    }
}
