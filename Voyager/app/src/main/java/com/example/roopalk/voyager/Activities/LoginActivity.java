package com.example.roopalk.voyager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roopalk.voyager.R;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
{
    //Using ButterKnife to bind the views to the IDs
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;

    private final static String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //persistent user

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if(etUsername.getText().toString() == "" || etPassword.getText().toString() == "")
        {
            btnLogin.setEnabled(false);
        }
        else
        {
            btnLogin.setEnabled(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                //calling the login method, where we can use Parse
                try
                {
                    login(username, password);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login(String username, String password) throws ParseException {
        //setting up parse configuration
        ParseUser.logInInBackground(username, password, new LogInCallback()
        {
            //checks if the log in has actually been completed successfully
            @Override
            public void done(ParseUser user, ParseException e)
            {
                if(e == null)
                {
                    Log.d(TAG, "Login successful!");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Log.e(TAG, " Login failure.");
                    e.printStackTrace();
                }
            }
        });
    }
}