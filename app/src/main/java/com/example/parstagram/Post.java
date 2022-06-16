package com.example.parstagram;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.ArrayList;
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

}
