package com.notcmput301.habitbook;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User loggedInUser;

    private HabitTypeSingleton HTS;

    private ArrayList<HabitType> habitTypes;

    //position map
    private ArrayList<HabitType> todayHabits;
    private Map<String, Integer> positionMap = new HashMap<>();

    private Gson gson = new Gson();
    private NetworkHandler nH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent receiver = getIntent();

        String u = receiver.getExtras().getString("passedUser");
        HTS = HabitTypeSingleton.getInstance();
        loggedInUser = gson.fromJson(u, User.class);;
        habitTypes = HTS.getHabitTypes();

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

        //get our network handler
        nH = new NetworkHandler(this);

        //Checks if Network Connection is detected.
        BroadcastReceiver br = new NetworkStateChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(br, filter);

        fillList();
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
            if(!nH.isNetworkAvailable()){
                Toast.makeText(this, "Content Not accessible without internet", Toast.LENGTH_LONG).show();
            }else{
                Intent online = new Intent(MainActivity.this, Online.class);
                online.putExtra("passedUser", gson.toJson(loggedInUser));
                finish();
                startActivity(online);
            }
        } else if (id == R.id.logout) {
            Intent logout = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logout);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fillList(){
        this.todayHabits = new ArrayList<>();

        ListView habitlist = (ListView) findViewById(R.id.TodayHabitList);

        if (nH.isNetworkAvailable() && habitTypes.size() == 0){
            habitTypes = nH.getHabitList(loggedInUser.getUsername());
            HTS.setHabitTypes(habitTypes);
        }else if (!nH.isNetworkAvailable()){
            Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
        }

        for (int i = 0; i < habitTypes.size(); i++){
            HabitType hT = habitTypes.get(i);

            //calculate if it is the days is before today
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            if(sdf.format(hT.getStartDate()).compareTo(sdf.format(today)) <= 0){
                //if habit type date is today or before today. We need the weekdayws
                Calendar cal = Calendar.getInstance();
//                Toast.makeText(this, cal.get(Calendar.DAY_OF_WEEK) + " ", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, hT.getWeekdays().toString() + " ", Toast.LENGTH_SHORT).show();=
                int index = (cal.get(Calendar.DAY_OF_WEEK)+5)%7;
                //if there is no habit event today and no week day == today is checked off
                boolean isComleted = false;
                for (HabitEvent hE: hT.getEvents()){
                    if(sdf.format(hE.getDate()).equals(sdf.format(today))) {isComleted = true; break;}
                }
                if (hT.getWeekdays().get(index) && !isComleted){
                    todayHabits.add(hT);
                    positionMap.put(hT.getTitle(), i);
                }
            }
        }

        MainActivity.HabitTypeAdapter hAdapter = new MainActivity.HabitTypeAdapter();
        habitlist.setAdapter(hAdapter);

        habitlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int hListPos = positionMap.get(todayHabits.get(position).getTitle());
                Intent habitdetail = new Intent(MainActivity.this, HabitTypeDetailsActivity.class);
                habitdetail.putExtra("passedUser", gson.toJson(loggedInUser));
                habitdetail.putExtra("passedPos", hListPos+"");
                startActivity(habitdetail);
            }
        });
    }


    public void HTLRefresh(View view){

        fillList();
    }


    class HabitTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return todayHabits.size();
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
            convertView = getLayoutInflater().inflate(R.layout.todays_habit_layout, null);
            TextView titleL = (TextView) convertView.findViewById(R.id.THL_Title);
            TextView descriptionL = (TextView) convertView.findViewById(R.id.THL_Comment);

            titleL.setText(todayHabits.get(position).getTitle());
            descriptionL.setText(todayHabits.get(position).getReason());
            return convertView;
        }
    }
}
