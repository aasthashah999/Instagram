package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    Button login;
    Button signUp;
    EditText tvusername;
    EditText tvpassword;
    ParseUser currentUser = ParseUser.getCurrentUser();

    /**
     * RETURN: void
     * Params: bundle that represents a state
     * This method sets the layout and calls a helper function for logging in.
     **/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginButton);
        tvusername = findViewById(R.id.tvusername);
        tvpassword = findViewById(R.id.tvNameInput);
        signUp = findViewById(R.id.buttonSignUp);

        if(currentUser != null){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Please login", Toast.LENGTH_LONG).show();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = tvusername.getText().toString();
                final String password = tvpassword.getText().toString();

                login(username, password);
                Toast.makeText(getApplicationContext(), "trying to login in", Toast.LENGTH_LONG).show();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, signUpActivity.class);
                startActivity(i);
            }
        });

    }

    /**
     * RETURN: void
     * Params: bundle that represents a state
     * This method allows the user to attempt logging in
     **/
    private void login(String username, String password) {
        //NOTE: Use logInInBackground because the this method takes place on the background thread and does not impact the main thread.
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successfully");
                    final Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    //NOTE: This prevents the user from clicking the back button and being send to the login
                    finish();
                } else {
                    Log.e("LoginActivity", "Login Failure");
                }
            }
        });
    }

}
