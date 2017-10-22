package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FollowerRequestsActivity extends AppCompatActivity {
    private TextView viewPermissionRequests;
    private ArrayList<User> followers;
    private ArrayList<FollowerRequest> followerRequests;
    private ListView followerRequestList;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_requests);
    }
}
