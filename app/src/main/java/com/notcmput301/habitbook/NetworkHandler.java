/*
 *  * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 *  * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 *  * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shangchen on 2017-12-01.
 */

public class NetworkHandler {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    public String username;
    private String[] k;
    private String[] v;


    public NetworkHandler(Context cont){
        this.context=cont;
        this.sharedPref = context.getSharedPreferences("offlineTasks", Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();
        Username unm = Username.getInstance();
        this.username=unm.getName();
    }

    /**
     * Checks network availability
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Get the field of a specific item we choose. Position defines which item, title defines the field
     * of the item
     * @param action
     * @param position
     * @return
     */
    public String getString(String action, int position){
        String key = String.format("%s_%d", action, position);
        return sharedPref.getString(key, "Could not Find: "+key);
    }

    /**
     * Insert a field of a specific item we choose.  action defines the field
     * of the item, and its position is automatically updated
     * @param action
     * @param text
     */
    public void putString(String action, String text){
        String key = String.format("%s_%d", action, getMax());
        editor.putString(key, text);
        updateMax(1);
        editor.apply();
    }

    /**
     * Finds the position of last item in sharedPreferences
     * @return
     */
    public int getMax(){
        return Integer.parseInt(sharedPref.getString("max_item", "0"));
    }

    /**
     * Changes position of what we define as the last item. This is neccessary when we add or delete
     * items
     * @param increment
     */
    public void updateMax(int increment){
        editor.putString("max_item", Integer.toString(getMax()+increment));
        editor.apply();
    }

    /**
     * Clears everything in sharedPreferences.
     */
    public void resetPref(){
        editor.clear();
        editor.commit();
    }

    /**
     * Check if editor is clear
     */
    public boolean isEditorEmpty(){
        int max = getMax();
        if (max==0){
            return true;
        }return false;
    }


    /**
     * Writes to file, using the following grammar
     *
     * prefix-type:objectText
     *
     * Prefix indicate if we are adding or deleting
     * d = delete
     * a = add
     *
     * Type will either be
     * can be anything
     *
     * Object Text is the gson representation of it
     *
     * @param task
     * @param objectText
     * @param type
     */


    //a-habittype: gson representation of habittype
    //d-habit
    //u-habitytpye: new gson -->
    //a-habittype: gson representation of habittype
    //d-habit

    /*
    gson -> habit types
    delete the old habit types
     */

    /**
     * Verifies if the habitType if given title exists
     * @param title
     * @return
     */
    public boolean verifyExistance(String title){
        ElasticSearch.habitTypeExists hte = new ElasticSearch.habitTypeExists();
        hte.execute(username, title);
        try{
            int success = hte.get();
            if (success < 0){
                Toast.makeText(context, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return false;
            }if (success > 0){
                Toast.makeText(context, "Habit Type Title has to be unique!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Adds a newHabit Type.
     * @param newHabit
     * @return
     */
    public boolean addHabitType(HabitType newHabit){

        ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
        aht.execute(newHabit);
        try{
            boolean success = aht.get();
            if (!success){
                Toast.makeText(context, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                Toast.makeText(context, "Added Habit Type!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a habit type
     * @param habit
     * @return
     */
    public boolean deleteHabitType(HabitType habit){ //this is ok
        ElasticSearch.deleteHabitType delHT = new ElasticSearch.deleteHabitType();
        delHT.execute(username, habit.getTitle());
        try{
            boolean result = delHT.get();
            if (result){
                Toast.makeText(context, "deleted item!", Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     *The process of update HabitType is delete the old, and add new
     * 1. call delete old
     * 1. call add new. NOTE Make sure that all habit event title is also updated.
     */

    //------------------------------------------------------------------------------------

//    /**
//     * get habit type. (we can get the title from this)
//     * @param title
//     * @return
//     */
//    public HabitType getHabitType(String title){
//        ElasticSearch.getHabitType gHT = new ElasticSearch.getHabitType();
//        gHT.execute(username, title);
//        try{
//            HabitType result = gHT.get();
//            return result;
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("ES", "Failed to get Habit type of name "+title);
//            return null;
//        }
//    }
//
//    /**
//     * Set HabitType
//     */
//    public void setHabitType(HabitType h){
//        this.habitType = h;
//    }
//
//
//    /**
//     * set HabitEvent
//     */
//    public void setHabitEvent(HabitEvent h){
//        this.habitEvent = h;
//    }

    /**
     * add habit event is a composition of deleting old habit type, and
     * putting new habitType with added event
     *
     * 1. first we get old habit type
     * 2. then we delete old.
     * 3. then we add new (old + extra event)
     */


    /**
     * Update habit event is a composition of
     * 1. deleting habit type
     * 2. adding new habit type with updated events
     */


    /**
     * Deleting Habit event is a composition of
     * 1. deleting habit type
     * 2. adding habit type with the most recent version removed.
     */


    /**
     * prefereces.getAll returns a Map which is inheritently unordered.
     * This is bad news as the order of operations does indeed matter.
     * This function aims to order the hashmap
     */
    public void getOrder(){
        int length = sharedPref.getAll().size() - 1; //get size of the thing
        k = new String[length];
        v = new String[length];

        Map<String, ?> keys = sharedPref.getAll();
        for(Map.Entry<String,?> keyVal : keys.entrySet()){
            String key = keyVal.getKey();
            if(key.equals("max_item")){continue;}
            String gsonObject = keyVal.getValue().toString();
            String[] parts = key.split("_");
            String action = parts[0];
            int index = Integer.parseInt(parts[1]);

            k[index] = action;
            v[index] = gsonObject;
        }
    }


    /**
     * looks through all the tasks and perfroms the appropriate actions
     */
    public boolean doAllTasks(){

        getOrder();

        for (int i=0; i<k.length; i++){

            String gsonObject = v[i];

            if(k[i].equals("a")){
                HabitType hT = gson.fromJson(gsonObject, HabitType.class);
                if(verifyExistance(hT.getTitle())==false){continue;}
                addHabitType(hT);
            }else if(k[i].equals("d")){
                HabitType hT = gson.fromJson(gsonObject, HabitType.class);
                deleteHabitType(hT);
            }else if(k[i].equals("au")){ //au ignores the verificaiton rpocess
                HabitType hT = gson.fromJson(gsonObject, HabitType.class);
                addHabitType(hT);
            }
        }
        resetPref();
        return true;
    }

}
