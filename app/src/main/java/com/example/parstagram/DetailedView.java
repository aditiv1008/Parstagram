package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

public class DetailedView extends AppCompatActivity {
public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
ImageView ivDetailPicture;
TextView tvCaption;
TextView tvTimeStamp;
ImageButton ibComment;
TextView tvUsername;
ImageButton ibLike;
RecyclerView rvComments;
private Post post;
CommentsAdapter adapter;

    void refreshComments() {
        ParseQuery<Comment> query =  ParseQuery.getQuery("Comment");
        query.whereEqualTo(Comment.KEY_POST, post);
        query.orderByDescending("createdAt");
        query.include(Comment.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if (e != null) {
                    Log.e("failed to get comments", e.getMessage());
                    return;
                }
                adapter.mComments.clear();
                adapter.mComments.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onRestart () {
        super.onRestart();

        refreshComments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvCaption = (TextView) findViewById(R.id.tvCaption);
        ivDetailPicture = (ImageView) findViewById(R.id.ivDetailPicture);
        ibComment = (ImageButton) findViewById(R.id.ibComment);
        ibLike = (ImageButton)  findViewById(R.id.ibLike);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        tvUsername = (TextView) findViewById(R.id.tvUsername) ;



        ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailedView.this, ComposeCommentActivity.class);

                i.putExtra("post", post);

                startActivity(i);

            }
        });


      //  Drawable comment = this.getDrawable((R.drawable.ufi_comment));
      //  ibComment.setImageDrawable(comment);

      post = (Post) getIntent().getParcelableExtra(EXTRA_CONTACT);
      adapter = new CommentsAdapter();
      rvComments.setLayoutManager(new LinearLayoutManager(this));
      rvComments.setAdapter(adapter);

       Glide.with(this).load(post.getImage().getUrl()).into(ivDetailPicture);
       tvTimeStamp.setText(post.getCreatedAt().toString());
       tvUsername.setText(post.getUser().getUsername());
       tvCaption.setText(post.getDescription());


        //load all comments for this post
        refreshComments();



    }
}