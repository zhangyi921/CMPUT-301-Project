package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HabitTypeListActivity extends AppCompatActivity {
    private User loggedInUser;
    private ArrayList<HabitType> habitTypes;
    private ListView habitTypeList;
    private Button addNewHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_list);

        Button NewHabit = (Button) findViewById(R.id.NewHabit);
        NewHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitTypeListActivity.this, CreateHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}
