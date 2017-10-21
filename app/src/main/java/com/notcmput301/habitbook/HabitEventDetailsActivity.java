package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HabitEventDetailsActivity extends AppCompatActivity {
    private User loggedInUser;
    private HabitType habit;
    private HabitEvent habitEvent;
    private TextView habitEventInfo;
    private TextView commentView;
    private EditText commentText;
    private TextView insertImage;
    private ImageView imageView;
    private Button uploadPic;
    private Button removePic;
    private Button uploadLocation;
    private Button removeLocation;
    private TextView eventLocationView;
    private boolean loactionUploaded;
    private boolean imageUploaded;
    private Button cancel;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_details);
    }
}
