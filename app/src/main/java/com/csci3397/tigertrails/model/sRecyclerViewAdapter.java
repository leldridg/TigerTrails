package com.csci3397.tigertrails.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.view.PathActivity;

import java.util.ArrayList;
import java.util.Collections;

public class sRecyclerViewAdapter extends RecyclerView.Adapter<sRecyclerViewAdapter.sViewHolder>{
    Context context;
    static ArrayList<Path> paths;

    public sRecyclerViewAdapter(Context context, ArrayList<Path> paths){
        this.context = context;
        this.paths = paths;
        Collections.reverse(this.paths);
    }

    @NonNull
    @Override
    public sViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout, giving look to each row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_list_row, parent, false);

        return new sViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull sViewHolder holder, int position) {
        //assigning values to the rows created in the layout file based on the position of the recycler view

        holder.pathName.setText(paths.get(position).getPathName());
        holder.minutes.setText(String.format("%.2f min", paths.get(position).getMinutes()));
        holder.distance.setText(String.format("%.2f km", paths.get(position).getDistance()));
        holder.rating.setText(String.format("%s", paths.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        //know the number of items you want displayed
        return paths.size();
    }

    public static class sViewHolder extends RecyclerView.ViewHolder {
        //grabbing row view from layout file
        //similar to an onCreate method

        ImageButton upvote;
        ImageButton downvote;
        ImageButton bookmark;
        TextView rating;
        TextView distance;
        TextView minutes;
        TextView pathName;

        int ratingNum;
        boolean isBookmarked;

        public sViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            upvote = itemView.findViewById(R.id.upvote); //stuff might go wrong here, R
            downvote = itemView.findViewById(R.id.downvote);
            bookmark = itemView.findViewById(R.id.bookmark);
            rating = itemView.findViewById(R.id.rating);
            distance = itemView.findViewById(R.id.distance);
            minutes = itemView.findViewById(R.id.estTime);
            pathName = itemView.findViewById(R.id.pathName);

            pathName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the clicked item
                    int position = getAdapterPosition();

                    // Make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the path at that position
                        Path clickedPath = paths.get(position);

                        // Create an intent to start the PathActivity and pass the clicked path as extra
                        Intent intent = new Intent(context, PathActivity.class);
                        intent.putExtra("clicked_path", clickedPath);
                        context.startActivity(intent);
                    }
                }
            });

            isBookmarked = false;
            bookmark.setImageResource(R.drawable.ic_bookmark_border);

            ratingNum = 0;
            rating.setText(""+ratingNum);

            upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ratingNum = ratingNum + 1;
                    rating.setText(""+ratingNum);
                }
            });
            downvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ratingNum = ratingNum - 1;
                    rating.setText(""+ratingNum);
                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isBookmarked) {
                        bookmark.setImageResource(R.drawable.ic_bookmark_border);
                        isBookmarked = false;
                    } else {
                        bookmark.setImageResource(R.drawable.ic_bookmark_filled);
                        isBookmarked = true;
                    }
                }
            });
        }
    }
}
