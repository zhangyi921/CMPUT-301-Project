package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainMenuActivity extends AppCompatActivity {
    private User loggedInUser;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        this.loggedInUser = gson.fromJson(u, User.class);
    }

    public void MMLogout(View view){
        Intent logout = new Intent(MainMenuActivity.this, LoginActivity.class);
        startActivity(logout);
        Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
    }

    public void MMHabitType(View view){
        Intent habitType = new Intent(MainMenuActivity.this, HabitTypeListActivity.class);
        habitType.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(habitType);
    }

    public void MMTodayHabit(View view){
        Intent todayHabit = new Intent(MainMenuActivity.this, TodaysHabitActivity.class);
        todayHabit.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(todayHabit);
    }

    public void MMHabitEventHistory(View view){
        Intent habitEventHistory = new Intent(MainMenuActivity.this, HabitEventHistoryActivity.class);
        habitEventHistory.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(habitEventHistory);
    }

    public void MMOnline(View view){
        Intent online = new Intent(MainMenuActivity.this, OnlineMenuActivity.class);
        online.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(online);
    }
}
