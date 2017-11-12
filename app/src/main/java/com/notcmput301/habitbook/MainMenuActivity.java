package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainMenuActivity extends AppCompatActivity {
    private User loggedInUser;
    private TextView mainMenu;
    private Button habitTypes;
    private Button todaysHabits;
    private Button habitEventHistory;
    private Button onlineFunctions;
    private Button logout;
    private String target;
    private Gson gson;
    private LocalFileControler localFileController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //Intent receiver = getIntent();
        Bundle bundle = getIntent().getExtras();
        String target = bundle.getString("passedUser");
        Gson gson = new Gson();
        this.loggedInUser = gson.fromJson(target, User.class);
        this.gson = new Gson();
        //this.localFileController = new LocalFileControler();
    }

    public void logoutUser(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void viewHabitTypeList(View view) {
        Intent intent = new Intent(this, HabitTypeListActivity.class);
        target = gson.toJson(loggedInUser);
        intent.putExtra("user", target);
        //target = gson.toJson(localFileController);
        //intent.putExtra("localfilecontroller", target);
        startActivity(intent);
    }
}

