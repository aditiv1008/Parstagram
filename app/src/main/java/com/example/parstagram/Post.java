package com.example.parstagram;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKED_BY= "likedBy";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION,description);
    }
    public Post (){}
    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public List<ParseUser> getLikedBy() {
        List<ParseUser> likedBy = getList(KEY_LIKED_BY);
        if(likedBy == null) {
            return new ArrayList<>();
        }
        return likedBy;

    }

    public void setLikedBy(List<ParseUser> newLikedBy ) {
        put(KEY_LIKED_BY, newLikedBy);
    }

    public String getLikesCount() {
        int likeCount = getLikedBy().size();
        return String.valueOf(likeCount) + (likeCount == 1 ? " like" : " likes");
    }


    public boolean isLikedByCurrentUser() {
        List<ParseUser> likedBy = getLikedBy();
        for(int i =0; i < likedBy.size(); i++) {
            if(likedBy.get(i).hasSameId(ParseUser.getCurrentUser())) {
                return true;
            }
        }
        return false;
    }

    public void unlike(){
        List<ParseUser> likedBy = getLikedBy();
        for(int i =0; i < likedBy.size(); i++) {
            if(likedBy.get(i).hasSameId(ParseUser.getCurrentUser())) {
                likedBy.remove(i);
            }

        }
        setLikedBy(likedBy);
        saveInBackground();
    }

    public void like() {
        unlike();
        List<ParseUser> likedBy = getLikedBy();
        likedBy.add(ParseUser.getCurrentUser());
        setLikedBy(likedBy);
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }

    public String getTime() {
        Date createdAt = getCreatedAt();
        String timeAgo = calculateTimeAgo(createdAt);
        return timeAgo;
    }



}
