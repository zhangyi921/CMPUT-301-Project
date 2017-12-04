package com.notcmput301.habitbook;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HabitTypeList2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User loggedInUser;
    private HabitTypeSingleton HTS;
    private ArrayList<HabitType> habitTypes;
    private ArrayAdapter<HabitType> Adapter;
    private Gson gson = new Gson();
    private NetworkHandler nH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_list2);


        Intent receiver = getIntent();

        String u = receiver.getExtras().getString("passedUser");

        this.loggedInUser = gson.fromJson(u, User.class);
        this.HTS = HabitTypeSingleton.getInstance();
        this.habitTypes = HTS.getHabitTypes();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTS.setHabitTypes(habitTypes);
                Intent createHabit = new Intent(HabitTypeList2.this, CreateHabitActivity.class);
                createHabit.putExtra("passedUser", gson.toJson(loggedInUser));
                startActivity(createHabit);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null); //enables us to put our own icon and not show up as greys

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
        getMenuInflater().inflate(R.menu.habit_type_list2, menu);
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

        } else if (id == R.id.today_habit) {
            HTS.setHabitTypes(habitTypes);
            Intent todayHabit = new Intent(HabitTypeList2.this, MainActivity.class);
            todayHabit.putExtra("passedUser", gson.toJson(loggedInUser));
            startActivity(todayHabit);

        } else if (id == R.id.habit_event_history) {

            Intent habitType = new Intent(HabitTypeList2.this, HabitEventHistory2.class);

            habitType.putExtra("passedUser", gson.toJson(loggedInUser));

            finish();
            startActivity(habitType);
        } else if (id == R.id.online) {
            if(!nH.isNetworkAvailable()){
                Toast.makeText(this, "Content Not accessible without internet", Toast.LENGTH_LONG).show();
            }else{
                HTS.setHabitTypes(habitTypes);
                Intent online = new Intent(HabitTypeList2.this, Online.class);
                online.putExtra("passedUser", gson.toJson(loggedInUser));
                finish();
                startActivity(online);
            }
        } else if (id == R.id.logout) {
            Intent logout = new Intent(HabitTypeList2.this, LoginActivity.class);
            startActivity(logout);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fillList(){
        ListView habitlist = (ListView) findViewById(R.id.HabitList);
        if(nH.isNetworkAvailable() && habitTypes.size()==0){
            habitTypes = nH.getHabitList(loggedInUser.getUsername());
            HTS.setHabitTypes(habitTypes);
        }else if (!nH.isNetworkAvailable()){
            Toast.makeText(this, "You are Offline", Toast.LENGTH_SHORT).show();
        }

        //TODO implement list
        HabitTypeList2.HabitTypeAdapter hAdapter = new HabitTypeList2.HabitTypeAdapter();
        habitlist.setAdapter(hAdapter);

        habitlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent habitdetail = new Intent(HabitTypeList2.this, HabitTypeDetailsActivity.class);
                habitdetail.putExtra("passedUser", gson.toJson(loggedInUser));
                habitdetail.putExtra("passedPos", position+"");
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
