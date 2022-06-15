package com.example.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
   public ArrayList<Comment> mComments = new ArrayList();
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {


        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvAuthor;
        public TextView tvBody;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }


    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View commentView = inflater.inflate(R.layout.item_comment, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(commentView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Comment comment = mComments.get(position);

        holder.tvBody.setText(comment.getBody());
        holder.tvAuthor.setText(comment.getAuthor().getUsername());


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
