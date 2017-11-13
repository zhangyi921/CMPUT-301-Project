/*
 * LocalFileController
 *
 * Version 1.0
 *
 * November 12, 2017
 *
 * Copyright (c) 2017 Team NOTcmput301, CMPUT301, University of Alberta - All Rights Reserved
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in the project wiki on github. Otherwise please contact miller4@ualberta.ca.
 */

package com.notcmput301.habitbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

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

/**
 * Controller for handling local offline files
 *
 * @author NOTcmput301
 * @version 1.0
 * @since 1.0
 */

/*
* Use this class to login and get user object. Any changes made in user including the changes about
* habit type or habit event, only need to update user. */

public class LocalFileController extends AppCompatActivity{
    private static final String UserFile = "user.sav";

    private ArrayList<User> users;

    public LocalFileController(){

        this.users = loadFromFile();

    }

    public User Login(String username, String password){
        for (User u : this.users){
            if (u.getUsername() == username && u.getPassword() == password){
                return u;
            }
        }
        return null;
    }
    public void Save(User user){
        for (User u : this.users){
            if (u.getUsername() == user.getUsername()){
                int index = this.users.indexOf(u);
                this.users.set(index, user);
                SaveInFile();
                break;
            }
        }
    }
    private void SaveInFile(){
        try {
            FileOutputStream fos = openFileOutput(UserFile,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(this.users, writer);
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
    private ArrayList loadFromFile() {
        //ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(UserFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<User>>() {}.getType();


            return gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

            ArrayList<User> users = new ArrayList<User>();
            return users;

            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        //return tweets.toArray(new String[tweets.size()]);
    }
}
