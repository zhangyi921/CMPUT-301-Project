package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HabitEventDetailsActivity extends AppCompatActivity {

    private User loggedInUser;
    private HabitEvent habitEvent;
    private EditText titleE;
    private EditText commentE;
    private Button addPicB;
    private ArrayList<HabitType> habitTypes;
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

    public void update(View view){
        ElasticSearch.getHabitTypeList ghtl = new ElasticSearch.getHabitTypeList();
        ghtl.execute(loggedInUser.getUsername());
        try {
            habitTypes = ghtl.get();
            if (habitTypes==null){
                habitTypes = new ArrayList<>();
            }
            //loggedInUser.setHabitTypes(habitTypes);       //causes program to crash
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve items. Check connection", Toast.LENGTH_SHORT).show();
        }

        for (HabitType h : habitTypes){
            ArrayList<HabitEvent> he = h.getEvents();
            for (HabitEvent eve : he){

                if (eve.getComment().equals(habitEvent.getComment())){

                    ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
                    delHT.execute(loggedInUser.getUsername(), h.getTitle());
                    try{
                        boolean result = delHT.get();
                        if (result){
                            //Toast.makeText(this, "deleted item!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                    h.getEvents().remove(eve);
                    habitEvent.setComment(commentE.getText().toString());
                    h.getEvents().add(habitEvent);
                    ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
                    aht.execute(h);
                    try{
                        boolean success = aht.get();
                        if (!success){
                            Toast.makeText(this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(this, "Updated Habit Event!", Toast.LENGTH_SHORT).show();
                            //finish();
                            return;
                        }
                    }catch(Exception e){
                        Log.e("get failure", "Failed to retrieve");
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        //finish();

    }

    public void back(View view){
        finish();
    }
}
