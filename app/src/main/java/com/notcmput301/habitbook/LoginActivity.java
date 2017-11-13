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

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
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
                    Intent mainmenu = new Intent(LoginActivity.this, MainMenuActivity.class);
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
