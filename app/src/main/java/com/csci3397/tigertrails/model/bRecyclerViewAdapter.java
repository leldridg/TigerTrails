package com.csci3397.tigertrails.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci3397.tigertrails.R;

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

    }

    @Override
    public int getItemCount() {
        //know the number of items you want displayed
        return paths.size();
    }

    public static class bViewHolder extends RecyclerView.ViewHolder {
        //grabbing row view from layout file
        //similar to an onCreate method


        public bViewHolder(@NonNull View itemView, Context context) {
            super(itemView);


        }
    }
}