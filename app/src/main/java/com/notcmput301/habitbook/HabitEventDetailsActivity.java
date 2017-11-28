package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class HabitEventDetailsActivity extends AppCompatActivity {

    private User loggedInUser;
    private HabitEvent habitEvent;
    private EditText titleE;
    private EditText commentE;
    private Button addPicB;
    private Button createB;
    private Button backB;
    private CircleImageView imageV;
    private Gson gson = new Gson();
    private File image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        String e = receiver.getExtras().getString("passedHabitEvent");
        loggedInUser = gson.fromJson(u, User.class);
        habitEvent = gson.fromJson(e, HabitEvent.class);
        commentE = (EditText) findViewById(R.id.CHE_Comment);
        commentE.setText(habitEvent.getComment());
    }

    public void back(View view){
        finish();
    }
}
