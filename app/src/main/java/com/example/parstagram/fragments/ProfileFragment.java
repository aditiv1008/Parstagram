package com.example.parstagram.fragments;

import android.util.Log;

import com.example.parstagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {
   protected void queryPosts() {

       // specify what type of data we want to query - Post.class
       ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
       // include data referred by user key
       query.include(Post.KEY_USER);
       query.include(Post.KEY_LIKED_BY);

       query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());//MIGHT REMOVE LATER
       // limit query to latest 20 items
       query.setLimit(20);
       // order posts by creation date (newest first)
       query.addDescendingOrder("createdAt");
       // start an asynchronous call for posts
       query.findInBackground(new FindCallback<Post>() {
           @Override
           public void done(List<Post> posts, ParseException e) {
               // check for errors
               if (e != null) {
                   Log.e("FeedActivity", "Issue with getting posts", e);
                   return;
               }

               // for debugging purposes let's print every post description to logcat
               for (Post post : posts) {
                   Log.i("FeedActivity", "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
               }

               // save received posts to list and notify adapter of new data
               allPosts.addAll(posts);
               adapter.notifyDataSetChanged();
           }
       });

   }
}


