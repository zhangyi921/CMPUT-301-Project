package com.notcmput301.habitbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class HabitTypeListActivity extends AppCompatActivity {
    private User loggedInUser;
    private ArrayList<HabitType> habitTypes;
    private ArrayAdapter<HabitType> Adapter;
    private Gson gson = new Gson();
//    private String target;
//    private Gson gson;
//    //private User user;
//    private LocalFileControler localFileControler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_type_list);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        loggedInUser = gson.fromJson(u, User.class);
        fillList();

//        Bundle bundle = getIntent().getExtras();
//        this.target = bundle.getString("user");
//        this.gson = new Gson();
//        this.user = gson.fromJson(target, User.class);
//        this.habitTypes = user.getHabitTypes();
//        this.target = bundle.getString("localfilecontroller");
//        this.localFileControler = gson.fromJson(target, LocalFileControler.class);
//
//        this.Adapter = new ArrayAdapter<HabitType>(HabitTypeListActivity.this,
//                android.R.layout.simple_list_item_1, this.habitTypes);
//        this.habitTypeList = (ListView) findViewById(R.id.HabitList);
//        this.habitTypeList.setAdapter(Adapter);
//        this.habitTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
//                Intent intent = new Intent(HabitTypeListActivity.this, HabitTypeDetailsActivity.class);
//                intent.putExtra("index", i);
//                target = gson.toJson(user);
//                intent.putExtra("user", target);
//                target = gson.toJson(localFileControler);
//                intent.putExtra("localfilecontroller", target);
//                startActivityForResult(intent, 1);
//
//            }
//        });
//
//        Button NewHabit = (Button) findViewById(R.id.NewHabit);
//        NewHabit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HabitTypeListActivity.this, CreateHabitActivity.class);
//                target = gson.toJson(user);
//                intent.putExtra("user", target);
//                target = gson.toJson(localFileControler);
//                intent.putExtra("localfilecontroller", target);
//                startActivityForResult(intent,2);
//            }
//        }   );
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

        HabitTypeAdapter hAdapter = new HabitTypeAdapter();
        habitlist.setAdapter(hAdapter);

        habitlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent habitdetail = new Intent(HabitTypeListActivity.this, HabitTypeDetailsActivity.class);
                habitdetail.putExtra("passedUser", gson.toJson(loggedInUser));
                habitdetail.putExtra("passedHabitType", gson.toJson(habitTypes.get(position)));
                startActivity(habitdetail);
            }
        });
    }


    public void HTLnewHabitType(View view){

        Intent createHabit = new Intent(HabitTypeListActivity.this, CreateHabitActivity.class);
        createHabit.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(createHabit);
    }


    public void HTLRefresh(View view){

        fillList();
    }


    public void HTLBack(View view){

        Intent mainmenu = new Intent(HabitTypeListActivity.this, MainMenuActivity.class);
        mainmenu.putExtra("passedUser", gson.toJson(loggedInUser));
        startActivity(mainmenu);
    }


    class HabitTypeAdapter extends BaseAdapter{

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








//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
//        // back from detail page
//        if (requestCode == 1){
//            // if user makes changes
//            if (resultCode == Activity.RESULT_OK){
//                // reload user from saved file
//                this.user = this.localFileControler.Login(this.user.getUsername(), this.user.getPassword());
//                this.habitTypes = this.user.getHabitTypes();
//                this.Adapter.notifyDataSetChanged();
//            }
//        }
//        // back from creating habit type page
//        else if (requestCode == 2){
//            // if user makes changes
//            if (resultCode == Activity.RESULT_OK){
//                // reload user from saved file
//                this.user = this.localFileControler.Login(this.user.getUsername(), this.user.getPassword());
//                this.habitTypes = this.user.getHabitTypes();
//                this.Adapter.notifyDataSetChanged();
//            }
//        }
//    }
}
