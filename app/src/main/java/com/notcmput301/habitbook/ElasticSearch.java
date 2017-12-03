package com.notcmput301.habitbook;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by shang on 11/10/2017.
 */

public class ElasticSearch {
    private static JestDroidClient client;
    protected static String db = "t28test11";    //DATABASE

    //-------------------_FOR USERS_-------------------------------

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
            Search search = new Search.Builder(jsonQuery).addIndex(db).addType("user").build();

            try{
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    User test = result.getSourceAsObject(User.class);
                    if (test.getUsername().toLowerCase().equals(username.toLowerCase()) && test.getPassword().equals(password)) return test;
                }
            }catch(Exception e){
                Log.e("Failed Q", "Search broke");
                return null;
            }
            return null;
        }
    }

    /**
     * Used to check if username exists, -1 denotes fault, 1 denotes username exists, 0 denotes
     * does not exist
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
            Search search = new Search.Builder(jsonQuery).addIndex(db).addType("user").build();

            try{
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    User test = result.getSourceAsObject(User.class);
                    if (test==null){
                        return 0;
                    }
                    if (test.getUsername().toLowerCase().equals(username.toLowerCase())){
                        return 1;
                    }
                }else return -1;
            }catch(Exception e){
                Log.e("Failed Q", "Search broke");
                return -1;
            }
            return 0;
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
            Index uitem = new Index.Builder(u[0]).index(db).type("user").build();
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

    //----------------------------FOR HABIT TYPES------------------------------------

    /**
     * Check if habit type NAME is unique
     */
    public static class habitTypeExists extends  AsyncTask<String, Void, Integer>{

        @Override
        public Integer doInBackground(String... s) {
            verifySettings();
            if (s.length != 2) {
                Log.e("Bad input", "expected 2 strings");
                return -1;
            }
            String ownername = s[0];
            String title = s[1];
            String jsonQuery = "{ \"query\": {\"bool\": {\"must\": [{\"match\": {\"ownername\": \"" + ownername + "\"}},{\"match\": {\"title\": \"" +title+"\"}}]}}}";
            Search search = new Search.Builder(jsonQuery).addIndex(db).addType("habittype").build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    HabitType test = result.getSourceAsObject(HabitType.class);
                    if (test==null){ //could nto find anything
                        return 0;
                    }
                    if (test.getTitle().toLowerCase().trim().replaceAll("\\s+", " ").equals(title.toLowerCase().trim().replaceAll("\\s+", " "))){
                        return 1; //found a match
                    }
                }else{
                    return -1; //error
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Failed Q", "Search broke");
                return -1;
            }
            return 0;
        }
    }

    /**
     * Gets a habit type
     */
    /**
     * Check if habit type NAME is unique
     */
    public static class getHabitType extends  AsyncTask<String, Void, HabitType>{

        @Override
        public HabitType doInBackground(String... s) {
            verifySettings();
            String ownername = s[0];
            String title = s[1];
            String jsonQuery = "{ \"query\": {\"bool\": {\"must\": [{\"match\": {\"ownername\": \"" + ownername + "\"}},{\"match\": {\"title\": \"" +title+"\"}}]}}}";
            Search search = new Search.Builder(jsonQuery).addIndex(db).addType("habittype").build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    HabitType test = result.getSourceAsObject(HabitType.class);
                    return test;
                }else{
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Failed Q", "Search broke");
                return null;
            }
        }
    }

    /**
     * Used to add HabitType to database. Returns false on fail
     */
    public static class addHabitType extends AsyncTask<HabitType, Void, String>{

        @Override
        public String doInBackground(HabitType... ht){
            verifySettings();
            if (ht.length != 1){
                Log.e("Bad input", "expected 1 Habit type");
                return null;
            }
            Index htitem = new Index.Builder(ht[0]).index(db).type("habittype").build();
            try{
                DocumentResult result = client.execute(htitem);
                if (result.isSucceeded()){
                    return result.getId();
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("failed add", "Failed to add HabitType");
                return null;
            }
            return null;
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
            String jsonQuery = "{ \"size\": 10000, \"query\": {\"match\": {\"ownername\": \"" + username + "\"}}}";
            Search search = new Search.Builder(jsonQuery).addIndex(db).addType("habittype").build();            //potential issue here where match will amtch other owner name.

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

    /**
     * Deletes a habit type
     */
    public static class deleteHabitType extends AsyncTask<String, Void, Boolean>{

        @Override
        public Boolean doInBackground(String... s){
            String ownername = s[0];
            String title = s[1];
            String jsonQuery = "{\"query\": {\"bool\": {\"must\": [{\"match\": {\"ownername\": \"" + ownername + "\"}},{\"match\": {\"title\": \"" +title+"\"}}]}}}";
            String jestId = getJestId(jsonQuery, "habittype");
            if (deleteItem(jestId, "habittype")) return true;
            return false;
        }
    }

    //-------------------------FOR FOLLOWERS-------------------------------------------


    /**
     * adds a follower  NOTE* I think
     */
    public static class addFollowerPair extends AsyncTask<Followers, Void, Boolean>{

        @Override
        public Boolean doInBackground(Followers... f){
            verifySettings();
            Index fitem = new Index.Builder(f[0]).index(db).type("followers").build();
            try{
                DocumentResult result = client.execute(fitem);
                if (result.isSucceeded()) return true;
            }catch (Exception e){
                e.printStackTrace();
                Log.e("failed add", "Failed to add followerPair");
                return false;
            }
            return false;
        }
    }

    /**
     * Deletes request type
     *
     */
    public static class deleteFollowerPair extends AsyncTask<String, Void, Boolean>{

        @Override
        public Boolean doInBackground(String... s){
            String requester = s[0];
            String requested = s[1];
            String jsonQuery = "{\"query\": {\"bool\": {\"must\": [{\"match\": {\"requester\": \"" + requester + "\"}},{\"match\": {\"requestedUser\": \"" +requested+"\"}}]}}}";
            String jestId = getJestId(jsonQuery, "followers");
            if (deleteItem(jestId, "followers")) return true;
            return false;
        }
    }

    /**
     * Gets a list of all followers
     * NOTE that it requires a perspective argument (String) as the second argument
     * and also requries a success argument as the third argument
     * set success arg as 0 to view all pending request
     * set success arg as 1 to view all confirmed request
     * if perspective = "requester" get the list of follower obj where requester=username
     * if perspective = "requested" get the list of follower obj where requestedUser=username
     */
    public static class getFollowerPairs extends AsyncTask<String, Void, ArrayList<Followers>>{

        @Override
        public ArrayList<Followers> doInBackground(String...s){
            verifySettings();
            ArrayList<Followers> resultArr = new ArrayList<>();
            String username=s[0];
            String perspective=s[1];
            String success=s[2];
            String jsonQuery;
            //2 means we just want to check if its such records exist, without caring if
            //it was successful or not
            if (success.equals("2")){
                if (perspective.equals("requester")){
                    //jsonQuery = "{\"size\": 10000, \"query\": {\"bool\": {\"must\": [{\"match\": {\"requester\": \"" + username + "\"}},{\"match\": {\"requestAccepted\": \"" +success+"\"}}]}}}";
                    jsonQuery = "{ \"size\": 10000, \"query\": {\"match\": {\"requester\": \"" + username + "\"}}}";
                }else if (perspective.equals("requested")){
                    //jsonQuery = "{\"size\": 10000, \"query\": {\"bool\": {\"must\": [{\"match\": {\"requestedUser\": \"" + username + "\"}},{\"match\": {\"requestAccepted\": \"" +success+"\"}}]}}}";
                    jsonQuery = "{ \"size\": 10000, \"query\": {\"match\": {\"requestedUser\": \"" + username + "\"}}}";
                }
                else{
                    Log.e("Bad input", "undefined perspective");
                    return null;
                }
            //we expect 0 or 1
            }else{
                if (perspective.equals("requester")){
                    jsonQuery = "{\"size\": 10000, \"query\": {\"bool\": {\"must\": [{\"match\": {\"requester\": \"" + username + "\"}},{\"match\": {\"requestAccepted\": \"" +success+"\"}}]}}}";
                    //jsonQuery = "{ \"size\": 10000, \"query\": {\"match\": {\"requester\": \"" + username + "\"}}}";
                }else if (perspective.equals("requested")){
                    jsonQuery = "{\"size\": 10000, \"query\": {\"bool\": {\"must\": [{\"match\": {\"requestedUser\": \"" + username + "\"}},{\"match\": {\"requestAccepted\": \"" +success+"\"}}]}}}";
                    //jsonQuery = "{ \"size\": 10000, \"query\": {\"match\": {\"requestedUser\": \"" + username + "\"}}}";
                }
                else{
                    Log.e("Bad input", "undefined perspective");
                    return null;
                }
            }

            Search search = new Search.Builder(jsonQuery).addIndex(db).addType("followers").build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Followers> found = result.getSourceAsObjectList(Followers.class);
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



    //-------------------------------HELPERS---------------------------------------
    /**
     * Bulk builder
     */
    public static class bulkBuild extends AsyncTask<Bulk, Void, Boolean>{

        @Override
        public Boolean doInBackground(Bulk... b){
            verifySettings();
            try{
                client.execute(b[0]);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                Log.e("ES FAIL", "Bulk Fail");
                return false;
            }
        }
    }

    /**
     * Method for getting jesId asyncrhonously
     */

    public static class getJestIdAsync extends AsyncTask<String, Void, String>{
        @Override
        public String doInBackground(String... s){
            verifySettings();
            return getJestId(s[0], s[1]);
        }
    }

    /**
     * Method retrieves the jestId provided seary query is correct
     * @param jsonQuery
     * @param type
     * @return
     */
    public static String getJestId(String jsonQuery, String type){
        Search search = new Search.Builder(jsonQuery).addIndex(db).addType(type).build();
        String jestId;
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                SearchResult.Hit hit = result.getFirstHit(Map.class);
                Map source = (Map) hit.source;
                jestId = (String) source.get(JestResult.ES_METADATA_ID);
                if (jestId.isEmpty()){ //could nto find anything
                    Log.e("error", "Could not find jest id");
                    return null;
                }
                else{
                    return jestId;
                }
            }else{
                Log.e("error", "Search failed");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "Something went wrong");
            return null;
        }
    }

    /**
     * Method deletes an item with a given jestId and type
     * @param jestId
     * @param type
     * @return
     */
    public static boolean deleteItem(String jestId, String type){
        Delete delete = new Delete.Builder(jestId).index(db).type(type).build();
        try {
            if (client.execute(delete).isSucceeded()) {
                return true;
            }else{
                Log.e("DELETE FAIL", "not succeeded");
                return false;
            }
        } catch (Exception e) {
            Log.e("DELETE FAIL", "Failed delete");
            return false;
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
