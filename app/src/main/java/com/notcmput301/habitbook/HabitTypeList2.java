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

public class HabitTypeList2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User loggedInUser;
    private ArrayList<HabitType> habitTypes;
    private ArrayAdapter<HabitType> Adapter;
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_list2);

        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        loggedInUser = gson.fromJson(u, User.class);
        fillList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            // Handle the camera action
        } else if (id == R.id.today_habit) {

        } else if (id == R.id.habit_event_history) {

        } else if (id == R.id.online) {

        } else if (id == R.id.setting) {

        } else if (id == R.id.logout) {
            Intent createAccount = new Intent(HabitTypeList2.this, LoginActivity.class);
            startActivity(createAccount);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fillList(){
        ListView habitlist = (ListView) findViewById(R.id.HabitList);
        ElasticSearch.getHabitTypeList ghtl = new ElasticSearch.getHabitTypeList();
        ghtl.execute(loggedInUser.getUsername());
        try {
            habitTypes = ghtl.get();
            if (habitTypes==null){
                habitTypes = new ArrayList<>();
            }
            loggedInUser.setHabitTypes(habitTypes);       //causes program to crash
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve items. Check connection", Toast.LENGTH_SHORT).show();
        }

        HabitTypeList2.HabitTypeAdapter hAdapter = new HabitTypeList2.HabitTypeAdapter();
        habitlist.setAdapter(hAdapter);

        habitlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent habitdetail = new Intent(HabitTypeList2.this, HabitTypeDetailsActivity.class);
                habitdetail.putExtra("passedUser", gson.toJson(loggedInUser));
                habitdetail.putExtra("passedHabitType", gson.toJson(habitTypes.get(position)));
                startActivity(habitdetail);
            }
        });
    }


    public void HTLnewHabitType(View view){

        Intent createHabit = new Intent(HabitTypeList2.this, CreateHabitActivity.class);
        createHabit.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(createHabit);
    }


    public void HTLRefresh(View view){

        fillList();
    }


    public void HTLBack(View view){

        Intent mainmenu = new Intent(HabitTypeList2.this, MainMenuActivity.class);
        mainmenu.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(mainmenu);
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

            titleL.setText(habitTypes.get(position).getTitle());
            descriptionL.setText(habitTypes.get(position).getReason());
            return convertView;
        }
    }
}
