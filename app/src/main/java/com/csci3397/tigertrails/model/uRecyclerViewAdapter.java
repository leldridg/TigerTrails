package com.csci3397.tigertrails.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.view.PathActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class uRecyclerViewAdapter extends RecyclerView.Adapter<uRecyclerViewAdapter.uViewHolder>{
    Context context;
    static ArrayList<Path> paths;

    public uRecyclerViewAdapter(Context context, ArrayList<Path> paths){
        this.context = context;
        this.paths = (ArrayList<Path>) paths.stream().filter(path -> path.getCreator().equals("admin")).collect(Collectors.toList());
        //filter to only those paths created by current user
        Collections.reverse(this.paths);
    }

    @NonNull
    @Override
    public uViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating layout, giving look to each row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_list_row, parent, false);

        return new uViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull uViewHolder holder, int position) {
        //assigning values to the rows created in the layout file based on the position of the recycler view

        holder.pathName.setText(paths.get(position).getPathName());
        holder.estTime.setText(String.format("%.2f min", paths.get(position).getMinutes()));
        holder.distance.setText(String.format("%.2f km", paths.get(position).getDistance()));
    }

    @Override
    public int getItemCount() {
        //know the number of items you want displayed
        return paths.size();
    }

    public static class uViewHolder extends RecyclerView.ViewHolder {
        //grabbing row view from layout file
        //similar to an onCreate method

        TextView pathName;
        TextView estTime;
        TextView distance;

        public uViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            pathName = itemView.findViewById(R.id.pathName);
            estTime = itemView.findViewById(R.id.estTime);
            distance = itemView.findViewById(R.id.distance);

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

        }
    }
}