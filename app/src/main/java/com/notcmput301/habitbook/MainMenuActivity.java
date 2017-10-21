package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    private User loggedInUser;
    private TextView mainMenu;
    private Button habitTypes;
    private Button todaysHabits;
    private Button habitEventHistory;
    private Button onlineFunctions;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
}
