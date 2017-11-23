package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OnlineMenuActivity extends AppCompatActivity {
    private User loggedInUser;
    private ArrayList<HabitEvent> followedUserEvents;
    private TextView followedUserView;
    private ListView followedUserEventList;
    private Button sendFollowRequest;
    private EditText requestedUsernameText;
    private Button viewFollowerRequests;
    private Button back;
    private Button viewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_menu);
    }
}
