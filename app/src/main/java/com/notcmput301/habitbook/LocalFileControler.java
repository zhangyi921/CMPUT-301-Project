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

    private ArrayList<User> users;


    public LocalFileControler(){

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

            ArrayList<User> users = new ArrayList<>();
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
