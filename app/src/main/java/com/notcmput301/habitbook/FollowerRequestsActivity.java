package com.notcmput301.habitbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FollowerRequestsActivity extends AppCompatActivity {
    private User loggedInUser;
    private HabitListStore HLS;
    private Gson gson = new Gson();
    private ArrayList<Followers> reqArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_requests);
        Intent receiver = getIntent();
        String u = receiver.getExtras().getString("passedUser");
        String l = receiver.getExtras().getString("passedHList");
        this.loggedInUser = gson.fromJson(u, User.class);
        this.HLS = gson.fromJson(l, HabitListStore.class);
        fillList();
    }

    public void getReqList(){
        ElasticSearch.getFollowerPairs followers = new ElasticSearch.getFollowerPairs();
        //we only want to get followers obj that are not verified yet
        followers.execute(loggedInUser.getUsername(), "requested", "0");
        try{
            reqArr = followers.get();
            //will return null if it failed to retrieve items
            if (reqArr==null){
                reqArr = new ArrayList<>();
                Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return;
            }
        }catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrieve followers. Check connection", Toast.LENGTH_SHORT).show();
        }
    }

    class RequestListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return reqArr.size();
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
            convertView = getLayoutInflater().inflate(R.layout.request_list_layout, null);
            TextView unameTV = (TextView) convertView.findViewById(R.id.RLIST_uname);
            Button acceptButton = (Button) convertView.findViewById(R.id.RLSIT_Accept);
            Button rejectButton = (Button) convertView.findViewById(R.id.RLIST_Reject);
            final Followers currPair = reqArr.get(position);
            final String requester = currPair.getRequester();
            final String requested = currPair.getRequestedUser();
            unameTV.setText(requester);
            acceptButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(getBaseContext(), "accepted!", Toast.LENGTH_SHORT).show();
                    //first delete
                    ElasticSearch.deleteFollowerPair dF = new ElasticSearch.deleteFollowerPair();
                    dF.execute(requester, requested);
                    //then re-add with status = 1
                    currPair.acceptRequest();
                    ElasticSearch.addFollowerPair aF = new ElasticSearch.addFollowerPair();
                    aF.execute(currPair);
                    //wait for it to finish
                    int currlen = reqArr.size();
                    while (reqArr.size()==currlen){
                        getReqList();
                    }
                    fillList();
                }
            });
            rejectButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(getBaseContext(), "rejected!", Toast.LENGTH_SHORT).show();
                    //just Delete
                    ElasticSearch.deleteFollowerPair dF = new ElasticSearch.deleteFollowerPair();
                    dF.execute(requester, requested);
                    //wait for finish (THIS IS REALLY BAD)
                    int currlen = reqArr.size();
                    while (reqArr.size()==currlen){
                        getReqList();
                    }
                    fillList();
                }
            });
            return convertView;
        }
    }

    public void fillList(){
        ListView reqList = (ListView) findViewById(R.id.fu_list);
        getReqList();
        FollowerRequestsActivity.RequestListAdapter rAdapter = new FollowerRequestsActivity.RequestListAdapter();
        reqList.setAdapter(rAdapter);
    }
}
