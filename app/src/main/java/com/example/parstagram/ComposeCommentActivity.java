package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ComposeCommentActivity extends AppCompatActivity {

    private Post post;
    private Button btSave;
    private EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_comment);

        post = getIntent().getParcelableExtra("post");

        Toast.makeText(this, post.getDescription(), Toast.LENGTH_SHORT).show();

        btSave = findViewById(R.id.btSave);
        etBody = findViewById(R.id.etBody);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post new comment to Parse
                String body = etBody.getText().toString();

                // schema -- Comment.java

                Comment comment = new Comment();
                comment.setAuthor(ParseUser.getCurrentUser());
                comment.setBody(body);
                comment.setPost(post);

                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("yikes", e.getMessage());
                            return;
                        }
                        finish();
                }});
        }});
    }
}