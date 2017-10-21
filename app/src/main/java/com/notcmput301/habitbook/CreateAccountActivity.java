package com.notcmput301.habitbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView createAccountScreen;
    private EditText usernameText;
    private EditText passwordText;
    private EditText passwordConfirmText;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }
}
