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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class bRecyclerViewAdapter extends RecyclerView.Adapter<bRecyclerViewAdapter.bViewHolder>{
    Context context;
    static ArrayList<Path> paths;

    public bRecyclerViewAdapter(Context context, ArrayList<Path> paths){
        this.context = context;
        this.paths = (ArrayList<Path>) paths.stream().filter(path -> path.isBookmarked()).collect(Collectors.toList());
        //filter to only those paths created by current user
        Collections.reverse(this.paths);
    }

    @NonNull
    @Override
    public bViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout, giving look to each row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bookmarked_list_row, parent, false);

        return new bViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull bViewHolder holder, int position) {
        //assigning values to the rows created in the layout file based on the position of the recycler view

        holder.pathName.setText(paths.get(position).getPathName());
        holder.minutes.setText(String.format("%.2f min", paths.get(position).getMinutes()));
        holder.distance.setText(String.format("%.2f km", paths.get(position).getDistance()));

    }

    @Override
    public int getItemCount() {
        //know the number of items you want displayed
        return paths.size();
    }

    public static class bViewHolder extends RecyclerView.ViewHolder {
        //grabbing row view from layout file
        //similar to an onCreate method

        ImageButton bookmark;
        TextView pathName;
        TextView distance;
        TextView minutes;

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        public bViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            bookmark = itemView.findViewById(R.id.bookmark);
            pathName = itemView.findViewById(R.id.pathName);
            distance = itemView.findViewById(R.id.distance);
            minutes = itemView.findViewById(R.id.estTime);

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

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {

                        bookmark.setImageResource(R.drawable.ic_bookmark_border);
                        database.getReference("paths/"+(paths.size()-position)).child("bookmarked").setValue(false);

                    }
                }
            });
        }
    }
}