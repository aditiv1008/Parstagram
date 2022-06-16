package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnsignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        if(ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        btnsignUp = findViewById(R.id.btnSignup);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username=  etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

      /*  btnsignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username=  etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUp(username, password);
            }
        });*/

    }

    public void loginUser(String username, String password) {
        ParseUser.logInInBackground("aditi", "aditi", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e("MESSAGE", "Issue with login", e);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT);
            }


        });
    }
   /* public void signUp(String username, String password) {
        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername("joestevens");
        user.setPassword("secret123");
        user.setEmail("email@example.com");
// Set custom properties
        user.put("phone", "650-253-0000");
// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                   goMainActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong

                }
            }
        });
    } */
    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}