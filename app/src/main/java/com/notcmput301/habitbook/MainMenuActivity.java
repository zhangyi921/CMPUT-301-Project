package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent receiver = getIntent();
        this.loggedInUser = receiver.getParcelableExtra("passedUser");
    }

    public void MMLogout(View view){
        Intent logout = new Intent(MainMenuActivity.this, LoginActivity.class);
        startActivity(logout);
        Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
    }

    public void MMHabitType(View view){
        Intent habitType = new Intent(MainMenuActivity.this, HabitTypeListActivity.class);
        habitType.putExtra("passedUser", loggedInUser);
        startActivity(habitType);
    }

    public void MMTodayHabit(View view){
        Intent todayHabit = new Intent(MainMenuActivity.this, TodaysHabitActivity.class);
        todayHabit.putExtra("passedUser", loggedInUser);
        startActivity(todayHabit);
    }

    public void MMHabitEventHistory(View view){
        Intent habitEventHistory = new Intent(MainMenuActivity.this, HabitEventHistoryActivity.class);
        habitEventHistory.putExtra("passedUser", loggedInUser);
        startActivity(habitEventHistory);
    }

    public void MMOnline(View view){
        Intent online = new Intent(MainMenuActivity.this, OnlineMenuActivity.class);
        online.putExtra("passedUser", loggedInUser);
        startActivity(online);
    }
}
