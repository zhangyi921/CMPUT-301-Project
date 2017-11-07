package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

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

        Bundle bundle = getIntent().getExtras();
        String target = bundle.getString("user");
        Gson gson = new Gson();
        User user = gson.fromJson(target, User.class);

        ArrayAdapter<HabitType> Adapter = new ArrayAdapter<HabitType>(HabitTypeListActivity.this,
                android.R.layout.simple_list_item_1, user.getHabitTypes());
        ListView HabitList = (ListView) findViewById(R.id.HabitList);
        HabitList.setAdapter(Adapter);
        HabitList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

            }
        });

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
