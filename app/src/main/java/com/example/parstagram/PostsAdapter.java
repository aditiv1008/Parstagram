package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.fragments.ProfileFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }



class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTimeStamp;
    private ImageView ivprofilePic;
    private TextView tvUsername2;
    private TextView tvLikes;
    private ImageButton ibLike;
    private ImageButton ibComment;
    MainActivity activity;




    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        ivImage = itemView.findViewById(R.id.ivImage);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
        ivprofilePic = itemView.findViewById(R.id.ivprofilePic);
        tvUsername2 = itemView.findViewById(R.id.tvUsername2);
        tvLikes = itemView.findViewById(R.id.tvLikes);
        ibComment = (ImageButton) itemView.findViewById(R.id.ibComment);
        ibLike = (ImageButton) itemView.findViewById(R.id.ibLike);

    }



    public void bind(Post post) {
        // Bind the post data to the view elements

        ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComposeCommentActivity.class);

                i.putExtra("post", post);

                context.startActivity(i);

            }
        });

        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ParseUser> likedBy = post.getLikedBy();
                if(post.isLikedByCurrentUser()) {
                    //unlike
                    post.unlike();
                    ibLike.setBackgroundResource(R.drawable.ufi_heart);
                }else {
                    post.like();
                    ibLike.setBackgroundResource(R.drawable.ufi_heart_active);

                }
                post.setLikedBy(likedBy);
                post.saveInBackground();
                tvLikes.setText(post.getLikesCount());
            }
        });
        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        tvUsername2.setText(post.getUser().getUsername());
        tvTimeStamp.setText(post.getTime());




        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(context).load(image.getUrl()).into(ivImage);
            Glide.with(context).load(image.getUrl()).circleCrop().into(ivprofilePic);
        }
    
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedView.class);
                intent.putExtra(DetailedView.EXTRA_CONTACT, post);
                context.startActivity(intent);
            }
        });


    }

}
}