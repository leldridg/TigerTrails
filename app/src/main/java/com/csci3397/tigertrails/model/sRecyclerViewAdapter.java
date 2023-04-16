package com.csci3397.tigertrails.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci3397.tigertrails.R;

import java.util.ArrayList;

public class sRecyclerViewAdapter extends RecyclerView.Adapter<sRecyclerViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<Path> paths;

    public sRecyclerViewAdapter(Context context, ArrayList<Path> paths){
        this.context = context;
        this.paths = paths;
    }

    @NonNull
    @Override
    public sRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout, giving look to each row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_list_row, parent, false);

        return new sRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to the rows created in the layout file based on the position of the recycler view

        holder.pathName.setText(paths.get(position).getPathName());
        holder.minutes.setText(String.format("%s min", paths.get(position).getMinutes()));
        holder.distance.setText(String.format("%s mi", paths.get(position).getDistance()));
        holder.rating.setText(String.format("%s", paths.get(position).getRating()));
    }

    @Override
    public int getItemCount() {
        //know the number of items you want displayed
        return paths.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing row view from layout file
        //similar to an onCreate method

        ImageButton upvote;
        ImageButton downvote;
        ImageButton bookmark;
        TextView rating;
        TextView distance;
        TextView minutes;
        TextView pathName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            upvote = itemView.findViewById(R.id.upvote); //stuff might go wrong here, R
            downvote = itemView.findViewById(R.id.downvote);
            bookmark = itemView.findViewById(R.id.bookmark);
            rating = itemView.findViewById(R.id.rating);
            distance = itemView.findViewById(R.id.distance);
            minutes = itemView.findViewById(R.id.estTime);
            pathName = itemView.findViewById(R.id.pathName);
        }
    }
}
