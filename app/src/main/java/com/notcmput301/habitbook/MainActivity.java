package com.notcmput301.habitbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User loggedInUser;
    private ArrayList<HabitType> habitTypes;
    private ArrayAdapter<HabitType> Adapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        this.loggedInUser = gson.fromJson(u, User.class);
        fillList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                fillList();
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            // Handle the camera action
            Intent habitType = new Intent(MainActivity.this, HabitTypeList2.class);
            habitType.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(habitType);
        } else if (id == R.id.today_habit) {

        } else if (id == R.id.habit_event_history) {
            Intent habitEventHistory = new Intent(MainActivity.this, HabitEventHistory2.class);
            habitEventHistory.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(habitEventHistory);
        } else if (id == R.id.online) {
            Intent online = new Intent(MainActivity.this, Online.class);
            online.putExtra("passedUser", gson.toJson(loggedInUser));
            finish();
            startActivity(online);

        } else if (id == R.id.logout) {
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fillList(){
        ListView habitlist = (ListView) findViewById(R.id.TodayHabitList);
        ElasticSearch.getHabitTypeList ghtl = new ElasticSearch.getHabitTypeList();
        ghtl.execute(loggedInUser.getUsername());
        try {
            habitTypes = ghtl.get();
            if (habitTypes==null){
                habitTypes = new ArrayList<>();
            }

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            Date currentDate = new Date();

            for(int i = 0; i<habitTypes.size(); i++){
                HabitType habit = habitTypes.get(i);
                ArrayList<Boolean> days = habit.getWeekdays();
                Date startDate = habit.getStartDate();
                if(!days.get(day-1)){
                    habitTypes.remove(habit);
                    i--;
                }
                else if(startDate.after(currentDate)){
                    habitTypes.remove(habit);
                    i--;
                }
                else {
                    ArrayList<HabitEvent> events = habit.getEvents();
                    for(int j = 0; j < events.size(); j++) {
                        HabitEvent event = events.get(j);
                        Date eventDate = event.getDate();
                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal1.setTime(currentDate);
                        cal2.setTime(eventDate);
                        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                            habitTypes.remove(habit);
                            i--;
                        }
                    }
                }
            }
            //loggedInUser.setHabitTypes(habitTypes);       //causes program to crash
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve items. Check connection", Toast.LENGTH_SHORT).show();
        }
        
        MainActivity.HabitTypeAdapter hAdapter = new MainActivity.HabitTypeAdapter();
        habitlist.setAdapter(hAdapter);

        habitlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent habitdetail = new Intent(MainActivity.this, HabitTypeDetailsActivity.class);
                habitdetail.putExtra("passedUser", gson.toJson(loggedInUser));
                habitdetail.putExtra("passedHabitType", gson.toJson(habitTypes.get(position)));
                startActivity(habitdetail);
            }
        });
    }


    /*public void HTLnewHabitType(View view){

        Intent createHabit = new Intent(HabitTypeList2.this, CreateHabitActivity.class);
        createHabit.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(createHabit);
    }*/


    public void HTLRefresh(View view){

        fillList();
    }




    class HabitTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return habitTypes.size();
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
            convertView = getLayoutInflater().inflate(R.layout.habit_type_list_layout, null);
            TextView titleL = (TextView) convertView.findViewById(R.id.HTLIST_Title);
            TextView descriptionL = (TextView) convertView.findViewById(R.id.HTLIST_Description);
            TextView NumEvents = (TextView) convertView.findViewById(R.id.NumEvents);

            NumEvents.setText("Number of total events: "+habitTypes.get(position).getTotalEvents().toString());
            titleL.setText(habitTypes.get(position).getTitle());
            descriptionL.setText(habitTypes.get(position).getReason());
            return convertView;
        }
    }

}
