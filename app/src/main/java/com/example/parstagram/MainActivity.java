package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstagram.fragments.ComposeFragment;
import com.example.parstagram.fragments.PostsFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigation;
    public PostsFragment postsFragment = new PostsFragment();
    public ComposeFragment composeFragment = new ComposeFragment(MainActivity.this);
    public ProfileFragment  profileFragment = new ProfileFragment(ParseUser.getCurrentUser());

    public void goToFeedFragment(){
        bottomNavigation.setSelectedItemId(R.id.action_home);
    }
    public void goToProfile(User user){
        bottomNavigation.setSelectedItemId(R.id.action_profile);
        profileFragment.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = postsFragment;
                        break;
                    case R.id.action_compose:
                        fragment = composeFragment;
                        break;
                    case R.id.action_profile:
                    default:
                        profileFragment.user = (User) ParseUser.getCurrentUser();
                        fragment = profileFragment;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.rlContainer, fragment).commit();
                return true;

            }
        });
        bottomNavigation.setSelectedItemId(R.id.action_home);

       // queryPosts();


    }


    public void onLogoutButton(View view) {
        // forget who's logged in
       ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);


    }
}