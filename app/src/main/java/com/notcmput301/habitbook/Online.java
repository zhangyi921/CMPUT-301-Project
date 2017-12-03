/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Online extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User loggedInUser;
    private Gson gson = new Gson();
    private ArrayList<HabitEvent> eventlist = new ArrayList<>();
    private Map<Integer, String> monthMap = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        this.loggedInUser = gson.fromJson(u, User.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init months
        monthMap.put(0, "Jan"); monthMap.put(1, "Feb"); monthMap.put(2, "Mar");
        monthMap.put(3, "Apr"); monthMap.put(4, "May"); monthMap.put(5, "Jun");
        monthMap.put(6, "Jul"); monthMap.put(7, "Aug"); monthMap.put(8, "Sept");
        monthMap.put(9, "Oct"); monthMap.put(10, "Nov"); monthMap.put(11, "Dec");


        //follower request status button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Oln_viewRequestStatus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent followerRequestActivity = new Intent(Online.this, FollowerRequestsActivity.class);
                followerRequestActivity.putExtra("passedUser", gson.toJson(loggedInUser));
                startActivity(followerRequestActivity);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.oln_mapButton);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(Online.this, MapsActivity.class);
                map.putExtra("events", gson.toJson(eventlist));
                startActivity(map);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //enables us to put our own icon and not show up as greys
        navigationView.setItemIconTintList(null);
        //change the headerviews name, and image
        View headerview = navigationView.getHeaderView(0);
        TextView navName = (TextView) headerview.findViewById(R.id.MNavH_Name);
        navName.setText(loggedInUser.getUsername());
        navigationView.setNavigationItemSelectedListener(this);
        fillList();
    }

    //Checks if user exists
    public void sendRequest(View view){
        EditText reqText = (EditText) findViewById(R.id.Oln_EText);
        Button sendReq = (Button) findViewById(R.id.Oln_sendRequest);
        String uname = reqText.getText().toString().toLowerCase();
        //check if the user is a troll and put in his own username
        Followers newF = new Followers(loggedInUser.getUsername(), uname);
        if (uname.equals(loggedInUser.getUsername())){
            Toast.makeText(this, "Can't follow yourself", Toast.LENGTH_SHORT).show();
            return;
        }
        //check if the person is already followed
        ArrayList<Followers> allFollowers = new ArrayList<>();
        ElasticSearch.getFollowerPairs gFP = new ElasticSearch.getFollowerPairs();
        //the second argument denotes which perspective we are coming from.
        //requester indicates we want all followers objects where requester=username
        //requested indicates we want all followers objects where requested=username
        gFP.execute(loggedInUser.getUsername(), "requester", "2");
        try{
            allFollowers = gFP.get();
            //will return null if it failed to retrieve items
            if (allFollowers==null){
                Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve followers. Check connection", Toast.LENGTH_SHORT).show();
        }
        //check if user already followed this person
        for(Followers f: allFollowers){
            if (f.getRequestedUser().equals(uname)){
                Toast.makeText(this, "Follower already requested", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //check if username exist.. if it does send the request
        if (uname.isEmpty()){
            Toast.makeText(this, "Username needed", Toast.LENGTH_SHORT).show();
        }else{
            ElasticSearch.userExists ue = new ElasticSearch.userExists();
            ue.execute(uname);
            try{
                int success = ue.get();
                if (success < 0){
                    Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                    return;
                }else if (success == 0){
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //send request
                    ElasticSearch.addFollowerPair aF = new ElasticSearch.addFollowerPair();
                    aF.execute(newF);
                    try {
                        Boolean res = aF.get();
                        if (res) {
                            Toast.makeText(this, "Request Sent!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Toast.makeText(this, "Oops!, Something went wrong on our end", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (Exception e) {
                        Log.e("get failure", "Failed to add request");
                        e.printStackTrace();
                        Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }catch(Exception e){
                Log.e("get failure", "Failed to retrieve");
                e.printStackTrace();
                Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    public void fillList(){
        //get all followed users
        ElasticSearch.getFollowerPairs followers = new ElasticSearch.getFollowerPairs();
        followers.execute(loggedInUser.getUsername(), "requester", "1");
        ArrayList<Followers> fArr = new ArrayList<>();
        try{
            fArr = followers.get();
            //will return null if it failed to retrieve items
            if (fArr==null){
                fArr = new ArrayList<>();
                Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve followers. Check connection", Toast.LENGTH_SHORT).show();
        }
        //for each user, cycle through all their habittypes.
        for (Followers f: fArr){
            String requestedUser = f.getRequestedUser();
            ElasticSearch.getHabitTypeList gHT = new ElasticSearch.getHabitTypeList();
            gHT.execute(requestedUser);
            ArrayList<HabitType> habitTypes = new ArrayList<>();
            try {
                habitTypes = gHT.get();
                if (habitTypes==null){
                    habitTypes = new ArrayList<>();
                }
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Failed to retrieve items. Check connection", Toast.LENGTH_SHORT).show();
            }
            //add all their events of that habit type to eventlist
            for (HabitType h: habitTypes){
                eventlist.addAll(h.getEvents());
            }

//            for (int i = 0; i<eventlist.size(); ++i){
//                Toast.makeText(this, eventlist.get(i).getHabit(), Toast.LENGTH_SHORT).show();
//            }
        }
        ListView eventListView = (ListView) findViewById(R.id.Oln_eventListView);
        Online.OnlineListAdapter oAdapter = new Online.OnlineListAdapter();
        eventListView.setAdapter(oAdapter);
    }

    class OnlineListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return eventlist.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.online_list_layout, null);
            TextView titleE = (TextView) convertView.findViewById(R.id.OLIST_Title);
            TextView nameE = (TextView) convertView.findViewById(R.id.OLIST_Name);
            TextView dateE = (TextView) convertView.findViewById(R.id.OLIST_Date);
            CircleImageView imageV = (CircleImageView) convertView.findViewById(R.id.eventImg);
            Button like = (Button) convertView.findViewById(R.id.likeButton);
            like.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    HabitEvent habitEvent = eventlist.get(position);
                    habitEvent.setLikes(habitEvent.getLikes()+1);
                    ArrayList<HabitType> habitTypes = new ArrayList<HabitType>();
                    ElasticSearch.getHabitTypeList ghtl = new ElasticSearch.getHabitTypeList();
                    ghtl.execute(eventlist.get(position).getUser());
                    try {
                        habitTypes = ghtl.get();
                        if (habitTypes==null){
                            habitTypes = new ArrayList<>();
                        }
                        //loggedInUser.setHabitTypes(habitTypes);       //causes program to crash
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(Online.this, "Failed to retrieve items. Check connection", Toast.LENGTH_SHORT).show();
                    }

                    for (HabitType h : habitTypes){
                        //Toast.makeText(Online.this, h.getTitle().toString(), Toast.LENGTH_SHORT).show();
                        ArrayList<HabitEvent> he = h.getEvents();
                        for (HabitEvent eve : he){
                            //Toast.makeText(Online.this, eve.getComment().toString()+"--"+habitEvent.getComment().toString(), Toast.LENGTH_SHORT).show();



                            if (eve.getComment().equals(habitEvent.getComment())){

                                ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
                                delHT.execute(loggedInUser.getUsername(), h.getTitle());
                                try{
                                    boolean result = delHT.get();
                                    if (result){
                                        //Toast.makeText(this, "deleted item!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(Online.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(Online.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                                }
                                h.getEvents().remove(eve);
                                h.getEvents().add(habitEvent);
                                ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
                                aht.execute(h);
                                try{
                                    String success = aht.get();
                                    if (success == null){
                                        Toast.makeText(Online.this, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                                    }else{

                                        Toast.makeText(Online.this, "liked!", Toast.LENGTH_SHORT).show();

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


                }
            });
            titleE.setText(eventlist.get(position).getHabit());
            nameE.setText(eventlist.get(position).getComment());
            Date start = eventlist.get(position).getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            dateE.setText(monthMap.get(month) + " " +day);
            imageV.setImageBitmap(eventlist.get(position).imageToBitmap());
            return convertView;
        }
    }

    //

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
        getMenuInflater().inflate(R.menu.online, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.follow) {
            Intent intent = new Intent(Online.this, FollowerRequestsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.maps){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.habit_type) {
            Intent habitType = new Intent(Online.this, HabitTypeList2.class);
            habitType.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(habitType);

        } else if (id == R.id.today_habit) {

            Intent habitType = new Intent(Online.this, MainActivity.class);
            habitType.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(habitType);
        } else if (id == R.id.habit_event_history) {

            Intent online = new Intent(Online.this, HabitEventHistory2.class);
            online.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(online);
        } else if (id == R.id.online) {

        } else if (id == R.id.logout) {
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
