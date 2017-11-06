package com.notcmput301.habitbook;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by yizhang on 2017-11-05.
 */

public class LocalFileControler {
    private static final String UserFile = "user.sav";
    private static final String HabitFile = "habit.sav";
    private static final String EventFile = "event.sav";
    private ArrayList<User> users;
    private ArrayList<HabitType> habitTypes;
    private ArrayList<HabitEvent> habitEvents;

    public LocalFileControler(){

        users = loadFromFile(UserFile);
        habitTypes = loadFromFile(HabitFile);
        habitEvents = loadFromFile(EventFile);
    }
    public ArrayList<User> getUsers(){

        return users;
    }
    public ArrayList<HabitType> getHabitTypes(){

        return habitTypes;
    }
    public ArrayList<HabitEvent> getHabitEvents(){

        return habitEvents;
    }
    public void UpdateUsers(ArrayList<User> users){

        SaveInFile(users, UserFile);
    }
    public void UpdateHabitTypes(ArrayList<HabitType> habitTypes){

        SaveInFile(habitTypes, HabitFile);
    }
    public void UpdateHabitEvents(ArrayList<HabitEvent> habitEvents){
        SaveInFile(habitEvents, EventFile);
    }
    private void SaveInFile(ArrayList DataArray, String FileName){
        try {
            FileOutputStream fos = openFileOutput(FileName,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(DataArray, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
            //e.printStackTrace();
        }
    }
    private ArrayList loadFromFile(String FileName) {
        //ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType;
            if (FileName == UserFile){
                listType = new TypeToken<ArrayList<User>>() {}.getType();
            }
            else if (FileName == HabitFile){
                listType = new TypeToken<ArrayList<HabitType>>() {}.getType();
            }
            else {
                listType = new TypeToken<ArrayList<HabitEvent>>() {}.getType();
            }

            return gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            if (FileName == UserFile){
                ArrayList<User> users = new ArrayList<>();
                return users;
            }
            else if (FileName == HabitFile){
                ArrayList<HabitType> habitTypes = new ArrayList<>();
                return habitTypes;            }
            else {
                ArrayList<HabitEvent> habitEvents = new ArrayList<>();
                return habitEvents;            }
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        //return tweets.toArray(new String[tweets.size()]);
    }
}
