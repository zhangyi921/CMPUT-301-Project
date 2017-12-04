/*
 * NetworkHandler
 *
 * Version 1.0
 *
 * December 4, 2017
 *
 * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
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

import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;

/**
 * Created by shangchen on 2017-12-01.
 */

/**
 * Handles network related operations for offlien.online sync
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
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
            }if (success > 0){ //0 means it could not find anytihing
                return false; //false = foudn something
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
    public String addHabitType(HabitType newHabit){

        ElasticSearch.addHabitType aht = new ElasticSearch.addHabitType();
        aht.execute(newHabit);
        try{
            String id = aht.get();
            if (id==null){
                Toast.makeText(context, "Opps, Something went wrong on our end", Toast.LENGTH_SHORT).show();
                return null;
            }else{
                Toast.makeText(context, "Added Habit Type!", Toast.LENGTH_SHORT).show();
                return id;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
            return null;
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
     * Gets a list of habit types
     * @param owner
     * @return
     */
    public ArrayList<HabitType> getHabitList(String owner){
        ArrayList<HabitType> habitTypes;
        ElasticSearch.getHabitTypeList ghtl = new ElasticSearch.getHabitTypeList();
        ghtl.execute(owner);
        try {
            habitTypes = ghtl.get();
            if (habitTypes==null){
                return new ArrayList<>();
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Failed to retrieve items. Check connection", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
        return habitTypes;
    }


    /*
     *The process of update HabitType is delete the old, and add new
     * 1. call delete old
     * 1. call add new. NOTE Make sure that all habit event title is also updated.
     */

    //------------------------------------------------------------------------------------


    /*
     * add habit event is a composition of deleting old habit type, and
     * putting new habitType with added event
     *
     * 1. first we get old habit type
     * 2. then we delete old.
     * 3. then we add new (old + extra event)
     */


    /*
     * Update habit event is a composition of
     * 1. deleting habit type
     * 2. adding new habit type with updated events
     */


    /*
     * Deleting Habit event is a composition of
     * 1. deleting habit type
     * 2. adding habit type with the most recent version removed.
     */


    /*
     * preferences.getAll returns a Map which is inheritently unordered.
     * This is bad news as the order of operations does indeed matter.
     * This function aims to order the hashmap
     */

    public void waitOnAdd(HabitType hT){
        while (verifyExistance(hT.getTitle())){ //want to find it exists
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "waiting..", Toast.LENGTH_SHORT).show();
            Log.e("WAITING ON", hT.getTitle());
        }     //this is bad...
    }

    public void waitOnDelete(HabitType hT){
        while (!verifyExistance(hT.getTitle())){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "waiting..", Toast.LENGTH_SHORT).show();
            Log.e("WAITING ON", hT.getTitle());
        }    //this is bad...
    }


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

        Log.e("ARRAY", Arrays.toString(k));
    }


    /**
     * looks through all the tasks and perfroms the appropriate actions
     */
    public boolean doAllTasks() throws InterruptedException {

        Toast.makeText(context, "Synchronizing...", Toast.LENGTH_LONG).show();

        getOrder();
        //Bulk.Builder bulkB = new Bulk.Builder().defaultIndex(db).defaultType("habittype");

        String previousAction = "start";
        HabitType previousHabit = null;
        for (int i=0; i<k.length; i++){

            String gsonObject = v[i];
            HabitType hT = gson.fromJson(gsonObject, HabitType.class);


            if(k[i].equals("a")){


                if (previousAction.equals("a") && previousHabit != null){
                    waitOnAdd(previousHabit);
                }else if (previousAction.equals("d") && previousHabit != null){
                    waitOnDelete(previousHabit);
                }

                Log.e("adding", hT.getTitle() + " " + i + " 406");

                if(verifyExistance(hT.getTitle())==false){continue;}

                addHabitType(hT);

                previousAction="a";

                previousHabit = hT;

            }else if(k[i].equals("d")){

                if (previousAction.equals("a") && previousHabit != null){
                    waitOnAdd(previousHabit);
                    //Thread.sleep(1000);
                }else if (previousAction.equals("d") && previousHabit != null){
                    waitOnDelete(previousHabit);
                    //Thread.sleep(1000);
                }

                Log.e("deleting", hT.getTitle() + i + " 428");

                deleteHabitType(hT);

                previousAction="d";

                previousHabit = hT;

            }else if(k[i].equals("au")){ //au ignores the verificaiton rpocess

                if (previousAction.equals("a") && previousHabit != null){
                    waitOnAdd(previousHabit);
                    //Thread.sleep(1000);

                }else if (previousAction.equals("d") && previousHabit != null){
                    waitOnDelete(previousHabit);
                    //Thread.sleep(1000);
                }

                Log.e("adding NO DELETE", hT.getTitle() + " "+ i + "449");

                addHabitType(hT);

                previousAction="a";

                previousHabit = hT;
            }
        }

        resetPref();
        Toast.makeText(context, "complete!", Toast.LENGTH_LONG).show();
        return true;
    }

}
