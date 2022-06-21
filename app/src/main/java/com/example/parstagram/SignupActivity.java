package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnsignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnsignUp = findViewById(R.id.btnSignup);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUp(username, password);
            }
        });
    }


    public void signUp(String username, String password) {
        ParseUser user =  new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);

// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null || user.getCurrentUser() != null) {

                     Toast.makeText(SignupActivity.this, "Signup " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                    // Sign up didn't succeed. Look at the ParseException

                    // to figure out what went wrong

                goMainActivity();
            }
        });
    }
    private void goMainActivity() {
        Intent i = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(i);
        finish();
}
}