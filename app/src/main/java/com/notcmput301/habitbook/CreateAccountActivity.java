/*
 * CreateAccountActivity
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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for creating new account
 *
 * @author NOTcmput301
 * @version 1.0
 * @see User
 * @since 1.0
 */

public class CreateAccountActivity extends AppCompatActivity {
//    private TextView createAccountScreen;
//    private EditText usernameText;
//    private EditText passwordText;
//    private EditText passwordConfirmText;
//    private Button createAccount;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState previous instance of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    /**
     * Creates account given information
     *
     * @param view view of input info
     */
    public void caCreate(View view){
        EditText usernameEt = (EditText) findViewById(R.id.createAccount_Username);
        EditText passwordEt = (EditText) findViewById(R.id.createAccount_password);
        EditText passwordREt = (EditText) findViewById(R.id.createAccount_passwordR);

        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        String passwordR = passwordREt.getText().toString();

        if (username.isEmpty() || password.isEmpty() || passwordR.isEmpty()){
            Toast.makeText(this, "You have missing fields", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(passwordR)){
            Toast.makeText(this, "passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 5){
            Toast.makeText(this, "password is too short", Toast.LENGTH_LONG).show();
        }
        //see if username exists in database
        ElasticSearch.userExists ue = new ElasticSearch.userExists();
        ue.execute(username);
        try{
            int success = ue.get();
            if (success < 0){
                Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_LONG).show();
                return;
            }else if (success > 0){
                Toast.makeText(this, "Username exists!", Toast.LENGTH_LONG).show();
                return;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
            Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_LONG).show();
            return;
        }
        //create a new user
        User newUser = new User(username, password);
        ElasticSearch.addUser au = new ElasticSearch.addUser();
        au.execute(newUser);
        try{
            User u = au.get();
            if (u == null){
                Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_LONG).show();
                return;
            }else {
                Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();
                Intent mainmenu = new Intent(CreateAccountActivity.this, MainMenuActivity.class);
                mainmenu.putExtra("passedUser", u);
                startActivity(mainmenu);
                return;
            }
        }catch(Exception e){
            Log.e("get failure", "Failed to retrieve");
            e.printStackTrace();
            Toast.makeText(this, "Ooops, Something went wrong on our end", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * Returns to the previous activity
     *
     * @param view view of current activity
     */
    public void caBack(View view){
        Intent back = new Intent(CreateAccountActivity.this, LoginActivity.class);
        startActivity(back);
    }
}
