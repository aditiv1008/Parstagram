package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("4qwOULPogz5Dc6INlIKMJ4G2Rbwc30t6qtIX13Xt")
                .clientKey("fIhysWeyZfFOj4G2JvahvmuSakimQYu6LF43Q2ls")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
