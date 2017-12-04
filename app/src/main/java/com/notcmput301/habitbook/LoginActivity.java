package com.notcmput301.habitbook;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Network;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Gson gson = new Gson();
    private NetworkHandler nH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nH = new NetworkHandler(this);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.loginbackground);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


    }

    public void login(View view){
        if (!nH.isNetworkAvailable()){
            Toast.makeText(this, "Internet Connection needed", Toast.LENGTH_LONG).show();
            return;
        }
        EditText usernameEt = (EditText) findViewById(R.id.login_username);
        EditText passwordEt = (EditText) findViewById(R.id.login_password);
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Some fields are missing", Toast.LENGTH_LONG).show();
        }else{
            ElasticSearch.verifyLogin vl = new ElasticSearch.verifyLogin();
            vl.execute(username, password);
            try{
                User u = vl.get();
                if (u==null){
                    Toast.makeText(this, "Check password/Username or internet connection", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show();
                    //make username global
                    Username uname = Username.getInstance();
                    HabitTypeSingleton HTS = HabitTypeSingleton.getInstance();
                    HTS.setHabitTypes(new ArrayList<HabitType>());
                    uname.setName(u.getUsername());

                    nH.resetPref(); //I clear on purpose. This means if the user logged out...gg

                    Intent mainmenu = new Intent(LoginActivity.this, MainActivity.class);
                    mainmenu.putExtra("passedUser", gson.toJson(u));
                    startActivity(mainmenu);
                }
            }catch(Exception e){
                Log.e("get failure", "Failed to retrieve");
                e.printStackTrace();
            }
        }
    }

    public void createAccount(View view){
        Intent createAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(createAccount);
    }
}
