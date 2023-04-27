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

public class uRecyclerViewAdapter extends RecyclerView.Adapter<uRecyclerViewAdapter.uViewHolder>{
    Context context;
    static ArrayList<Path> paths;

    public uRecyclerViewAdapter(Context context, ArrayList<Path> paths){
        this.context = context;
        this.paths = paths;
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

    }

    @Override
    public int getItemCount() {
        //know the number of items you want displayed
        return paths.size();
    }

    public static class uViewHolder extends RecyclerView.ViewHolder {
        //grabbing row view from layout file
        //similar to an onCreate method


        public uViewHolder(@NonNull View itemView, Context context) {
            super(itemView);


        }
    }
}