package com.notcmput301.habitbook;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by shang on 11/10/2017.
 */

public class ElasticSearch {
    private static JestDroidClient client;

    /**
     * Used for verifying login, returns 2 if fault
     */
    public static class verifyLogin extends AsyncTask<String, Void, User>{

        /**
         * Used to login. returns user upon success
         * @param q
         * @return
         */
        @Override
        public User doInBackground(String... q){
            verifySettings();
            if (q.length != 2){
                Log.e("Bad input", "expected 2 strings");
                return null;
            }
            String username = q[0];
            String password = q[1];
            String jsonQuery = "{ \"query\": {\"bool\": {\"must\": [{\"match\": {\"username\": \""+username+"\"}},{\"match\": {\"password\": \""+password+"\"}}]}}}";
            Search search = new Search.Builder(jsonQuery).addIndex("t28test9").addType("user").build();

            try{
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    return result.getSourceAsObject(User.class);
                }
            }catch(Exception e){
                Log.e("Failed Q", "Search broke");
                return null;
            }
            return null;
        }
    }

    /**
     * Used to check if username exists, -1 denotes fault
     */
    public static class userExists extends AsyncTask<String, Void, Integer>{

        @Override
        public Integer doInBackground(String... q){
            verifySettings();
            if (q.length != 1){
                Log.e("Bad input", "expected 1 string");
                return -1;
            }
            String username = q[0];
            String jsonQuery = "{\"query\": {\"match\": {\"username\": \""+username+"\"}}}";
            Search search = new Search.Builder(jsonQuery).addIndex("t28test9").addType("user").build();

            try{
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    return result.getTotal();
                }
            }catch(Exception e){
                Log.e("Failed Q", "Search broke");
                return -1;
            }
            return -1;
        }
    }

    /**
     * Used to add user to database, -1 deonted fault;
     */

    public static class addUser extends AsyncTask<User, Void, User>{

        @Override
        public User doInBackground(User... u){
            verifySettings();
            if (u.length != 1){
                Log.e("Bad input", "expected 1 user");
                return null;
            }
            Index uitem = new Index.Builder(u[0]).index("t28test9").type("user").build();
            try{
                DocumentResult result = client.execute(uitem);
                if (result.isSucceeded()) {
                    u[0].setId(result.getId());
                    return u[0];
                }
            }catch(Exception e){
                e.printStackTrace();
                Log.e("failed add", "Failed to add user");
                return null;
            }
            return null;
        }
    }

    /**
     * Used to add HabitType to database. Returns false on fail
     */
    public static class addHabitType extends AsyncTask<HabitType, Void, Boolean>{

        @Override
        public Boolean doInBackground(HabitType... ht){
            verifySettings();
            if (ht.length != 1){
                Log.e("Bad input", "expected 1 Habit type");
                return false;
            }
            Index htitem = new Index.Builder(ht[0]).index("t28test9").type("habittype").build();
            try{
                DocumentResult result = client.execute(htitem);
                if (result.isSucceeded()){
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("failed add", "Failed to add HabitType");
                return false;
            }
            return false;
        }
    }

    /**
     * Used to return list of habitTypes
     */
    public static class getHabitTypeList extends AsyncTask<String, Void, ArrayList<HabitType>>{

        @Override
        public ArrayList<HabitType> doInBackground(String... s) {
            verifySettings();
            ArrayList<HabitType> resultArr = new ArrayList<>();
            if (s.length != 1) {
                Log.e("Bad input", "expected 1 String");
                return null;
            }
            String username = s[0];
            String jsonQuery = "{ \"query\": {\"match\": {\"ownername\": \"" + username + "\"}}}";
            Search search = new Search.Builder(jsonQuery).addIndex("t28test9").addType("habittype").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<HabitType> found = result.getSourceAsObjectList(HabitType.class);
                    resultArr.addAll(found);
                    return resultArr;
                }
            } catch (Exception e) {
                Log.e("Failed Q", "Search broke");
                return null;
            }
            return null;
        }
    }

    public static void verifySettings(){
        if (client==null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
