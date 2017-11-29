/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class HabitEventHistory2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User loggedInUser;
    private ArrayList<HabitType> habitTypes;
    private ArrayList<HabitEvent> habitEvents = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_history2);

        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        loggedInUser = gson.fromJson(u, User.class);
        FillList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FillList();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.habit_event_history2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.habit_type) {
            Intent habitType = new Intent(HabitEventHistory2.this, HabitTypeList2.class);
            habitType.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(habitType);

        } else if (id == R.id.today_habit) {

            Intent habitType = new Intent(HabitEventHistory2.this, MainActivity.class);
            habitType.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(habitType);
        } else if (id == R.id.habit_event_history) {

        } else if (id == R.id.online) {

            Intent online = new Intent(HabitEventHistory2.this, Online.class);
            online.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(online);
        } else if (id == R.id.setting) {

        } else if (id == R.id.logout) {
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void FillList(){
        ListView EventHistoryList = (ListView) findViewById(R.id.eventList);
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
        for (HabitType h :habitTypes){
            ArrayList<HabitEvent> events = h.getEvents();
            habitEvents.addAll(events);
            Integer i = habitEvents.size();
            Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show();
            /*for (HabitEvent e : events){
                habitEvents.add(e);
            }*/
        }
        HabitEventHistory2.EventHistoryAdapter eventHistoryAdapter = new HabitEventHistory2.EventHistoryAdapter();
        EventHistoryList.setAdapter(eventHistoryAdapter);
        EventHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent habitdetail = new Intent(HabitEventHistory2.this, HabitEventDetailsActivity.class);
                habitdetail.putExtra("passedUser", gson.toJson(loggedInUser));
                habitdetail.putExtra("passedHabitEvent", gson.toJson(habitEvents.get(position)));
                startActivity(habitdetail);
            }
        });
    }


    class EventHistoryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return habitEvents.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.habit_event_list_layout, null);
            TextView titleL = (TextView) convertView.findViewById(R.id.HTLIST_Title);
            TextView descriptionL = (TextView) convertView.findViewById(R.id.HTLIST_Description);

            titleL.setText(habitEvents.get(position).getComment());
            descriptionL.setText("hbit type:"+habitEvents.get(position).getHabit());
            return convertView;
        }
    }
}
